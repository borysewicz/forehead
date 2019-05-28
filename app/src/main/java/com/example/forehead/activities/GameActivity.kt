package com.example.forehead.activities

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment

import android.view.View
import com.example.forehead.R
import com.example.forehead.activities.CategoryActivity.Companion.CAT_KEY
import com.example.forehead.gameFragments.AnswerFragment
import com.example.forehead.gameFragments.CountingFragment
import com.example.forehead.gameFragments.QuestionFragment
import com.example.forehead.model.Category
import com.example.forehead.model.Question
import com.example.forehead.repository.TestQuestionRepository
import com.example.forehead.sensor.RotationSensorListener
import java.util.*



class GameActivity : AppCompatActivity(), QuestionFragment.QuestionFragmentListener, CountingFragment.OnCountdownFinished {


    private lateinit var questionFragment: QuestionFragment
    private lateinit var answerFragment: AnswerFragment
    private lateinit var countingFragment: CountingFragment
    private lateinit var questions : Queue<Question>

    private lateinit var sensorManager : SensorManager
    private lateinit var rotationSensor : Sensor
    private lateinit var proxSensor : Sensor
    private var correctAnswers = 0

    companion object {
        const val CORR_ANSWERS: String = "correct_answers"
       const val QUESTION_NUMBER_KEY = "question_number"
        private const val timeForResult = 1500L
        private const val timeForOrientationChange = 200L
        private const val QUESTION_NUMBER = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        questions = TestQuestionRepository().getQuestions(intent.extras.get(CAT_KEY) as Category,QUESTION_NUMBER)
        questionFragment = QuestionFragment()
        questionFragment.category = intent.extras.get(CAT_KEY) as Category
        answerFragment = AnswerFragment()
        countingFragment = CountingFragment()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR)
        proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        RotationSensorListener.setSensor(rotationSensor)
    }

    override fun onStart() {
        super.onStart()
        rotationSensor.also { rotSensor ->
            sensorManager.registerListener(RotationSensorListener, rotSensor, SensorManager.SENSOR_DELAY_NORMAL) // TODO: Fix this
        }
        proxSensor.also { proximitySensor ->
            sensorManager.registerListener(questionFragment,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL)
        }
        swapFragments(countingFragment)
    }
    override fun onResume() {
        super.onResume()
        rotationSensor.also { rotSensor ->
            sensorManager.registerListener(RotationSensorListener, rotSensor,500000,200) // make constants
        }
        proxSensor.also { proximitySensor ->
            sensorManager.registerListener(questionFragment,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(RotationSensorListener)
        sensorManager.unregisterListener(questionFragment)
    }


    private fun loadQuestion() {
        if (RotationSensorListener.getOrientation() != RotationSensorListener.Orientation.PLAYABLE){
            Handler().postDelayed({
                loadQuestion()
            }, timeForOrientationChange)  //if the device is in the wrong orientation, try again in a moment
            return
        }
        if (questions.isEmpty()){
            endGame()
        }
        else{
            questionFragment.replaceQuestion(questions.poll())
            swapFragments(questionFragment)
        }
    }

    private fun swapFragments(toSwap: Fragment){
        val fragManager = supportFragmentManager
        val transaction =  fragManager.beginTransaction()
        transaction.replace(R.id.activeFragment,toSwap)
        transaction.commit()
    }

    override fun onCountdownFinished() {
        loadQuestion()
    }

    override fun onAnswerGiven(result: QuestionResult) {
        when (result) {
            QuestionResult.PASS -> answerFragment.setAnswer(result,getString(R.string.question_pass) )
            QuestionResult.CORRECT -> {
                correctAnswers++
                answerFragment.setAnswer(result,getString(R.string.question_correct))
            }
            else -> answerFragment.setAnswer(result,getString(R.string.question_time_up))
        }
        swapFragments(answerFragment)

        Handler().postDelayed({
            loadQuestion()
        }, timeForResult)  //after specific amount of time we load the next question
    }



    private fun endGame() {
        val intent = Intent(this, EndGameActivity::class.java)
        intent.putExtra(CORR_ANSWERS,correctAnswers)
        intent.putExtra(QUESTION_NUMBER_KEY, QUESTION_NUMBER)
        startActivity(intent)
        finish()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            hideSystemUI()
        }
        else showSystemUI()
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}

enum class QuestionResult{
    TIME_PASSED,CORRECT,PASS
}

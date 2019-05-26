package com.example.forehead.activities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log

import android.view.View
import com.example.forehead.R
import com.example.forehead.activities.CategoryActivity.Companion.CAT_KEY
import com.example.forehead.gameFragments.AnswerFragment
import com.example.forehead.gameFragments.QuestionFragment
import com.example.forehead.model.Category
import com.example.forehead.model.Question
import com.example.forehead.repository.TestQuestionRepository
import com.example.forehead.sensor.RotationSensorListener
import java.util.*



class GameActivity : AppCompatActivity(), QuestionFragment.QuestionFragmentListener {

    private lateinit var questionFragment: QuestionFragment
    private lateinit var answerFragment: AnswerFragment
    private lateinit var questions : Queue<Question>
    private val timeForResult = 1500L
    private val timeForOrientationChange = 100L
    private lateinit var sensorManager : SensorManager
    private lateinit var rotationSensor : Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        questions = TestQuestionRepository().getQuestions(intent.extras.get(CAT_KEY) as Category)
        questionFragment = QuestionFragment()
        answerFragment = AnswerFragment()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR)
        RotationSensorListener.setSensor(rotationSensor)
    }

    override fun onStart() {
        super.onStart()
        rotationSensor.also { rotSensor ->
            sensorManager.registerListener(RotationSensorListener, rotSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        loadQuestion()
    }
    override fun onResume() {
        super.onResume()
        rotationSensor.also { rotSensor ->
            sensorManager.registerListener(RotationSensorListener, rotSensor,500000,200) // make constants
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(RotationSensorListener)
    }


    private fun loadQuestion() {
        if (questions.peek() == null){
            endGame()
        }
        if (RotationSensorListener.getOrientation() != RotationSensorListener.Orientation.PLAYABLE){
            Handler().postDelayed({
                loadQuestion()
            }, timeForOrientationChange)  //if the device is in the wrong orientation, try again in a moment
            return
        }
        questionFragment.replaceQuestion(questions.poll())
        swapFragments(questionFragment)
    }

    private fun swapFragments(toSwap: Fragment){
        val fragManager = supportFragmentManager
        val transaction =  fragManager.beginTransaction()
        transaction.replace(R.id.activeFragment,toSwap)
        transaction.commit()
    }

    override fun onAnswerGiven(result: QuestionResult) {
        when (result) {
            QuestionResult.PASS -> answerFragment.setAnswer(getString(R.string.question_pass))
            QuestionResult.CORRECT -> answerFragment.setAnswer(getString(R.string.question_correct))
            else -> answerFragment.setAnswer(getString(R.string.question_time_up))
        }
        swapFragments(answerFragment)

        Handler().postDelayed({
            loadQuestion()
        }, timeForResult)  //after specific amount of time we load the next question
    }


    private fun endGame() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

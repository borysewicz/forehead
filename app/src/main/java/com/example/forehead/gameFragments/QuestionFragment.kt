package com.example.forehead.gameFragments

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.forehead.activities.QuestionResult
import com.example.forehead.model.Question
import com.example.forehead.sensor.RotationSensorListener
import android.os.CountDownTimer
import com.example.forehead.R
import com.example.forehead.model.Category
import com.example.forehead.support.SafeClickListener


class QuestionFragment : Fragment(), RotationSensorListener.RotationSensorObserver {

    var category: Category? = null
    private var question: String? = null
    private var tip: String? = null
    private var listener: QuestionFragmentListener? = null
    private val TIME_FOR_QUESTION = 30100L
    private val SECOND_IN_MILIS = 1000L
    private var timer : CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RotationSensorListener.registerListener(this)
        timer = object: CountDownTimer(TIME_FOR_QUESTION, SECOND_IN_MILIS) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished/SECOND_IN_MILIS
                String.format("00:%02d", secondsRemaining)
                view?.findViewById<TextView>(R.id.question_timer_TV)?.text = String.format("00:%02d", secondsRemaining)
            }

            override fun onFinish() {
                listener?.onAnswerGiven(QuestionResult.TIME_PASSED)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        timer?.start()
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.question_question_TV).text = question
        view.findViewById<TextView>(R.id.question_tip_TV).text = tip
        view.findViewById<ConstraintLayout>(R.id.question_fragment_CL).setSafeOnClickListener {
            listener?.onAnswerGiven(QuestionResult.PASS)
        }
        setCategoryName()
    }

    private fun setCategoryName() {
        if (category == null) return
        when (category){
            Category.FICTIONAl -> view?.findViewById<TextView>(R.id.question_category)?.text = getString(R.string.fictional_cat)
            Category.MUSIC -> view?.findViewById<TextView>(R.id.question_category)?.text = getString(R.string.music_cat)
            Category.GEOGRAPHY -> view?.findViewById<TextView>(R.id.question_category)?.text = getString(R.string.geography_cat)
            Category.FOOD -> view?.findViewById<TextView>(R.id.question_category)?.text = getString(R.string.food_cat)
        }
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }

    override fun onRotationChanged(orientation: RotationSensorListener.Orientation) {
        Log.d("ROLL",orientation.toString())
        if (orientation == RotationSensorListener.Orientation.CORRECT_ANSWER){
            listener?.onAnswerGiven(QuestionResult.CORRECT)
        }
    }

    fun replaceQuestion(nextQuestion: Question){
        this.question = nextQuestion.question
        this.tip = nextQuestion.tip
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is QuestionFragmentListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement QuestionFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface QuestionFragmentListener {
        fun onAnswerGiven(result: QuestionResult)
    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {

        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

}

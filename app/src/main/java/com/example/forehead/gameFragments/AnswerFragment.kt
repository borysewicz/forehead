package com.example.forehead.gameFragments

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.forehead.R
import com.example.forehead.activities.QuestionResult

class AnswerFragment : Fragment() {

    private var result: String? = null
    private lateinit var questionRes: QuestionResult

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_answer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.answer_answer_TV).text = result
        setBackground()
    }

    private fun setBackground() {
        when (questionRes){
            QuestionResult.CORRECT -> view?.findViewById<ConstraintLayout>(R.id.answer_fragmemt_CL)?.setBackgroundResource(R.drawable.correct__background)
            QuestionResult.PASS -> view?.findViewById<ConstraintLayout>(R.id.answer_fragmemt_CL)?.setBackgroundResource(R.drawable.pass_background)
            else -> view?.findViewById<ConstraintLayout>(R.id.answer_fragmemt_CL)?.setBackgroundResource(R.drawable.timeup_background)
        }
    }

    fun setAnswer(result: QuestionResult, shownText:String){
        questionRes = result
        this.result = shownText
        }
}



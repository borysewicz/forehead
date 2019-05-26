package com.example.forehead.gameFragments

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.forehead.R
import com.example.forehead.activities.QuestionResult
import com.example.forehead.model.Question

class QuestionFragment : Fragment() {
    private var question: String? = null
    private var tip: String? = null
    private var listener: QuestionFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    fun replaceQuestion(nextQuestion: Question){
        this.question = nextQuestion.question
        this.tip = nextQuestion.tip
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.question_question_TV).text = question
        view.findViewById<TextView>(R.id.question_tip_TV).text = tip
        view.findViewById<ConstraintLayout>(R.id.question_fragment_CL).setOnClickListener {
            listener?.onAnswerGiven(QuestionResult.PASS)
        }
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

}

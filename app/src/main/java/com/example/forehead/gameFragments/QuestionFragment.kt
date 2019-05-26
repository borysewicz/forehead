package com.example.forehead.gameFragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.forehead.R
import com.example.forehead.model.Question

class QuestionFragment : Fragment() {
    private var question: String? = null
    private var tip: String? = null
    private var listener: QuestionFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            question = it.getString(QUESTION_KEY)
            tip = it.getString(TIP_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.question_question_TV).text = "ll"
        view.findViewById<TextView>(R.id.question_tip_TV).text = "kkk"
        view.findViewById<ConstraintLayout>(R.id.question_fragment_CL).setOnClickListener {
            listener?.onAnswerGiven(true)
        }
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is QuestionFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement QuestionFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface QuestionFragmentListener {
        fun onAnswerGiven(result: Boolean)
    }

    companion object {
        const val QUESTION_KEY = "Question"
        const val TIP_KEY = "Tip"

        @JvmStatic
        fun newInstance(question: Question) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(QUESTION_KEY,question.question)
                    putString(TIP_KEY,question.tip)
                }
            }
    }
}

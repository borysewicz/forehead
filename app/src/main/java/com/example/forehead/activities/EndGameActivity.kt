package com.example.forehead.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.forehead.R
import com.example.forehead.activities.GameActivity.Companion.CORR_ANSWERS
import com.example.forehead.activities.GameActivity.Companion.QUESTION_NUMBER_KEY

class EndGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)
        val correct = intent.getIntExtra(CORR_ANSWERS,-1)
        val allQuestions = intent.getIntExtra(QUESTION_NUMBER_KEY,-1)
        findViewById<TextView>(R.id.endgame_corAnswNumber_TV).text = "${correct}/${allQuestions}"
        findViewById<Button>(R.id.endgame_finish_B).setOnClickListener{
            finish()
        }
    }

}

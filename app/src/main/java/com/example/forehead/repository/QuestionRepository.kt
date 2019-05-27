package com.example.forehead.repository

import com.example.forehead.model.Category
import com.example.forehead.model.Question
import java.util.*

interface QuestionRepository {

    fun getQuestions(category: Category, questions: Int) : Queue<Question>
}
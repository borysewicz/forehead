package com.example.forehead.repository

import com.example.forehead.model.Category
import com.example.forehead.model.Question

interface QuestionRepository {

    fun getQuestions(category: Category) : List<Question>
}
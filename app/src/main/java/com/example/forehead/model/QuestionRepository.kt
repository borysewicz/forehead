package com.example.forehead.model

interface QuestionRepository {

    fun getQuestions(category: Category) : List<Question>
}
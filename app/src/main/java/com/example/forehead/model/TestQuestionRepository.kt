package com.example.forehead.model

class TestQuestionRepository : QuestionRepository {
    override fun getQuestions(category: Category): List<Question> {
        return when(category){
            Category.FOOD -> returnFoodQuestions()
            Category.GEOGRAPHY -> returnGeographyQuestions()
            Category.MUSIC -> returnMusicQuestions()
            Category.FICTIONAl -> returnFictionalQuestions()
        }
    }

    private fun returnFictionalQuestions(): List<Question> {
        return listOf()
    }

    private fun returnMusicQuestions(): List<Question> {
        return listOf()
    }

    private fun returnGeographyQuestions(): List<Question> {
        return listOf()
    }

    private fun returnFoodQuestions() : List<Question> {
        return listOf()
    }
}
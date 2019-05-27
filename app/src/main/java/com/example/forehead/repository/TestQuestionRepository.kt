package com.example.forehead.repository

import com.example.forehead.model.Category
import com.example.forehead.model.Question
import java.util.*

class TestQuestionRepository : QuestionRepository {
    override fun getQuestions(category: Category, questions: Int): Queue<Question> {
        return when(category){
            Category.FOOD -> returnFoodQuestions(questions)
            Category.GEOGRAPHY -> returnGeographyQuestions(questions)
            Category.MUSIC -> returnMusicQuestions(questions)
            Category.FICTIONAl -> returnFictionalQuestions(questions)
        }
    }

    private fun returnFictionalQuestions(questions: Int): Queue<Question> {
        val questionQueue = LinkedList<Question>()
        questionQueue.addAll(listOf(
            Question("Thor", "Młot, Avengers"),
            Question("Harry Potter", "Czarodziej, książka"),
            Question("Jon Snow", "Gra o tron"),
            Question("Kaczor Donald", "Zwierzę, komiks"),
            Question("Król Lew", "Zwierzę, bajka"),
            Question("Aragorn", "Wojownik, Władca Pierścieni"),
            Question("Fineasz", "Ferb, bajka"),
            Question("Pokahontas", "Indianka, bajka"),
            Question("Nemo", "Rybka, bajka"),
            Question("Papa smerf", "Niebieski z czerwoną czapką, bajka")
        ))
        return questionQueue
    }

    private fun returnMusicQuestions(questions: Int): Queue<Question> {
        val questionQueue = LinkedList<Question>()
        questionQueue.addAll(listOf(
            Question("Karma Police", "Radiohead"),
            Question("Bohemian Rhapsody", "Queen"),
            Question("Chciałem Być", "Krzysztof Krawczyk"),
            Question("Małomiasteczkowy", "Dawid Podsiadło"),
            Question("Wsiąść do pociągu", "Maryla Rodowicz"),
            Question("Mamma Mia", "Abba"),
            Question("Losing my Religion", "R.E.M"),
            Question("Africa", "Toto"),
            Question("ściernisko", "Golec uOrkiestra"),
            Question("Oj ne ne", "Akcent")
        ))
        return questionQueue
    }

    private fun returnGeographyQuestions(questions: Int): Queue<Question> {
        val questionQueue = LinkedList<Question>()
        questionQueue.addAll(listOf(
            Question("Delhi", "Indie"),
            Question("Mediolan", "Włochy"),
            Question("Morena czołowa", "Lodowiec"),
            Question("Bazalt", "Wulkan"),
            Question("K-2", "Himalaje"),
            Question("Kolorado", "Kanion"),
            Question("Wyspy Wielkanocne", "Posągi"),
            Question("Morze Adriatyckie", "Chorwacja"),
            Question("Sewilla", "Hiszpania"),
            Question("Florencja", "Włochy")
        ))
        return questionQueue
    }

    private fun returnFoodQuestions(questions: Int): Queue<Question> {
        val questionQueue = LinkedList<Question>()
        questionQueue.addAll(listOf(
            Question("Awokado", "Owoc"),
            Question("Produkty zbożowe", "Chleb, Mąka"),
            Question("Sałatka jarzynowa", "święta"),
            Question("Grochówka", "Wojsko"),
            Question("Mirabelka", "Kwaśna, żółta i mała"),
            Question("Tuńczyk", "Duża ryba, kot"),
            Question("Kawior", "Ikra"),
            Question("Nutella", "Słoik, czekolada"),
            Question("Gordon Ramsay", "Kucharz, Szkocja"),
            Question("Magda Gessler", "Kuchenne Rewolucje")
        ))
        return questionQueue
    }
}
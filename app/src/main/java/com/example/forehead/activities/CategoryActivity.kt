package com.example.forehead.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.forehead.R
import com.example.forehead.model.Category

class CategoryActivity : AppCompatActivity() {

   companion object {
       val CAT_KEY = "Category"
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
    }

    override fun onStart() {
        super.onStart()
        setUpButtons()
    }

    private fun setUpButtons() {
        findViewById<ImageButton>(R.id.category_return_IB).setOnClickListener { finish() }
        findViewById<ImageButton>(R.id.category_food).setOnClickListener{startGameActivity(Category.FOOD)}
        findViewById<ImageButton>(R.id.category_geography).setOnClickListener{startGameActivity(Category.GEOGRAPHY)}
        findViewById<ImageButton>(R.id.category_music).setOnClickListener{startGameActivity(Category.MUSIC)}
        findViewById<ImageButton>(R.id.category_fictional).setOnClickListener{startGameActivity(Category.FICTIONAl)}
    }

    private fun startGameActivity(category: Category) {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(CAT_KEY,category)
            startActivity(intent)
    }



    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            hideSystemUI()
        }
        else showSystemUI()
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }


}

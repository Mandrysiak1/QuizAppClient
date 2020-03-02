package com.example.quizzapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : AppCompatActivity(){

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.quizzapp.R.layout.activity_menu)



        newGameCardView.setOnClickListener {
            val intent = Intent(this,LobbyActivity::class.java)
            startActivity(intent)
        }
        rewards.setOnClickListener{
            val intent = Intent(this,ShopActivity::class.java)
            startActivity(intent)
        }


       societies.setOnClickListener{
           val intent = Intent(this, SocietiesActivity::class.java)
           startActivity(intent)
       }


        questions.setOnClickListener {
            val intent = Intent(this,QuestionsActivity::class.java)
            startActivity(intent)
        }

        rankings.setOnClickListener {
            val intent = Intent(this,RankingActivity::class.java)
            startActivity(intent)
        }

        quests.setOnClickListener {
            val intent = Intent(this,QuestActivity::class.java)
            startActivity(intent)
        }
    }


}


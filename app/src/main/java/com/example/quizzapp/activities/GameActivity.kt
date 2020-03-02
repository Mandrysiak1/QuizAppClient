package com.example.quizzapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.quizzapp.Services.NetModule.username
import com.example.quizzapp.Services.WebSocketModule
import com.example.quizzapp.objects.Responses.GameState
import com.example.quizzapp.vm.GameViewModel
import kotlinx.android.synthetic.main.activity_game.*
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.quizzapp.R


class GameActivity : AppCompatActivity(){

    private lateinit var viewModel : GameViewModel
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(com.example.quizzapp.R.layout.activity_game)



        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        viewModel.gameID = intent.getStringExtra("gameID");

     //   WebSocketModule.setGameState(viewModel.currentGameState)

        val gameStateObserver = Observer<GameState>{ it ->
            if(it.roundNumber == 1)
            {

                var keys = RankingStatic.x.keys


                var x = RankingStatic.x.keys?.stream()?.filter { it -> it == username }
                    ?.findFirst()?.orElse("")

                keys?.remove(x)




                player1ID.text = keys?.elementAtOrNull(0)
                player2ID.text = keys?.elementAtOrNull(0)
                player3ID.text = keys?.elementAtOrNull(0)


                if(player1ID.text.isNullOrEmpty()) Player1.visibility  = View.INVISIBLE; !Player1.isClickable
                if(player2ID.text.isNullOrEmpty()) Player2.visibility  = View.INVISIBLE; !Player2.isClickable
                if(player3ID.text.isNullOrEmpty()) Player3.visibility  = View.INVISIBLE; !Player3.isClickable
            }

            for (key in RankingStatic.x.keys) {
                viewModel.playerToInteger[key] = 0
            }
          //  yourScore.setText(it.ranking?.ranking?.get(username)!!)


            if(it.roundNumber == 1){
                yourScoreNumber.text = "10"
                player1Score.text = "Current Score: 10"
                player2Score.text = "Current Score: 10"
                player3Score.text = "Current Score: 10"

            }
            else {
                RankingStatic.x[username].let { yourScoreNumber.text = it.toString() }
            player1Score.text = "Current Score: " +  RankingStatic.x[player1ID.text.toString()].toString()
            player2Score.text = "Current Score: " +  RankingStatic.x[player2ID.text.toString()].toString()
            player3Score.text = "Current Score: " +  RankingStatic.x[player3ID.text.toString()].toString()
            }


            round_number.setText(it.roundNumber.toString() + "/10")


            if(it.roundNumber != 11){

                Question_textView.text = it.question?.text
                AnswerA.text = it.question!!.answers['a']
                AnswerB.text = it.question!!.answers['b']
                AnswerC.text = it.question!!.answers['c']
                AnswerD.text = it.question!!.answers['d']

                resetView()
                startTimer()

            }else{
                QuestionLayout.visibility = View.GONE
                afterGameLayout.visibility = View.VISIBLE

                if(RankingStatic.x[username]!! > RankingStatic.x[player1ID.text.toString()]!!)
                sumUpText.text = " You win with " + RankingStatic.x[username].toString()  + " points!"
                else
                {
                    sumUpText.text = " You lose with " + RankingStatic.x[username].toString() + " points!"
                }

            }



        }
        val gameStartedObserver = Observer<Boolean>{

            if(it)
            startTimer()

        }

     //   WebSocketModule.setGameState(viewModel.currentGameState)


        WebSocketModule.stateGame.observe(this,gameStateObserver)
        viewModel.gameStarted.observe(this,gameStartedObserver);

        setUpOnClick()



    }

    private fun resetView() {
        player1bid.setText("Your Bid: 0")
        player2bid.setText("Your Bid: 0")
        player3bid.setText("Your Bid: 0")
        resetHighligh(null)
    }

    private fun setUpOnClick() {

        AnswerA.setOnClickListener { viewModel.isAnswered = true; viewModel.answer = 'a'; resetHighligh(AnswerA)}
        AnswerB.setOnClickListener { viewModel.isAnswered  = true; viewModel.answer = 'b'; resetHighligh(AnswerB) }
        AnswerC.setOnClickListener { viewModel.isAnswered  = true; viewModel.answer = 'c'; resetHighligh(AnswerC) }
        AnswerD.setOnClickListener { viewModel.isAnswered  = true; viewModel.answer = 'd'; resetHighligh(AnswerD) }

        player1plus.setOnClickListener {
            val key  = player1ID.text.toString()
            viewModel.playerToInteger.compute(key) { _, v -> v?.plus(1) }

            var value = viewModel.playerToInteger[key]
            player1bid.setText(value.toString())
            viewModel.playerToInteger[key]?.let { it1 -> player1bid.setText("Your Bid: " +it1.toString()) }

            if(viewModel.playerToInteger[key]!! < 0 ){
                player1bidFrame.setBackgroundResource(R.drawable.rounded_corner_bad)
            }else if (viewModel.playerToInteger[key]!! >= 0 ){
                player1bidFrame.setBackgroundResource(R.drawable.rounded_corner_good)
            }
        }
        player1minus.setOnClickListener {
            val key  = player1ID.text.toString()
            viewModel.playerToInteger.compute(key) { _, v -> v?.minus(1) }
            viewModel.playerToInteger[key]?.let { it1 -> player1bid.setText("Your Bid: " + it1.toString()) }
            if(viewModel.playerToInteger[key]!! < 0 ){
                player1bidFrame.setBackgroundResource(R.drawable.rounded_corner_bad)
            }else if (viewModel.playerToInteger[key]!! >= 0 ){
                player1bidFrame.setBackgroundResource(R.drawable.rounded_corner_good)
            }
        }
        player1bid.setOnClickListener {
            val key  = player1ID.text.toString()
            viewModel.playerToInteger.compute(key){ _, v ->v?.times(-1) }
            viewModel.playerToInteger[key]?.let { it1 -> player1bid.setText("Your Bid: " +it1.toString())

                if(viewModel.playerToInteger[key]!! < 0 ){
                    player1bidFrame.setBackgroundResource(R.drawable.rounded_corner_bad)
                }else if (viewModel.playerToInteger[key]!! >= 0 ){
                    player1bidFrame.setBackgroundResource(R.drawable.rounded_corner_good)
                }

                }

        }

        player2plus.setOnClickListener {
            val key  = player2ID.text.toString()
            viewModel.playerToInteger.compute(key) { _, v -> v?.plus(1) }
            viewModel.playerToInteger[key]?.let { it1 -> player2bid.setText(it1) }

            if(viewModel.playerToInteger[key]!! < 0 ){
                player2bidFrame.setBackgroundResource(R.drawable.rounded_corner_bad)
            }else if (viewModel.playerToInteger[key]!! >= 0 ){
                player2bidFrame.setBackgroundResource(R.drawable.rounded_corner_good)
            }
        }
        player2minus.setOnClickListener {
            val key  = player2ID.text.toString()
            viewModel.playerToInteger.compute(key) { _, v -> v?.minus(1) }
            viewModel.playerToInteger[key]?.let { it1 -> player2bid.setText(it1) }
            if(viewModel.playerToInteger[key]!! < 0 ){
                player2bidFrame.setBackgroundResource(R.drawable.rounded_corner_bad)
            }else if (viewModel.playerToInteger[key]!! >= 0 ){
                player2bidFrame.setBackgroundResource(R.drawable.rounded_corner_good)
            }
        }
        player2bid.setOnClickListener {
            val key  = player2ID.text.toString()
            viewModel.playerToInteger.compute(key){ _, v ->v?.times(-1) }
            viewModel.playerToInteger[key]?.let { it1 -> player2bid.setText(it1)

                if(viewModel.playerToInteger[key]!! < 0 ){
                    player2bidFrame.setBackgroundResource(R.drawable.rounded_corner_bad)
                }else if (viewModel.playerToInteger[key]!! >= 0 ){
                    player2bidFrame.setBackgroundResource(R.drawable.rounded_corner_good)
                }

            }

        }


        player3plus.setOnClickListener {
            val key  = player3ID.text.toString()
            viewModel.playerToInteger.compute(key) { _, v -> v?.plus(1) }
            viewModel.playerToInteger[key]?.let { it1 -> player3bid.setText(it1) }

            if(viewModel.playerToInteger[key]!! < 0 ){
                player3bidFrame.setBackgroundResource(R.drawable.rounded_corner_bad)
            }else if (viewModel.playerToInteger[key]!! >= 0 ){
                player3bidFrame.setBackgroundResource(R.drawable.rounded_corner_good)
            }
        }
        player3minus.setOnClickListener {
            val key  = player3ID.text.toString()
            viewModel.playerToInteger.compute(key) { _, v -> v?.minus(1) }
            viewModel.playerToInteger[key]?.let { it1 -> player3bid.setText(it1) }
            if(viewModel.playerToInteger[key]!! < 0 ){
                player3bidFrame.setBackgroundResource(R.drawable.rounded_corner_bad)
            }else if (viewModel.playerToInteger[key]!! >= 0 ){
                player3bidFrame.setBackgroundResource(R.drawable.rounded_corner_good)
            }
        }
        player3bid.setOnClickListener {
            val key  = player3ID.text.toString()
            viewModel.playerToInteger.compute(key){ _, v ->v?.times(-1) }
            viewModel.playerToInteger[key]?.let { it1 -> player3bid.setText(it1)

                if(viewModel.playerToInteger[key]!! < 0 ){
                    player3bidFrame.setBackgroundResource(R.drawable.rounded_corner_bad)
                }else if (viewModel.playerToInteger[key]!! >= 0 ){
                    player3bidFrame.setBackgroundResource(R.drawable.rounded_corner_good)
                }

            }

        }

        backtoMenu.setOnClickListener {
            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent) }


    }

    private fun resetHighligh(value: Button?) {
        arrayListOf<Button>(AnswerA,AnswerB,AnswerC,AnswerD).iterator().forEach { e -> e.setBackgroundResource(R.drawable.rounded_corner3) }
        value?.setBackgroundResource(R.drawable.rounded_corner_green)
    }




    fun startTimer() {
        Log.d("timer","in timer")
        timer = object : CountDownTimer(14000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftNumber.text = (millisUntilFinished /1000).toString()
            }
            override fun onFinish() {
                viewModel.sendData()

            }
        }
        timer.start()
    }
}

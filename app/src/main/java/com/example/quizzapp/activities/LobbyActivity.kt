package com.example.quizzapp.activities

import android.R
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.quizzapp.Services.NetModule
import com.example.quizzapp.Services.WebSocketModule
import com.example.quizzapp.objects.LobbyListItem
import com.example.quizzapp.objects.Responses.GameState
import com.example.quizzapp.objects.Responses.LobbyResponse
import com.example.quizzapp.objects.Responses.SocietyResponse
import com.example.quizzapp.objects.SpinnerItem
import com.example.quizzapp.vm.LobbyViewModel
import kotlinx.android.synthetic.main.activity_lobby.*


class LobbyActivity : AppCompatActivity(){

    private lateinit var viewModel : LobbyViewModel
    private lateinit var timer: CountDownTimer

    private var lobbyID:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(com.example.quizzapp.R.layout.activity_lobby)

        viewModel = ViewModelProviders.of(this).get(LobbyViewModel::class.java)

        val SocietiesObserver = Observer<SocietyResponse> { newName ->
            var options =
                viewModel.societies.value?.societiesEntities?.map { it ->SpinnerItem(it.name,it.id) }
                    ?.toCollection(ArrayList())

            val adapter = ArrayAdapter<SpinnerItem>(applicationContext, R.layout.simple_spinner_item, options)

            spinner.adapter = adapter


        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                viewModel.currentSoc.value = spinner.adapter.getItem(position) as SpinnerItem
            }

        }

        val lobbyObserver = Observer<LobbyResponse> {

            var data = it.lobbies.map { e -> LobbyListItem(e.playersNames as ArrayList<String>,e.id) }.toCollection(ArrayList())

            if(data.isNotEmpty())
                societiesList.adapter = LobbyListAdapter(this,data)
        }

        val currentLobbyItemObserver = Observer<LobbyListItem> {

            viewModel.connectToLobby(it)
            viewModel.getLobbies()


        }

        val currentSocObserver = Observer<SpinnerItem> {
            viewModel.getLobbies()
        }

        val startedObserver = Observer<GameState> {

//            if(true)
//            {
//                val intent = Intent(this,GameActivity::class.java)
//                intent.putExtra("gameID", viewModel.currentLobbyItem.value?.id)
//                startActivity(intent)
//            }
        }


        viewModel.lobbies.observe(this,lobbyObserver)
        viewModel.societies.observe(this,SocietiesObserver)
        viewModel.currentLobbyItem.observe(this,currentLobbyItemObserver)
        viewModel.currentSoc.observe(this,currentSocObserver)
        //viewModel.started.observe(this,startedObserver
        WebSocketModule.stateGame.observe(this,startedObserver)

        viewModel.getSocieties()


        newLobby.setOnClickListener{

           // val x = viewModel.societies.value?.societiesEntities?.find { e -> e.name.equals(spinner.selectedItem.toString()) }?.id

            viewModel.createLobby()

        }

        startGame.setOnClickListener{
            //viewModel.currentLobbyItem.value?.let { viewModel.sendData(it) }

            viewModel.currentLobbyItem.value?.id?.let { it1 -> NetModule.startGame(lobbyID)}


            val intent = Intent(this,GameActivity::class.java)
            intent.putExtra("gameID", lobbyID)
            startActivity(intent)



        }



        societiesList.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                run {


                    if(!(societiesList.adapter.getItem(position) as LobbyListItem).list.contains(NetModule.username))
                    {
                        viewModel.addplayer(position)

                    }

                    viewModel.currentLobbyItem.value = societiesList.adapter.getItem(position) as LobbyListItem

                    lobbyID = (societiesList.adapter.getItem(position) as LobbyListItem).id



                }
            }

        WebSocketModule.connectStomp()
        WebSocketModule.stateGame.observe(this,startedObserver)
        //startTimer()
    }


    fun startTimer() {
        Log.d("timer","in timer")
        timer = object : CountDownTimer(600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {

            }
        }
        timer.start()
    }



class LobbyListAdapter(private val context: Activity,
                    private val dataSource: ArrayList<LobbyListItem>) : ArrayAdapter<LobbyListItem>(context,com.example.quizzapp.R.layout.lobby_item) {




    override fun getCount(): Int {
        return dataSource.count()
    }


    override fun getItem(position: Int): LobbyListItem? {
        return dataSource[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


        val inflater = context.layoutInflater
        val rowView = inflater.inflate(com.example.quizzapp.R.layout.lobby_item, null, true)

        val player1 = rowView.findViewById(com.example.quizzapp.R.id.player1) as TextView
        val player2 = rowView.findViewById(com.example.quizzapp.R.id.player2) as TextView
        val player3 = rowView.findViewById(com.example.quizzapp.R.id.player3) as TextView
        val player4 = rowView.findViewById(com.example.quizzapp.R.id.player4) as TextView


        player1.text = dataSource[position].list.elementAtOrNull(0)
        player2.text = dataSource[position].list.elementAtOrNull(1)
        player3.text = dataSource[position].list.elementAtOrNull(2)
        player4.text = dataSource[position].list.elementAtOrNull(3)


        return rowView

    }


}
}
package com.example.quizzapp.activities

import android.R
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.quizzapp.Services.NetModule
import com.example.quizzapp.objects.LobbyListItem
import com.example.quizzapp.objects.Responses.RankingItem
import com.example.quizzapp.objects.Responses.RankingResponse
import com.example.quizzapp.objects.Responses.SocietyResponse
import com.example.quizzapp.objects.SpinnerItem
import com.example.quizzapp.vm.RankingsViewModel
import kotlinx.android.synthetic.main.activity_add_question.view.*
import kotlinx.android.synthetic.main.activity_lobby.*
import kotlinx.android.synthetic.main.activity_rankings.*
import kotlinx.android.synthetic.main.activity_rankings.spinner
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class RankingActivity :AppCompatActivity()
{

    lateinit var viewModel : RankingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(com.example.quizzapp.R.layout.activity_rankings)


        viewModel = ViewModelProviders.of(this).get(RankingsViewModel::class.java)

        val dataObserver = Observer<RankingResponse> { value ->

            val sortedList = value.items.sortedWith(compareBy({ it.points }, { it.userID }))
            RankingList.adapter = RankingsAdpate(this, ArrayList(sortedList.reversed()))
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                viewModel.currentSoc.value = spinner.adapter.getItem(position) as SpinnerItem
            }

        }

        val SocietiesObserver = Observer<SocietyResponse> { newName ->
            var options =
                viewModel.societies.value?.societiesEntities?.map { it ->SpinnerItem(it.name,it.id) }
                    ?.toCollection(ArrayList())

            val adapter = ArrayAdapter<SpinnerItem>(applicationContext, R.layout.simple_spinner_item, options)

            spinner.adapter = adapter

        }

        val currentSocObserver = Observer<SpinnerItem> {
            viewModel.getRankings(it)
        }


        viewModel.societies.observe(this,SocietiesObserver)
        viewModel.currentSoc.observe(this,currentSocObserver)
        viewModel.data.observe(this,dataObserver)

        viewModel.getSocieties()

    }




}

class RankingsAdpate(private val context: Activity,
                       private val dataSource: ArrayList<RankingItem>
) : ArrayAdapter<RankingItem>(context,com.example.quizzapp.R.layout.ranking_item) {




    override fun getCount(): Int {
        return dataSource.count()
    }


    override fun getItem(position: Int): RankingItem {
        return dataSource[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


        val inflater = context.layoutInflater
        val rowView = inflater.inflate(com.example.quizzapp.R.layout.ranking_item, null, true)

        val playerName = rowView.findViewById(com.example.quizzapp.R.id.playerName) as TextView
        val playerPoints = rowView.findViewById(com.example.quizzapp.R.id.PlayerPointsInRanking) as TextView
        val playerPosition = rowView.findViewById(com.example.quizzapp.R.id.playerPosition) as TextView


       // var f = dataSource[position].points?.plus(7)?.times(Random.nextInt(1,4))?.times( Random.nextInt(1,5));

        playerName.text = dataSource[position].userID;
        playerPoints.text = dataSource[position].points.toString()
        playerPosition.text = (position + 1).toString()

        return rowView

    }


}
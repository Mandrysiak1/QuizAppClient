package com.example.quizzapp.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzapp.objects.Responses.RankingItem
import kotlinx.android.synthetic.main.activity_quest.*

class QuestActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(com.example.quizzapp.R.layout.activity_quest)


        var x :ArrayList<RankingItem> = ArrayList()

        x.add(RankingItem("Play 5 Games",100))
        x.add(RankingItem("Add 25 questions",200))
        x.add(RankingItem("Win 3 games",300))

        questList.adapter = QuestAdapter(this,x);
    }
}
class QuestAdapter(private val context: Activity,
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



        playerName.text = dataSource[position].userID;
        playerPoints.text = dataSource[position].points.toString()
        playerPosition.text =""

        return rowView

    }


}

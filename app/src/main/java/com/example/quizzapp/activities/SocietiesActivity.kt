package com.example.quizzapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.quizzapp.R
import com.example.quizzapp.objects.Responses.SocietiesEntity
import com.example.quizzapp.objects.Responses.SocietyResponse
import com.example.quizzapp.vm.SocietyViewModel
import kotlinx.android.synthetic.main.activity_add_question.*
import kotlinx.android.synthetic.main.activity_your_societies.*

class SocietiesActivity : AppCompatActivity()
{

    private lateinit var viewModel : SocietyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_your_societies)

        viewModel = ViewModelProviders.of(this).get(SocietyViewModel::class.java)
//
//        val reponseObserver = Observer<Boolean> {
//            if(it){
//                Toast.makeText(this,"Question added successfully",Toast.LENGTH_LONG)
//                question. text = ""
//                answerA.text = ""
//                answerB.text = ""
//                answerC.text = ""
//                answerD.text = ""
//                answersRadioGroup.clearCheck()
//            }else{
//                Toast.makeText(this,"Adding question failed ",Toast.LENGTH_LONG)
//
//            }
//
//
//        }
//        fun addQuestion(){
//
//           if(answerA.text != "" && answerB.text != "" && answerC.text != "" && answerD.text != "" && question.text != "" )
//           {
//               var question  = Question(answerA.text,answerB.text,answerC.text,answerD.text, question.text)
//               viewModel.addQuestion( QuestionBody(question,socID))
//           }else{
//               Toast.makeText(this,"Fill all fields and mark correct answer", Toast.LENGTH_LONG)
//           }
//        }

        val socObserver= Observer<SocietyResponse> {

           // societiesList.adapter = SocietyListAdapter(this, (viewModel.societies.value as ArrayList<SocietiesEntity>) )


            (viewModel.societies.value as SocietyResponse).societiesEntities

           var options =  ArrayList((viewModel.societies.value as SocietyResponse).societiesEntities)

            societiesList.adapter = SocietyListAdapter(this,options)
        }

        viewModel.societies.observe(this,socObserver)

        societiesList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            run {

                viewModel.selectedSociety.value = societiesList.adapter.getItem(position) as SocietiesEntity

            }
        }

        leaveSoc.setOnClickListener { viewModel.leaveSociety() }

        joinSociety.setOnClickListener {
            val intent = Intent(this,JoinSocietyActivity::class.java)
            startActivity(intent) }

        viewModel.getSocieties()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSocieties()
    }





}
class SocietyListAdapter(private val context: Activity,
                      private val dataSource: ArrayList<SocietiesEntity>) : ArrayAdapter<SocietiesEntity>(context,
    R.layout.society_item) {


    override fun getCount(): Int {
        return dataSource.count()
    }


    override fun getItem(position: Int): SocietiesEntity? {
        return dataSource[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.society_item, null, true)


        val name: TextView = rowView.findViewById(R.id.SocName) as TextView

        name.text = dataSource[position].name

        return rowView

    }
}

package com.example.quizzapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.quizzapp.R
import com.example.quizzapp.objects.LobbyListItem
import com.example.quizzapp.objects.Responses.AllQuestionResponse
import com.example.quizzapp.objects.Responses.SocietyResponse
import com.example.quizzapp.objects.Responses.questions.Question
import com.example.quizzapp.objects.SpinnerItem
import com.example.quizzapp.vm.QuestionViewModel
import kotlinx.android.synthetic.main.activity_lobby.*
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_question.spinner

class QuestionsActivity : AppCompatActivity(){

    private lateinit var viewModel : QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_question)


        viewModel = ViewModelProviders.of(this).get(QuestionViewModel::class.java)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                viewModel.currentSoc.value = spinner.adapter.getItem(position) as SpinnerItem
                viewModel.getAllQuestion()
            }

        }

        val currentSocObserver = Observer<SpinnerItem> { 
            viewModel.getAllQuestion()
        }


        val SocietiesObserver = Observer<SocietyResponse> { newName ->
            var options =
                viewModel.societies.value?.societiesEntities?.map { it ->SpinnerItem(it.name,it.id) }
                    ?.toCollection(ArrayList())

            val adapter = ArrayAdapter<SpinnerItem>(applicationContext, android.R.layout.simple_spinner_item, options)

            spinner.adapter = adapter


        }

        val QuestionSObserver = Observer<AllQuestionResponse> {

            var data = viewModel.questions.value?.questions?.map { it -> it.question }?.toCollection(ArrayList())

            questionsList.adapter = data?.let { it1 -> QuestionListAdapter(this, it1) }
        }

        viewModel.currentSoc.observe(this,currentSocObserver)
        viewModel.societies.observe(this, SocietiesObserver)
        viewModel.questions.observe(this, QuestionSObserver)

        newQuestionButton.setOnClickListener {

            val intent = Intent(this,AddQuestionActivity::class.java)
            intent.putExtra("socID", viewModel.currentSoc.value?.id)
            startActivity(intent)
        }

        questionsList.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                run {

                   Log.d("xx","DOTYK")

                }
            }

        viewModel.getSocieties()

    }


    override fun onResume() {
        super.onResume()
        viewModel.getAllQuestion()
    }



}

class QuestionListAdapter(private val context: Activity,
                          private val dataSource: ArrayList<Question>) : ArrayAdapter<Question>(context,com.example.quizzapp.R.layout.question_item) {



    override fun getCount(): Int {
        return dataSource.count()
    }

    override fun getItem(position: Int): Question? {
        return dataSource[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(com.example.quizzapp.R.layout.question_item, null, true)


        val question= rowView.findViewById(com.example.quizzapp.R.id.Question_textViewItem) as TextView
        val answerA= rowView.findViewById(com.example.quizzapp.R.id.AnswerA_Item) as TextView
        val answerB = rowView.findViewById(com.example.quizzapp.R.id.AnswerB_Item) as TextView
        val answerC = rowView.findViewById(com.example.quizzapp.R.id.AnswerC_Item) as TextView
        val answerD = rowView.findViewById(com.example.quizzapp.R.id.AnswerD_Item) as TextView


        question.text = dataSource[position].text
        answerA.text = dataSource[position].answers.a
        answerB.text = dataSource[position].answers.b
        answerC.text = dataSource[position].answers.c
        answerD.text = dataSource[position].answers.d


        return rowView

    }

}

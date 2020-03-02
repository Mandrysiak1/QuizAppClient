package com.example.quizzapp.activities

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.quizzapp.R
import com.example.quizzapp.objects.Questionb
import com.example.quizzapp.vm.AddQuestionViewModel
import kotlinx.android.synthetic.main.activity_add_question.*

class AddQuestionActivity :AppCompatActivity()
{

    lateinit var viewModel  : AddQuestionViewModel
    var chosenAnswer : Char = '/'

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_add_question)

        viewModel = ViewModelProviders.of(this).get(AddQuestionViewModel::class.java)

        viewModel.socID = intent.getStringExtra("socID")

                    radioGroup.setOnCheckedChangeListener { group, checkedId ->
            run {
                when (checkedId) {
                    R.id.radioButtonA -> chosenAnswer = 'a'
                    R.id.radioButtonB -> chosenAnswer = 'b'
                    R.id.radioButtonC -> chosenAnswer = 'c'
                    R.id.radioButtonD -> chosenAnswer = 'd'
                }

            }
        }




    addQuestionButton.setOnClickListener {
            if(answerA.text.toString() != "" && answerB.text.toString() != "" && answerC.text.toString() != "" && answerD.toString()!= "" && question.text.toString() != "" &&(radioButtonA.isChecked || radioButtonB.isChecked || radioButtonC.isChecked||radioButtonD.isChecked)  )
            {
                var answers = HashMap<Char,String>()
                answers['a'] = answerA.text.toString()
                answers['b'] = answerB.text.toString()
                answers['c'] = answerC.text.toString()
                answers['d'] = answerD.text.toString()
                var questionbody  = Questionb( question.text.toString(),answers,chosenAnswer)
                viewModel.sendQuestion( questionbody)
                Toast.makeText(this,"Question successfully added", Toast.LENGTH_LONG).show()
                answerA.setText("")
                answerB.setText("")
                answerC.setText("")
                answerD.setText("")
                question.setText("")
                radioGroup.clearCheck()

            }else{
                Toast.makeText(this,"Fill all fields and mark correct answer", Toast.LENGTH_LONG).show()
            }
        }
        }

}

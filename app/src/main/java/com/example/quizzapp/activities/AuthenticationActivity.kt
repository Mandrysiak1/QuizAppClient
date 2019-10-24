package com.example.quizzapp.activities

import android.animation.Animator
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizzapp.R
import com.example.quizzapp.vm.AuthenticationViewModel
import kotlinx.android.synthetic.main.activity_main.*


class AuthenticationActivity : AppCompatActivity() {



    private lateinit var viewModel: AuthenticationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        setUpOnClick()


        object : CountDownTimer(5000, 1000) {
            override fun onFinish() {
                bookITextView.visibility = GONE
                loadingProgressBar.visibility = GONE
                rootView.setBackgroundColor(ContextCompat.getColor(this@AuthenticationActivity, R.color.colorSplashText))
                bookIconImageView.setImageResource(R.drawable.background_color_book)
                startAnimation()
            }

            override fun onTick(p0: Long) {}
        }.start()
    }

    private fun setUpOnClick() {

        buttonSignUp.setOnClickListener{
            afterAnimationView.visibility = GONE
            sighupView.visibility = VISIBLE
        }

        buttonsignIn.setOnClickListener{
            afterAnimationView.visibility = VISIBLE
            sighupView.visibility = GONE
        }

        button_forgetPasswd.setOnClickListener {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
        }

        loginButton.setOnClickListener{
            viewModel.authenticate(LoginEmailEditText.text.toString(),LoginPasswordEditText.text.toString())
        }







    }

    private fun startAnimation() {
        bookIconImageView.animate().apply {
            x(50f)
            y(100f)
            duration = 1000
        }.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                afterAnimationView.visibility = VISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }
        })

    }






}

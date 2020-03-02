package com.example.quizzapp.activities

import android.animation.Animator
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.quizzapp.vm.AuthenticationViewModel
import kotlinx.android.synthetic.main.activity_main.*


class AuthenticationActivity : AppCompatActivity() {


    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var pref : SharedPreferences
    private lateinit var editor :SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(com.example.quizzapp.R.layout.activity_main)
        pref = applicationContext.getSharedPreferences("MyPref", 0) // 0 - for private mode
        editor = pref.edit()
        viewModel = ViewModelProviders.of(this).get(AuthenticationViewModel::class.java)

        setUpOnClick()

        val LoginObserver = Observer<String> { newName ->
            // Update the UI, in this case, a TextView.
            LoginEmailEditText.setText(newName)
        }
        val PasswdObserver = Observer<String> { newName ->
            // Update the UI, in this case, a TextView.
            LoginPasswordEditText.setText(newName)
            }
            val isLoggedObserver = Observer<Boolean> { value ->
                if(!value)
                    Toast.makeText(this,"Błąd logowania",Toast.LENGTH_LONG).show()
                else{
                    val intent = Intent(this,MenuActivity::class.java)
                    startActivity(intent)
                }
            }
        val isRegisteredObserver = Observer<Boolean> { value ->
            if(value)
            {
                afterAnimationView.visibility = VISIBLE
                sighupView.visibility = GONE
                Toast.makeText(this,"User registered successfully, please log in now", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Register failed, email already taken", Toast.LENGTH_LONG).show()
            }

        }
        viewModel.login.observe(this,LoginObserver)
        viewModel.passwd.observe(this,PasswdObserver)
        viewModel.isLogged.observe(this,isLoggedObserver)
        viewModel.isRegistered.observe(this,isRegisteredObserver)

        viewModel.login.value= pref.getString("login",null)
        viewModel.passwd.value = pref.getString("passwd",null)


        object : CountDownTimer(5000, 1000) {
            override fun onFinish() {
                bookITextView.visibility = GONE
                loadingProgressBar.visibility = GONE
                rootView.setBackgroundColor(ContextCompat.getColor(this@AuthenticationActivity, com.example.quizzapp.R.color.colorSplashText))
                bookIconImageView.setImageResource(com.example.quizzapp.R.drawable.background_color_book)
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

        loginButton2.setOnClickListener{
            viewModel.login.value= LoginEmailEditText.text?.replace("\\s".toRegex(), "").toString();
            viewModel.passwd.value = LoginPasswordEditText.text?.replace("\\s".toRegex(), "").toString();
            viewModel.authenticate()
            editor.putString("login",viewModel.login.value.toString())
            editor.putString("passwd",viewModel.passwd.value.toString())
            editor.commit()

        }

        registerButton.setOnClickListener{
            if(RegisterPasswordEditText.text.toString() != RegisterConfirmPassword2EditText.text.toString()) {
                Toast.makeText(this,"Podane hasła nie są takie same",Toast.LENGTH_LONG).show()
            }
            else{
                viewModel.register(RegisterEmailEditText.text?.replace("\\s".toRegex(), "").toString(),
                    RegisterPasswordEditText.text?.replace("\\s".toRegex(), "").toString())}
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

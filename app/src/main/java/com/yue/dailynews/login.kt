package com.yue.dailynews

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.yue.dailynews.common.Util
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {

    private lateinit var mUsernameEditText: EditText
    private lateinit var mPasswordEditText: EditText
    private lateinit var mSubmitButton: Button
    private lateinit var mRegisterButton: Button

    private lateinit var progressBar: ProgressBar
    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Util.setAppLocale(Util.language,resources)

        val preferences: SharedPreferences = getSharedPreferences("dailynews", Context.MODE_PRIVATE)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        Log.d("login", "onCreate called")

        progressBar = findViewById(R.id.progressBar)

        mUsernameEditText = findViewById(R.id.editTextLogin) as EditText
        mPasswordEditText = findViewById(R.id.editTextPassword) as EditText
        mSubmitButton = findViewById(R.id.submit) as Button
        mRegisterButton = findViewById(R.id.register) as Button

        progressBar.visibility = View.INVISIBLE

        mUsernameEditText.setText(preferences.getString("SAVED_USERNAME", ""))
        mPasswordEditText.setText(preferences.getString("SAVED_PASSWORD",""))
        mUsernameEditText.addTextChangedListener(textWatcher)
        mPasswordEditText.addTextChangedListener(textWatcher)


        // An OnClickListener is an interface with a single function, so you can use lambda-shorthand
        // The lambda is called when the user pressed the button
        // https://developer.android.com/reference/android/view/View.OnClickListener
        mUsernameEditText.setOnClickListener{
            mPasswordEditText.setText("")
        }

        mPasswordEditText.setOnClickListener{
            mPasswordEditText.setText("")
        }

        mSubmitButton.setOnClickListener {
            //            sleep(1000)
//            firebaseAnalytics.logEvent("login_clicked", null)
            progressBar.visibility = View.VISIBLE
            val inputtedUsername: String = mUsernameEditText.text.toString().trim()
            val inputtedPassword: String = mPasswordEditText.text.toString()

            firebaseAuth
                .signInWithEmailAndPassword(inputtedUsername, inputtedPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser: FirebaseUser? = firebaseAuth.currentUser
                        val email = currentUser?.email
                        Toast.makeText(this, "Logged in as: $email", Toast.LENGTH_SHORT).show()

                        // Save the inputted username to file
                        preferences
                            .edit()
                            .putString("SAVED_USERNAME", mUsernameEditText.text.toString().trim())
                            .apply()
                        preferences
                            .edit()
                            .putString("SAVED_PASSWORD", mPasswordEditText.text.toString())
                            .apply()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        val exception = task.exception

                        // Example of logging some extra metadata (the error reason) with our analytic
                        val reason =
                            if (exception is FirebaseAuthInvalidCredentialsException) "invalid_credentials" else "connection_failure"
                        val bundle = Bundle()
                        bundle.putString("error_type", reason)

                        Toast.makeText(this, "Registration failed: $exception", Toast.LENGTH_SHORT)
                            .show()

                        progressBar.visibility = View.INVISIBLE

                    }
                }
        }

        mRegisterButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val intent = Intent(this, Register::class.java)
            startActivity(intent)


        }
    }

    override fun onResume() {
        super.onResume()
        Util.setAppLocale(Util.language,resources)

    }
        // A TextWatcher is an interface with three functions, so we cannot use lambda-shorthand
    // The functions are called accordingly as the user types in the EditText
    // https://developer.android.com/reference/android/text/TextWatcher
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(newString: CharSequence, start: Int, before: Int, count: Int) {
            val inputtedUsername: String = mUsernameEditText.text.toString().trim()
            val inputtedPassword: String = mPasswordEditText.text.toString().trim()
            val enabled: Boolean = inputtedUsername.isNotEmpty() && inputtedPassword.isNotEmpty()

            // Kotlin shorthand for login.setEnabled(enabled)
            mSubmitButton.isEnabled = enabled
            mRegisterButton.isEnabled = enabled
        }
    }
}

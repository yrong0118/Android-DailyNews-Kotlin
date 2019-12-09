package com.yue.dailynews

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.yue.dailynews.common.Util
import com.yue.dailynews.common.Util.Companion.isStringEmpty


class Register : AppCompatActivity() {

    private lateinit var mUsernameEditText: EditText
    private lateinit var mPasswordEditText: EditText
    private lateinit var mPasswordEditTextConfrim: EditText
    private lateinit var mGoBackButton: TextView
    private lateinit var mRegisterButton: Button

    private lateinit var progressBar: ProgressBar
    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Util.setAppLocale(Util.language,resources)

        setContentView(R.layout.activity_register)


        firebaseAuth = FirebaseAuth.getInstance()
        Log.d("login", "onCreate called")
        mUsernameEditText = findViewById(R.id.editTextLoginC) as EditText
        mPasswordEditText = findViewById(R.id.editTextPasswordC) as EditText
        mPasswordEditTextConfrim = findViewById(R.id.editTextPasswordConfirm) as EditText
        progressBar = findViewById(R.id.progressBarC)
        mGoBackButton = findViewById(R.id.submitC)
        mRegisterButton = findViewById(R.id.registerC) as Button

        progressBar.visibility = View.INVISIBLE
        mUsernameEditText.addTextChangedListener(textWatcher)
        mPasswordEditText.addTextChangedListener(textWatcher)
        mPasswordEditTextConfrim.addTextChangedListener(textWatcher)

        // An OnClickListener is an interface with a single function, so you can use lambda-shorthand
        // The lambda is called when the user pressed the button
        // https://developer.android.com/reference/android/view/View.OnClickListener
        mUsernameEditText.setOnClickListener{
            mPasswordEditText.setText("")
            mPasswordEditTextConfrim.setText("")

        }
        mPasswordEditText.setOnClickListener{
            mPasswordEditText.setText("")
            mPasswordEditTextConfrim.setText("")
        }
        mPasswordEditTextConfrim.setOnClickListener{
            mPasswordEditTextConfrim.setText("")
        }

        mGoBackButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        mRegisterButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val inputtedUsername: String = mUsernameEditText.text.toString().trim()
            val inputtedPassword: String = mPasswordEditText.text.toString()
            val inputtedPasswordConfirm: String = mPasswordEditTextConfrim.text.toString()
            if (isStringEmpty(inputtedPassword) || isStringEmpty(inputtedPasswordConfirm) || isStringEmpty(inputtedUsername) || inputtedPassword != inputtedPasswordConfirm){
                mPasswordEditText.setText("")
                mPasswordEditTextConfrim.setText("")
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }else{
                firebaseAuth
                    .createUserWithEmailAndPassword(inputtedUsername, inputtedPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUser: FirebaseUser? = firebaseAuth.currentUser
                            val email = currentUser?.email
                            Toast.makeText(this, "Registered as $email", Toast.LENGTH_SHORT).show()
                            firebaseAuth.signInWithEmailAndPassword(inputtedUsername, inputtedPassword)
                                .addOnCompleteListener{task->
                                    if (task.isSuccessful) {
                                        val currentUser: FirebaseUser? = firebaseAuth.currentUser
                                        val email = currentUser?.email
                                        Toast.makeText(this, "Logged in as: $email", Toast.LENGTH_SHORT).show()

                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                    }else {
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

                        } else {
                            val exception = task.exception
                            Toast.makeText(this, "Registration failed: $exception", Toast.LENGTH_SHORT).show()
                            mUsernameEditText.setText("")
                            mPasswordEditText.setText("")
                            mPasswordEditTextConfrim.setText("")
                            progressBar.visibility = View.INVISIBLE
                        }
                    }



            }

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
            mGoBackButton.isEnabled = enabled
            mRegisterButton.isEnabled = enabled
        }
    }
}



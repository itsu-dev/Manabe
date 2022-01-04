package dev.itsu.manabe.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import dev.itsu.manabe.R
import dev.itsu.manabe.api.Manaba
import dev.itsu.manabe.utils.Preferences
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        actionBar?.hide()

        findViewById<Button>(R.id.login_button).setOnClickListener {
            doLogin()
        }

        val credentials = Preferences.getCredentials()
        if (credentials.first.isNotEmpty() && credentials.second.isNotEmpty()) {
            setUserId(credentials.first)
            setPassword(credentials.second)
            doLogin()
        }
    }

    private fun doLogin() {
        if (getUserId().isNotEmpty() and getPassword().isNotEmpty()) {
            GlobalScope.launch(Dispatchers.Main) {
                setLoggingIn(true)
                withContext(Dispatchers.IO) {
                    Manaba.login(getUserId(), getPassword())
                }.let {
                    if (it) {
                        if (isChecked()) {
                            Preferences.setCredentials(getUserId(), getPassword())
                        }
                        startActivity(
                            Intent(this@LoginActivity, MainActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                        )
                    } else {
                        setLoggingIn(false)
                        showErrorMessage()
                    }
                }
            }
        }
    }

    fun getUserId() = findViewById<EditText>(R.id.login_user_id).text.toString()

    fun getPassword() = findViewById<EditText>(R.id.login_password).text.toString()

    fun setUserId(userId: String) = findViewById<EditText>(R.id.login_user_id).setText(userId)

    fun setPassword(password: String) = findViewById<EditText>(R.id.login_password).setText(password)

    fun isChecked() = findViewById<CheckBox>(R.id.login_checkbox).isChecked

    fun setLoggingIn(loggingIn: Boolean) {
        if (loggingIn) {
            findViewById<EditText>(R.id.login_user_id).isEnabled = false
            findViewById<EditText>(R.id.login_password).isEnabled = false
            findViewById<CheckBox>(R.id.login_checkbox).isEnabled = false
            findViewById<ProgressBar>(R.id.login_progress).visibility = View.VISIBLE
            findViewById<TextView>(R.id.login_error_text).visibility = View.GONE
            findViewById<Button>(R.id.login_button).visibility = View.GONE

        } else {
            findViewById<EditText>(R.id.login_user_id).isEnabled = true
            findViewById<EditText>(R.id.login_password).isEnabled = true
            findViewById<CheckBox>(R.id.login_checkbox).isEnabled = true
            findViewById<ProgressBar>(R.id.login_progress).visibility = View.GONE
            findViewById<TextView>(R.id.login_error_text).visibility = View.GONE
            findViewById<Button>(R.id.login_button).visibility = View.VISIBLE
        }
    }

    fun showErrorMessage() {
        findViewById<TextView>(R.id.login_error_text).visibility = View.VISIBLE
    }
}
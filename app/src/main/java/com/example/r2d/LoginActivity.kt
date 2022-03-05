package com.example.r2d

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.r2d.requestresponse.ApiAdapter
import com.example.r2d.requestresponse.Const
import com.example.r2d.utils.AlertDialogHelper
import com.example.r2d.utils.Progress
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private var Email: EditText? = null
    private var Password: EditText? = null
    private var Login: Button? = null
    private var passwordreset: TextView? = null
    private var passwordresetemail: EditText? = null
    private var progressBar: ProgressBar? = null

    private var processDialog: ProgressDialog? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Email = findViewById(R.id.emailSignIn) as EditText?
        Password = findViewById(R.id.password) as EditText?
        Login = findViewById(R.id.Login) as Button?
        passwordreset = findViewById(R.id.forgotpassword)
        passwordresetemail = findViewById(R.id.emailSignIn)
        progressBar = findViewById(R.id.progressbars) as ProgressBar?
        progressBar!!.visibility = View.GONE

        processDialog = ProgressDialog(this)
        Login!!.setOnClickListener {
            //callLoginApi()
            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
        }
        passwordreset!!.setOnClickListener {  }
    }

 /*   fun resetpasword() {
        val resetemail = passwordresetemail!!.text.toString()
        if (resetemail.isEmpty()) {
            passwordresetemail!!.error = "It's empty"
            passwordresetemail!!.requestFocus()
            return
        }
        progressBar!!.visibility = View.VISIBLE

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@LoginActivity,
                        "We have sent you instructions to reset your password!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Failed to send reset email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                progressBar!!.visibility = View.GONE
            }
    }*/


    // call login api
    private fun callLoginApi() {

        try {
            ApiAdapter.getInstance(this)
            Progress.start(this)
            callLoginApi("123465795","clmkxcm")
        } catch (e: ApiAdapter.Companion.NoInternetException) {
            AlertDialogHelper.alertInternetError(this){
                callLoginApi()
            }
        }
    }

    fun callLoginApi(number : String, password : String) {

        try {
            var jsonObject: JSONObject? = null
            try {
                jsonObject = JSONObject()

                jsonObject.put(Const.PARAM_NUMBER, number)
                jsonObject.put(Const.PARAM_PASSWORD, password)


            } catch (ex: JSONException) {
                ex.printStackTrace()
            }

            val body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            val getResult =
                ApiAdapter.getApiService()!!.loginResponse("application/json", "no-cache", body)

            getResult.enqueue(object : Callback<LoginResponseModel> {
                override fun onResponse(
                    call: Call<LoginResponseModel>,
                    response: Response<LoginResponseModel>
                ) {

                    try {

                        val loginResponse = response.body()

                        if (loginResponse != null) {
                           // login success
                            Toast.makeText(this@LoginActivity, "success", Toast.LENGTH_LONG).show()

                        }
                    } catch (ex: NullPointerException) {
                        ex.printStackTrace()
                        // login fail
                        Toast.makeText(this@LoginActivity, "fail", Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                    t.printStackTrace()
                    // login fail
                    Toast.makeText(this@LoginActivity, "fail", Toast.LENGTH_LONG).show()
                }
            })
        } catch (e: ApiAdapter.Companion.NoInternetException) {
            // login fail
            Toast.makeText(this@LoginActivity, "fail", Toast.LENGTH_LONG).show()
        }
    }
}

class LoginResponseModel{

}

package com.ankit.basechurch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.ankit.basechurch.R
import com.ankit.basechurch.util.debugger
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userLogin.setOnClickListener(View.OnClickListener {
            loginUser()
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
            finish()
    }
    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser?.email
        if (currentUser != null) {
            debugger("Current user$currentUser")
            updateUI(currentUser)
        }
    }
    private fun updateUI(user: String?){
        debugger("Cureent uaer->>>>>$user")
        if(user!=null) {
            val snackbar = Snackbar.make(LinearLayout, "Signin as$user", Snackbar.LENGTH_INDEFINITE)
            snackbar.show()
            snackbar.apply {
                duration = BaseTransientBottomBar.LENGTH_SHORT
                show()
            }


            Handler().postDelayed({
                val intent=Intent(this@Login, MainActivity::class.java)
                intent.putExtra( "authName",user)
                startActivity(intent)
                finish()
            },4000)

        }
        else{

        }


    }

    private fun loginUser() {
        val Lname=Loginname.text.toString()
        val Lpassword=LoginPassword.text.toString()
        when{
            TextUtils.isEmpty(Lname)-> Toast.makeText(this,"Empty Name Field", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(Lpassword)-> Toast.makeText(this,"Empty Password Field", Toast.LENGTH_SHORT).show()
            else->{
                llProgressBar.visibility=View.VISIBLE
                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(Lname,Lpassword).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val intent= Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        val message=task.exception!!.toString()
                        Toast.makeText(this,"Error$message", Toast.LENGTH_SHORT).show()
                        FirebaseAuth.getInstance().signOut()
                        llProgressBar.visibility=View.GONE
                    }    }

            }
        }
    }
}

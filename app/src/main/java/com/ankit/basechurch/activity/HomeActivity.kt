package com.ankit.basechurch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.ankit.basechurch.R
import com.ankit.basechurch.util.debugger
import com.ankit.basechurch.util.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_register.signUp
import kotlinx.android.synthetic.main.fragment_register.userEmail
import kotlinx.android.synthetic.main.fragment_register.userName
import kotlinx.android.synthetic.main.fragment_register.userPassword

class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        signUp.setOnClickListener(View.OnClickListener {
            createAccount()
        })
        Loin.setOnClickListener(View.OnClickListener {
            val intent=Intent(this@HomeActivity, Login::class.java)
            startActivity(intent)
        })

    }
    private fun createAccount(){
        val name=userName.text.toString()
        val email=userEmail.text.toString()
        val password=userPassword.text.toString()
        when{
            TextUtils.isEmpty(name)-> Toast.makeText(this,"UserName is Empty", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(email)-> Toast.makeText(this,"Email is empty,", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(password)-> Toast.makeText(this,"Passowrd is empty", Toast.LENGTH_SHORT).show()
            else ->{
                val mAuth:FirebaseAuth= FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            saveUserInfo(email,password)
                        }
                        else{
                            val message=task.exception!!.toString()
                            debugger("error:$message")
                            Toast.makeText(this,"Error:$message", Toast.LENGTH_SHORT).show()
                            mAuth.signOut()
                        }
                    }
            }
        }
    }

    private fun saveUserInfo(email: String, password: String) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val user= User(
            FirebaseAuth.getInstance().currentUser!!.uid,
            userName.text.toString(),
            userEmail.text.toString(),
            userPassword.text.toString()
        )
        userRef.child(currentUserID).setValue(user).addOnCompleteListener {
            val intent= Intent(this@HomeActivity, MainActivity::class.java)
            intent.putExtra("cuser",currentUserID)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

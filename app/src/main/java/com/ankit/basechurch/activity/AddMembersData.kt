package com.ankit.basechurch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.ankit.basechurch.R
import com.ankit.basechurch.util.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_members_data.*

class AddMembersData : AppCompatActivity() {
    val ref= FirebaseDatabase.getInstance().reference.child("Members")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_members_data)
        AddMember.setOnClickListener(View.OnClickListener {
            MemberButtom()
        })


    }

    private fun MemberButtom(){
        val Firstpayee=FirstNameInput.text.toString()
        val Lastpayee=LastNameInput.text.toString()
        val phoneinputs=MemberPhoneInput.text.toString()

        when{
            TextUtils.isEmpty(Firstpayee)-> Toast.makeText(this,"ENTER PAYEE'S NAME",
                Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(Lastpayee)-> Toast.makeText(this,"ENTER DATE", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(phoneinputs)-> Toast.makeText(this,"ENTER AMOUNT", Toast.LENGTH_SHORT).show()


            else->{

                val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
                val UserID:String= ref.push().key!!
                var TT= User(
                    UserID,
                    FirstNameInput.text.toString(),
                    LastNameInput.text.toString(),
                    MemberPhoneInput.text.toString()
                    //formatted
                    //formatted
                )
                ref.child(UserID).setValue(TT).addOnCompleteListener {

                }
            }
        }
    }
}

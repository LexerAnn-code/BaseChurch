package com.ankit.basechurch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.ankit.basechurch.R
import com.ankit.basechurch.util.Tithes
import com.ankit.basechurch.util.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tithes_edit.*
import kotlinx.android.synthetic.main.fragment_tithes2.*

class TitheEditActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_POST = "extra_post"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tithes_edit)

        val ttb= AnimationUtils.loadAnimation(this, R.anim.bbt)
        val ttb2= AnimationUtils.loadAnimation(this, R.anim.rrts)
        fabUp.startAnimation(ttb)
        fabDel.startAnimation(ttb)
        TithesCardEdit.startAnimation(ttb2)


//        if (intent.hasExtra(EditActivity.EXTRA_POST)) {
//            val editable = intent.getParcelableExtra<User>(EditActivity.EXTRA_POST)
//            TithesCashEdit.setText(editable?.TitheAmount)
//            TithesDateEdit.setText(editable?.TitheDate)
//        }
        fabInsert.setOnClickListener(View.OnClickListener {
            writeTithes()
        })

        fabUp.setOnClickListener(View.OnClickListener {
            upDate()
            onBackPressed()
        })
        fabDel.setOnClickListener(View.OnClickListener {
            delete()
        })

    }
    private fun writeTithes(){

        when{
            TextUtils.isEmpty(TithesCashEdit.text.toString())-> Toast.makeText(this@TitheEditActivity,"Enter your Title",
                Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(TithesDateEdit.text.toString())-> Toast.makeText(this@TitheEditActivity,"Enter your Body",
                Toast.LENGTH_LONG).show()
            else->{
                val ref= FirebaseDatabase.getInstance().reference.child("Tithes")
               // val tto = intent.getParcelableExtra<User>(EditActivity.EXTRA_POST)
                if(intent.hasExtra(EXTRA_POST)){
                    val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
                    val tith = intent.getParcelableExtra<User>(EXTRA_POST)
                val TithesID:String= ref.push().key!!

                var TT= Tithes(
                    TithesID,
                    TithesCashEdit.text.toString(),
                    TithesDateEdit.text.toString(),
                     tith.uid,
                    tith.userLastName
                    //formatted
                    //formatted
                )
                ref.child(TithesID).setValue(TT).addOnCompleteListener {

                }
                    }
                }
            }
        }
    private fun upDate(){
        when{
            TextUtils.isEmpty(TithesCashEdit.text.toString())-> Toast.makeText(this@TitheEditActivity,"Enter your Title",
                Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(TithesDateEdit.text.toString())-> Toast.makeText(this@TitheEditActivity,"Enter your Body",
                Toast.LENGTH_LONG).show()
            else->{
                val ref= FirebaseDatabase.getInstance().reference.child("Tithes")
                val tto = intent.getParcelableExtra<Tithes>(EditActivity.EXTRA_POST)
                if(intent.hasExtra(EXTRA_POST)){
                    ref.child(tto.id).updateChildren(Tithes.toTithesHasMap(tto.apply {
                        this.TitheAmount=TithesCashEdit.text.toString()
                        this.TitheDate=TithesDateEdit.text.toString()
                    })).addOnCompleteListener {
                        Toast.makeText(this,"Updated", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun delete(){
        val refs= FirebaseDatabase.getInstance().reference.child("Tithes")
        val offs = intent.getParcelableExtra<Tithes>(EXTRA_POST)
        if(intent.hasExtra(EXTRA_POST)){
            refs.child(offs.id).removeValue().addOnCompleteListener {
                Toast.makeText(this,"Data Deleted Successfully",Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }
}

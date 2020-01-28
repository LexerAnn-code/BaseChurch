package com.ankit.basechurch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.ankit.basechurch.R
import com.ankit.basechurch.util.Offering
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_POST = "extra_post"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val ttb=AnimationUtils.loadAnimation(this, R.anim.bbt)
        val ttb2=AnimationUtils.loadAnimation(this, R.anim.rrts)
        fab.startAnimation(ttb)
        fab2.startAnimation(ttb)
        OfferingCard.startAnimation(ttb2)


        if (intent.hasExtra(EXTRA_POST)) {
            val editable = intent.getParcelableExtra<Offering>(EXTRA_POST)
            OfferingCash.setText(editable?.OfferingAmount)
            OfferingDate.setText(editable?.Date)
        }



        fab.setOnClickListener(View.OnClickListener {
            upDate()
            onBackPressed()
        })
        fab2.setOnClickListener(View.OnClickListener {
            delete()
        })
    }
    private fun saveTithes(){

//        val tpayee=TPayeeLayInput.text.toString()
//        val ainputs=TAmountInput.text.toString()
//        val dinputs=TDateInput.text.toString()
//
//        when{
//            TextUtils.isEmpty(tpayee)-> Toast.makeText(activity,"ENTER PAYEE'S NAME",
//                Toast.LENGTH_SHORT).show()
//            TextUtils.isEmpty(dinputs)-> Toast.makeText(activity,"ENTER DATE", Toast.LENGTH_SHORT).show()
//            TextUtils.isEmpty(ainputs)-> Toast.makeText(activity,"ENTER AMOUNT", Toast.LENGTH_SHORT).show()
//
//
//            else->{
//
//                val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
//                val TithesID:String= ref.push().key!!
//                var TT= Tithes(
//                    TithesID,
//                    TAmountInput.text.toString(),
//                    TDateInput.text.toString(),
//                    TPayeeLayInput.text.toString()
//                    //formatted
//                    //formatted
//                )
//                ref.child(TithesID).setValue(TT).addOnCompleteListener {
//
//                }
//            }
//        }
//    }
    }
    private fun upDate(){
when{
    TextUtils.isEmpty(OfferingCash.text.toString())-> Toast.makeText(this@EditActivity,"Enter your Title",
        Toast.LENGTH_LONG).show()
    TextUtils.isEmpty(OfferingCash.text.toString())-> Toast.makeText(this@EditActivity,"Enter your Body",
        Toast.LENGTH_LONG).show()
else->{
   val ref= FirebaseDatabase.getInstance().reference.child("Offerings")
    val off = intent.getParcelableExtra<Offering>(EXTRA_POST)
    if(intent.hasExtra(EXTRA_POST)){
        ref.child(off.id).updateChildren(Offering.toHashMap(off.apply {
            this.OfferingAmount=OfferingCash.text.toString()
            this.Date=OfferingDate.text.toString()
        })).addOnCompleteListener {
            Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show()
        }
    }
}
}


    }
    private fun delete(){
        val refs= FirebaseDatabase.getInstance().reference.child("Offerings")
        val offs = intent.getParcelableExtra<Offering>(EXTRA_POST)
        if(intent.hasExtra(EXTRA_POST)){
            refs.child(offs.id).removeValue().addOnCompleteListener {
                Toast.makeText(this,"Data Deleted Successfully",Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }


    }
}

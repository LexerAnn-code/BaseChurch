package com.ankit.basechurch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankit.basechurch.R
import com.ankit.basechurch.util.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_detail_member.*
import kotlinx.android.synthetic.main.fragment_search.*

class DetailMember : AppCompatActivity() {

    private lateinit var adapter: RecyclerMemberDetailAdapter
    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    companion object {
        const val EXTRA_POST = "extra_post"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_member)
        val memb = intent.getParcelableExtra<User>(TitheEditActivity.EXTRA_POST)
       mFirstName.setText(memb.userFirstName)
        mSecondName.setText(memb.userLastName)
        mPhone.setText(memb.userPhone)
        debugger("DataPassed->>${memb.userFirstName}")
    setupList()
        updatePost()
    }
    private fun setupList() {
        adapter = RecyclerMemberDetailAdapter(this@DetailMember)
        mBerRecycler.apply {
            // Adapter
            this.adapter = this@DetailMember.adapter
            // Layout Manager
            val LinearLayouts = LinearLayoutManager(this@DetailMember)
            //val GridLayouts= GridLayoutManager(activity,2, GridLayoutManager.VERTICAL,false)

            this.layoutManager = LinearLayouts
        }

    }
    private fun updatePost(){
        val TRef=database.reference.child("Tithes").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {

                if(p0.exists()){
                    val tithesLists= mutableListOf<Tithes>()
                    p0.children.forEach {
                        val tithesListItems = it.getValue(Tithes::class.java)
                        val membs = intent.getParcelableExtra<User>(TitheEditActivity.EXTRA_POST)
                        if (membs.uid == tithesListItems!!.TithesPid) {
                            tithesLists.add(tithesListItems!!)
                        }
                        debugger("Tithes->>${tithesListItems.TithesPid}")
                    }
                    adapter.submitList(tithesLists)
                }
                //if(sum==null){
            }

        })
    }






}

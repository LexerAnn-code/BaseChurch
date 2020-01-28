package com.ankit.basechurch.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankit.basechurch.R
import com.ankit.basechurch.util.RecyclerTithesAdapter
import com.ankit.basechurch.util.Tithes
import com.ankit.basechurch.util.User
import com.ankit.basechurch.util.debugger
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_tithes2.*
import kotlinx.android.synthetic.main.fragment_tithes2.view.*

/**
 * A simple [Fragment] subclass.
 */
class TithesFragment : Fragment() {
    private var sum:Double=0.00
    private lateinit var adapter: RecyclerTithesAdapter
    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    val ref= FirebaseDatabase.getInstance().reference.child("Tithes")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_tithes2, container, false)
        // Inflate the layout for this fragment
        view.TPayeeLayInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (TPayeeLayInput.text.toString() == "") {

                } else {
                    searchUser(s.toString())


                }
            }

        })
       updatePost()
        return view


}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // setupList()
        addTithesButton.setOnClickListener(View.OnClickListener {
    //TithesButtom()
})

    }
    private fun searchUser(input: String) {
        val titheRef = database.reference.child("Members").orderByChild("userFirstName").startAt(input)
            .endAt(input + "\uf8ff").addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        debugger("Passed->$input")
                        val searchList = mutableListOf<User>()
                        p0.children.forEach {
                            val searchListItem = it.getValue(User::class.java)
                            searchList.add(searchListItem!!)
                        }
                        adapter.submitList(searchList)
                    }
                }
            })
    }
//    private fun TithesButtom(){
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupList()
    }
    private fun setupList() {
        adapter = RecyclerTithesAdapter(this@TithesFragment.requireActivity())
        recyclerTithes.apply {
            // Adapter
            this.adapter = this@TithesFragment.adapter
            // Layout Manager
            val LinearLayouts= LinearLayoutManager(this@TithesFragment.requireContext())
            //val GridLayouts= GridLayoutManager(activity,2, GridLayoutManager.VERTICAL,false)

            this.layoutManager = LinearLayouts
        }


    }


    private fun updatePost(){
        val TRef=database.reference.child("Members").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {



                if(p0.exists()){
                    val tithesLists= mutableListOf<User>()
                    p0.children.forEach {
                        val tithesListItems=it.getValue(User::class.java)


                        tithesLists.add(tithesListItems!!)
                        //var s= tListItem.TithesAmount.toDouble()

                        //sum=sum?.plus(s)
                        //debugger("D->>>$sum")
                    }

                   //TithesTotal.setText("Total Tithes: GHc "+sum.toString())
                    adapter.submitList(tithesLists)
                }
                //if(sum==null){

                //}
                //else{
                // TitesTotal.setText("Total Tithes: GHc "+sum.toString())
                //}


            }

        })
    }
}

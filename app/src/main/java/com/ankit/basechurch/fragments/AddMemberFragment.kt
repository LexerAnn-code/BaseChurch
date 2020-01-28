package com.ankit.basechurch.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.ankit.basechurch.R
import com.ankit.basechurch.activity.AddMembersData
import com.ankit.basechurch.util.RecyclerMemberAdapter
import com.ankit.basechurch.util.RecyclerTithesAdapter
import com.ankit.basechurch.util.User
import com.ankit.basechurch.util.debugger
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_add_member.*
import kotlinx.android.synthetic.main.fragment_add_member.view.*
import kotlinx.android.synthetic.main.fragment_tithes2.*
import kotlinx.android.synthetic.main.fragment_tithes2.view.*

/**
 * A simple [Fragment] subclass.
 */
class AddMemberFragment : Fragment() {
    private lateinit var adapter: RecyclerMemberAdapter
    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    val ref= FirebaseDatabase.getInstance().reference.child("Tithes")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_add_member, container, false)
        view.MemberLayInput.addTextChangedListener(object : TextWatcher {
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
                if (MemberLayInput.text.toString() == "") {

                } else {
                    searchUser(s.toString())


                }
            }

        })
updatePost()

        return view


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this@AddMemberFragment.requireContext(),AddMembersData::class.java))
        }
    setupList()
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
    private fun setupList() {
        adapter = RecyclerMemberAdapter(this@AddMemberFragment.requireActivity())
        MemberRecycler.apply {
            // Adapter
            this.adapter = this@AddMemberFragment.adapter
            // Layout Manager
            val LinearLayouts= LinearLayoutManager(this@AddMemberFragment.requireContext())
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

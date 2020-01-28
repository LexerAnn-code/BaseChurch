package com.ankit.basechurch.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankit.basechurch.R
import com.ankit.basechurch.util.RecyclerSearchAdapter
import com.ankit.basechurch.util.Tithes
import com.ankit.basechurch.util.debugger
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {
    private lateinit var adapter: RecyclerSearchAdapter
    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    val ref = FirebaseDatabase.getInstance().reference.child("Tithes")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.searchDateInput.addTextChangedListener(object : TextWatcher {
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
                if (searchDateInput.text.toString() == "") {

                } else {
                    searchUser(s.toString())
                    Summation.visibility = View.VISIBLE

                }
            }

        })
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupList()
    }

    private fun searchUser(input: String) {
        val tRef = database.reference.child("Tithes").orderByChild("titheDate").startAt(input)
            .endAt(input + "\uf8ff").addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        debugger("Passed->$input")
                        val searchList = mutableListOf<Tithes>()
                        var sum = 0.00
                        p0.children.forEach {
                            val searchListItem = it.getValue(Tithes::class.java)
                            sum += searchListItem!!.TitheAmount.toDouble()
                            searchList.add(searchListItem)
                        }
                        Summation?.setText("Total Tithes by Date GH¢$sum")
                        adapter.submitList(searchList)
                    }
                }
            })
    }

    private fun setupList() {
        adapter = RecyclerSearchAdapter(this@SearchFragment.requireActivity())
        searchRecycler.apply {
            // Adapter
            this.adapter = this@SearchFragment.adapter
            // Layout Manager
            val LinearLayouts = LinearLayoutManager(this@SearchFragment.requireContext())
            //val GridLayouts= GridLayoutManager(activity,2, GridLayoutManager.VERTICAL,false)

            this.layoutManager = LinearLayouts
        }

    }
}

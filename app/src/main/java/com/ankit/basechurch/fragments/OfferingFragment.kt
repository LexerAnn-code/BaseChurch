package com.ankit.basechurch.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankit.basechurch.util.Offering
import com.ankit.basechurch.R
import com.ankit.basechurch.util.RecyclerOfferingAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_tithes.*

/**
 * A simple [Fragment] subclass.
 */
class OfferingFragment : Fragment() {

    companion object {
        const val EXTRA_POST = "extra_post"
    }

    private val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    private lateinit var adapter: RecyclerOfferingAdapter
    val ref = FirebaseDatabase.getInstance().reference.child("Offerings")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        updatePost()
        return inflater.inflate(R.layout.fragment_tithes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addButtonOffering.setOnClickListener(View.OnClickListener {
            ButtomTithes()

        })


    }

    private fun ButtomTithes() {
        val tinput = OfferingAmountInput.text.toString()
        val dinput = OfferingDateInput.text.toString()
        when {
            TextUtils.isEmpty(tinput) -> Toast.makeText(
                activity,
                "ENTER OFFERING AMOUNT",
                Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(dinput) -> Toast.makeText(
                activity,
                "ENTER DATE",
                Toast.LENGTH_SHORT
            ).show()


            else -> {
                val OfferingID: String = ref.push().key!!
                val Off = Offering(
                    OfferingID,
                    OfferingAmountInput.text.toString(),
                    OfferingDateInput.text.toString()
                )
                ref.child(OfferingID).setValue(Off).addOnCompleteListener {
                    Toast.makeText(activity, "Offering Posted Successfully", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupList()
    }

    private fun setupList() {
        adapter = RecyclerOfferingAdapter(this@OfferingFragment.requireActivity())
        recycler.apply {
            // Adapter
            this.adapter = this@OfferingFragment.adapter
            // Layout Manager
            val LinearLayouts = LinearLayoutManager(this@OfferingFragment.requireContext())
            //val GridLayouts= GridLayoutManager(activity,2, GridLayoutManager.VERTICAL,false)
            this.layoutManager = LinearLayouts
        }
    }

    private fun updatePost() {
        val tithesRef = database.reference.child("Offerings")
        tithesRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
                if (p0.exists()) {
                    val tList = mutableListOf<Offering>()
                    var sum: Double = 0.00
                    p0.children.forEach {

                        val tListItem = it.getValue(Offering::class.java)
                        var s = tListItem!!.OfferingAmount.toDouble()
                        sum += s
                        tList.add(tListItem!!)


                    }
                    OfferingTotal?.text = "Total Offering GH¢$sum"
                    adapter.submitList(tList)
                }
            }
        })
    }

}

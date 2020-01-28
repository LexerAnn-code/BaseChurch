package com.ankit.basechurch.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ankit.basechurch.activity.Login
import com.ankit.basechurch.R
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 */
class SignFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        FirebaseAuth.getInstance().signOut()
        val intent= Intent(this@SignFragment.context, Login::class.java)
        startActivity(intent)
        return inflater.inflate(R.layout.fragment_sign, container, false)
    }

}

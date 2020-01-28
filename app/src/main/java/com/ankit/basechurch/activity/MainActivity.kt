package com.ankit.basechurch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ankit.basechurch.R
import com.ankit.basechurch.util.debugger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var k = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController=findNavController(R.id.nav_host_fragment)
        bottom_nav_view.setupWithNavController(navController);
    }
    override fun onBackPressed() {
        k++;
        if(k == 1) {
            Toast.makeText(this@MainActivity,"Click one more time to exist app", Toast.LENGTH_SHORT).show()
        } else {
            finish()
            super.onBackPressed();
        }
    }

}

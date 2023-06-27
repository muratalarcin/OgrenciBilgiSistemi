package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alarcin.login4.databinding.ActivityOgretmenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OgretmenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOgretmenBinding
    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null       //standart olucak bunlar, realtime database için ve authentication için
    var database : FirebaseDatabase? = null
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOgretmenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ogretmenDerslereGitButton.setOnClickListener {
            intent = Intent(applicationContext, KullanicilarActivity::class.java)
            startActivity(intent)
        }

        //navigation bar olayý
        binding.apply {
            toggle = ActionBarDrawerToggle(this@OgretmenActivity, drawerlayout1, R.string.open, R.string.close)
            drawerlayout1.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            ogretmenNavigationView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.firstItem -> {
                        intent = Intent(applicationContext, OgretmenActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.secondItem -> {
                        intent = Intent(applicationContext, KullanicilarActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.thirdItem -> {
                        intent = Intent(applicationContext, NotlarActivity::class.java)
                        startActivity(intent)
                        finish()
                    }


                }
                true
            }
        }
    }
    //navigation bar ýn sayfada tekrar açýlmasý için
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }
}

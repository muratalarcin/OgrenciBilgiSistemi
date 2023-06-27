package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alarcin.login4.databinding.ActivityAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null       //standart olucak bunlar, realtime database için ve authentication için
    var database : FirebaseDatabase? = null
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("duyurular")

        //çýkýþ yap botunu
        binding.adminCikis.setOnClickListener {
            intent = Intent(applicationContext ,GirisActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.adminKeydetButton.setOnClickListener {
            val girilenDuyuru = binding.adminDuyurugirme.text.toString()
            val currentUser = auth.currentUser
            val  currentUserDb = currentUser?.let { it1 -> databaseReference?.child(it1.uid) }//child kýsýmlarý önemli user id ile kaydediyo böylece
            currentUserDb?.child("duyuru")?.setValue(girilenDuyuru)

            Toast.makeText(this@AdminActivity, "Duyuru kaydý baþarýlý.", Toast.LENGTH_LONG).show()
        }

        binding.adminYemekList.setOnClickListener {
            intent = Intent(applicationContext, YemekListesiActivity::class.java)
            startActivity(intent)
        }

        binding.adminDersListesi.setOnClickListener {
            intent = Intent(applicationContext, AdminDersListesiActivity::class.java)
            startActivity(intent)
        }

        binding.adminOnayDurumu.setOnClickListener {
            intent = Intent(applicationContext, AdminOnayListesiActivity::class.java)
            startActivity(intent)
        }

        //navigation bar olayý
        binding.apply {
            toggle = ActionBarDrawerToggle(this@AdminActivity, adminDrawerlayout, R.string.open, R.string.close)
            adminDrawerlayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            adminNavigationView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.firstItem->{
                        intent = Intent(applicationContext, AdminActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.secondItem->{
                        intent = Intent(applicationContext, YemekListesiActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.thirdItem->{
                        intent = Intent(applicationContext, AdminDersListesiActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.fourthItem->{
                        intent = Intent(applicationContext, DersEkleCikarActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.fivethItem->{
                        intent = Intent(applicationContext, AdminOnayListesiActivity::class.java)
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
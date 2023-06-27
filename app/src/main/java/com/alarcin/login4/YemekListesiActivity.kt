package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alarcin.login4.databinding.ActivityYemekListesiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class YemekListesiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYemekListesiBinding
    lateinit var auth: FirebaseAuth
    lateinit var toggle: ActionBarDrawerToggle
    var databaseReference : DatabaseReference? = null       //standart olucak bunlar, realtime database için ve authentication için
    var database : FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYemekListesiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("aylikyemeklistesi")

        binding.yemekListMenuGuncelle.setOnClickListener {

            val currentUser = auth.currentUser
            val  currentUserDb = currentUser?.let { it1 -> databaseReference?.child(it1.uid) }//child kýsýmlarý önemli user id ile kaydediyo böylece
            currentUserDb?.child("1")?.setValue(binding.yemekListYemek1.text.toString())
            currentUserDb?.child("2")?.setValue(binding.yemekListYemek2.text.toString())
            currentUserDb?.child("3")?.setValue(binding.yemekListYemek3.text.toString())
            currentUserDb?.child("4")?.setValue(binding.yemekListYemek4.text.toString())
            currentUserDb?.child("5")?.setValue(binding.yemekListYemek5.text.toString())
            currentUserDb?.child("6")?.setValue(binding.yemekListYemek6.text.toString())
            currentUserDb?.child("7")?.setValue(binding.yemekListYemek7.text.toString())
            currentUserDb?.child("8")?.setValue(binding.yemekListYemek8.text.toString())
            currentUserDb?.child("9")?.setValue(binding.yemekListYemek9.text.toString())
            currentUserDb?.child("10")?.setValue(binding.yemekListYemek10.text.toString())
            currentUserDb?.child("11")?.setValue(binding.yemekListYemek11.text.toString())
            currentUserDb?.child("12")?.setValue(binding.yemekListYemek12.text.toString())
            currentUserDb?.child("13")?.setValue(binding.yemekListYemek13.text.toString())
            currentUserDb?.child("14")?.setValue(binding.yemekListYemek14.text.toString())
            currentUserDb?.child("15")?.setValue(binding.yemekListYemek15.text.toString())
            currentUserDb?.child("16")?.setValue(binding.yemekListYemek16.text.toString())
            currentUserDb?.child("17")?.setValue(binding.yemekListYemek17.text.toString())
            currentUserDb?.child("18")?.setValue(binding.yemekListYemek18.text.toString())
            currentUserDb?.child("19")?.setValue(binding.yemekListYemek19.text.toString())
            currentUserDb?.child("20")?.setValue(binding.yemekListYemek20.text.toString())
            currentUserDb?.child("21")?.setValue(binding.yemekListYemek21.text.toString())
            currentUserDb?.child("22")?.setValue(binding.yemekListYemek22.text.toString())
            currentUserDb?.child("23")?.setValue(binding.yemekListYemek23.text.toString())
            currentUserDb?.child("24")?.setValue(binding.yemekListYemek24.text.toString())
            currentUserDb?.child("25")?.setValue(binding.yemekListYemek25.text.toString())
            currentUserDb?.child("26")?.setValue(binding.yemekListYemek26.text.toString())
            currentUserDb?.child("27")?.setValue(binding.yemekListYemek27.text.toString())
            currentUserDb?.child("28")?.setValue(binding.yemekListYemek28.text.toString())
            currentUserDb?.child("29")?.setValue(binding.yemekListYemek29.text.toString())
            currentUserDb?.child("30")?.setValue(binding.yemekListYemek30.text.toString())
            currentUserDb?.child("31")?.setValue(binding.yemekListYemek31.text.toString())


            Toast.makeText(this@YemekListesiActivity, "Menü güncellendi.", Toast.LENGTH_LONG).show()
        }
//navigation bar olayý
        binding.apply {
            toggle = ActionBarDrawerToggle(this@YemekListesiActivity, adminDrawerlayout, R.string.open, R.string.close)
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
package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alarcin.login4.databinding.ActivityDersEkleCikarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class DersEkleCikarActivity : AppCompatActivity() {
    lateinit var binding: ActivityDersEkleCikarBinding
    lateinit var auth: FirebaseAuth
    lateinit var toggle: ActionBarDrawerToggle
    var databaseReference: DatabaseReference? =
        null       //standart olucak bunlar, realtime database için ve authentication için
    var databaseReference1: DatabaseReference? =
        null       //standart olucak bunlar, realtime database için ve authentication için
    var database: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDersEkleCikarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        val id = databaseReference!!.push()


        //ders listesine ekle
        binding.derEkleCikartButton.setOnClickListener {
            val birinciDers = binding.ekleBirinciDers.text.toString()
            val ikinciDers = binding.ekleIkinciDers.text.toString()
            val ucuncuDers = binding.ekleUcuncuDers.text.toString()
            val dorduncuDers = binding.ekleDorduncuDers.text.toString()
            val besinciDers = binding.ekleBesinciDers.text.toString()
            val altinciDers = binding.ekleAltinciDers.text.toString()
            val yedinciDers = binding.ekleYedinciDers.text.toString()

            val currentUser = auth.currentUser
            val currentUserDb =
                currentUser?.let { it1 -> databaseReference?.child(it1.uid) }//child kýsýmlarý önemli user id ile kaydediyo böylece
            currentUserDb?.child("zders1")?.setValue(birinciDers)
            currentUserDb?.child("zders2")?.setValue(ikinciDers)
            currentUserDb?.child("zders3")?.setValue(ucuncuDers)
            currentUserDb?.child("zders4")?.setValue(dorduncuDers)
            currentUserDb?.child("zders5")?.setValue(besinciDers)
            currentUserDb?.child("zders6")?.setValue(altinciDers)
            currentUserDb?.child("zders7")?.setValue(yedinciDers)
            currentUserDb?.child("id")?.setValue(id.key.toString())


            intent = Intent(applicationContext, AdminDersListesiActivity::class.java)
            startActivity(intent)
            finish()


            Toast.makeText(this@DersEkleCikarActivity, "Ders kaydý baþarýlý.", Toast.LENGTH_LONG)
                .show()

        }

            //navigation bar olayý
            binding.apply {
                toggle = ActionBarDrawerToggle(this@DersEkleCikarActivity, adminDrawerlayout, R.string.open, R.string.close)
                adminDrawerlayout.addDrawerListener(toggle)
                toggle.syncState()

                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                adminNavigationView.setNavigationItemSelectedListener {
                    when (it.itemId) {
                        R.id.firstItem -> {
                            intent = Intent(applicationContext, AdminActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        R.id.secondItem -> {
                            intent = Intent(applicationContext, YemekListesiActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        R.id.thirdItem -> {
                            intent =
                                Intent(applicationContext, AdminDersListesiActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        R.id.fourthItem -> {
                            intent = Intent(applicationContext, DersEkleCikarActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        R.id.fivethItem -> {
                            intent =
                                Intent(applicationContext, AdminOnayListesiActivity::class.java)
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


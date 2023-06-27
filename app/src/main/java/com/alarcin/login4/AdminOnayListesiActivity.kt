package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alarcin.login4.databinding.ActivityAdminOnayListesiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AdminOnayListesiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminOnayListesiBinding
    lateinit var auth: FirebaseAuth
    lateinit var toggle: ActionBarDrawerToggle
    var databaseReference : DatabaseReference? = null       //standart olucak bunlar, realtime database i�in ve authentication i�in
    var database : FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminOnayListesiBinding.inflate(layoutInflater)
        setContentView(binding.root)




        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")


            //tek tek onaylama
            val currentUser = auth.currentUser
            val userReference =
                databaseReference?.child(currentUser?.uid!!) //�nlem user id nin bo� ge�ilmemesi i�in, id yi ele ald�k burda art�k i�lem yapabiliriz
            userReference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child("onaydurumu").value.toString() == "onaylanmad�") {
                        val currentUser = auth.currentUser
                        val userReference =
                            databaseReference?.child(currentUser?.uid!!) //�nlem user id nin bo� ge�ilmemesi i�in, id yi ele ald�k burda art�k i�lem yapabiliriz
                        userReference?.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                binding.adminOnay1.text =
                                    snapshot.child("adisoyadi").value.toString()
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })



        //tek tek onaylama i�lemi
            binding.adminOnaylaButton.setOnClickListener {
                val currentUser = auth.currentUser
                val currentUserDb =
                    currentUser?.let { it1 -> databaseReference?.child(it1.uid) }//child k�s�mlar� �nemli user id ile kaydediyo b�ylece
                if (binding.checkBox1.isChecked) {
                    currentUserDb?.child("onaydurumu")?.setValue(binding.checkBox1.text.toString())
                    Toast.makeText(this@AdminOnayListesiActivity, "Kay�t Ba�ar�l�", Toast.LENGTH_LONG).show()
                    intent = Intent(applicationContext, AdminActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                if (binding.checkBox2.isChecked) {
                    Toast.makeText(applicationContext, "Hesab�n�z silindi", Toast.LENGTH_LONG).show()
                    auth.signOut()
                    startActivity(
                        Intent(this@AdminOnayListesiActivity, GirisActivity::class.java)
                    )
                    finish()
                    val currentUserDb =
                        currentUser?.let { it1 -> databaseReference?.child(it1.uid) }//child k�s�mlar� �nemli user id ile kaydediyo b�ylece
                    currentUserDb?.removeValue()
                }
            }

            //navigation bar olay�
            binding.apply {
                toggle = ActionBarDrawerToggle(this@AdminOnayListesiActivity, adminDrawerlayout, R.string.open, R.string.close)
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
        //navigation bar �n sayfada tekrar a��lmas� i�in
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if (toggle.onOptionsItemSelected(item)){
                true
            }
            return super.onOptionsItemSelected(item)
        }
    }
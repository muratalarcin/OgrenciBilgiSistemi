package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alarcin.login4.databinding.ActivityAdminDersListesiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AdminDersListesiActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminDersListesiBinding
    lateinit var auth: FirebaseAuth
    lateinit var toggle: ActionBarDrawerToggle
    var databaseReference: DatabaseReference? = null     //standart olucak bunlar, realtime database için ve authentication için
    var database : FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDersListesiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        val id = databaseReference!!.push()


        binding.gosterEkleCikartButton.setOnClickListener {
            intent = Intent(applicationContext, DersEkleCikarActivity::class.java)
            startActivity(intent)
        }
        //ders listesini göster
            val currentUser = auth.currentUser  //kullanýcý giriþ yaptýysa onu ddinledim ve onu currentUser a aktardým
            val userReference = databaseReference?.child(currentUser?.uid!!) //ünlem user id nin boþ geçilmemesi için, id yi ele aldýk burda artýk iþlem yapabiliriz
            userReference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.gosterBirinciDers.text =  snapshot.child("zders1").value.toString()
                    binding.gosterIkinciDers.text = snapshot.child("zders2").value.toString()
                    binding.gosterUcuncuDers.text = snapshot.child("zders3").value.toString()
                    binding.gosterDorduncuDers.text = snapshot.child("zders4").value.toString()
                    binding.gosterBesinciDers.text = snapshot.child("zders5").value.toString()
                    binding.gosterAltinciDers.text = snapshot.child("zders6").value.toString()
                    binding.gosterYedinciDers.text = snapshot.child("zders7").value.toString()



                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        //navigation bar olayý
        binding.apply {
            toggle = ActionBarDrawerToggle(this@AdminDersListesiActivity, adminDrawerlayout, R.string.open, R.string.close)
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

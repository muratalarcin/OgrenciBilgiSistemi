package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alarcin.login4.databinding.ActivityAnasayfaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class AnasayfaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnasayfaBinding
    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null       //standart olucak bunlar, realtime database için ve authentication için
    var databaseReference1 : DatabaseReference? = null       //standart olucak bunlar, realtime database için ve authentication için
    var database : FirebaseDatabase? = null
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnasayfaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("duyurular")
        databaseReference1 = database?.reference!!.child("aylikyemeklistesi")

        val currentUser = auth.currentUser  //kullanýcý giriþ yaptýysa onu ddinledim ve onu currentUser a aktardým
        //realtime database deki id ye ulaþýp altýndaki childlerin içindeki veriyi sayfaya aktarýcaz
        val userReference = databaseReference?.child(currentUser?.uid!!) //ünlem user id nin boþ geçilmemesi için, id yi ele aldýk burda artýk iþlem yapabiliriz
        userReference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                binding.anasayfaDuyurular.text = snapshot.child("duyuru").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //hangi günün yemeði
        val userReference1 = databaseReference1?.child(currentUser?.uid!!)
        userReference1?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val simdikiZaman = LocalDateTime.now().dayOfMonth
                if (simdikiZaman == 1){
                    binding.anasayfaYemek.text = snapshot.child("1").value.toString()
                }
                if (simdikiZaman == 2){
                    binding.anasayfaYemek.text = snapshot.child("2").value.toString()
                }
                if (simdikiZaman == 3){
                    binding.anasayfaYemek.text = snapshot.child("3").value.toString()
                }
                if (simdikiZaman == 4){
                    binding.anasayfaYemek.text = snapshot.child("4").value.toString()
                }
                if (simdikiZaman == 5){
                    binding.anasayfaYemek.text = snapshot.child("5").value.toString()
                }
                if (simdikiZaman == 6){
                    binding.anasayfaYemek.text = snapshot.child("6").value.toString()
                }
                if (simdikiZaman == 7){
                    binding.anasayfaYemek.text = snapshot.child("7").value.toString()
                }
                if (simdikiZaman == 8){
                    binding.anasayfaYemek.text = snapshot.child("8").value.toString()
                }
                if (simdikiZaman == 9){
                    binding.anasayfaYemek.text = snapshot.child("9").value.toString()
                }
                if (simdikiZaman == 10){
                    binding.anasayfaYemek.text = snapshot.child("10").value.toString()
                }
                if (simdikiZaman == 11){
                    binding.anasayfaYemek.text = snapshot.child("11").value.toString()
                }
                if (simdikiZaman == 12){
                    binding.anasayfaYemek.text = snapshot.child("12").value.toString()
                }
                if (simdikiZaman == 13){
                    binding.anasayfaYemek.text = snapshot.child("13").value.toString()
                }
                if (simdikiZaman == 14){
                    binding.anasayfaYemek.text = snapshot.child("14").value.toString()
                }
                if (simdikiZaman == 15){
                    binding.anasayfaYemek.text = snapshot.child("15").value.toString()
                }
                if (simdikiZaman == 16){
                    binding.anasayfaYemek.text = snapshot.child("16").value.toString()
                }
                if (simdikiZaman == 17){
                    binding.anasayfaYemek.text = snapshot.child("17").value.toString()
                }
                if (simdikiZaman == 18){
                    binding.anasayfaYemek.text = snapshot.child("18").value.toString()
                }
                if (simdikiZaman == 19){
                    binding.anasayfaYemek.text = snapshot.child("19").value.toString()
                }
                if (simdikiZaman == 20){
                    binding.anasayfaYemek.text = snapshot.child("20").value.toString()
                }
                if (simdikiZaman == 21){
                    binding.anasayfaYemek.text = snapshot.child("21").value.toString()
                }
                if (simdikiZaman == 22){
                    binding.anasayfaYemek.text = snapshot.child("22").value.toString()
                }
                if (simdikiZaman == 23){
                    binding.anasayfaYemek.text = snapshot.child("23").value.toString()
                }
                if (simdikiZaman == 24){
                    binding.anasayfaYemek.text = snapshot.child("24").value.toString()
                }
                if (simdikiZaman == 25){
                    binding.anasayfaYemek.text = snapshot.child("25").value.toString()
                }
                if (simdikiZaman == 26){
                    binding.anasayfaYemek.text = snapshot.child("26").value.toString()
                }
                if (simdikiZaman == 27){
                    binding.anasayfaYemek.text = snapshot.child("27").value.toString()
                }
                if (simdikiZaman == 28){
                    binding.anasayfaYemek.text = snapshot.child("28").value.toString()
                }
                if (simdikiZaman == 29){
                    binding.anasayfaYemek.text = snapshot.child("29").value.toString()
                }
                if (simdikiZaman == 30){
                    binding.anasayfaYemek.text = snapshot.child("30").value.toString()
                }
                if (simdikiZaman == 31){
                    binding.anasayfaYemek.text = snapshot.child("31").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        //navigation bar olayý
        binding.apply {
            toggle = ActionBarDrawerToggle(this@AnasayfaActivity, drawerlayout, R.string.open, R.string.close)
            drawerlayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navigationView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.firstItem->{
                        intent = Intent(applicationContext, AnasayfaActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.secondItem->{
                        intent = Intent(applicationContext, ProfilActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.thirdItem->{
                        intent = Intent(applicationContext, GuncelleActivity::class.java)
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
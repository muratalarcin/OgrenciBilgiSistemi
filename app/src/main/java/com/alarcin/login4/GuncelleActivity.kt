package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alarcin.login4.databinding.ActivityGuncelleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class GuncelleActivity : AppCompatActivity() {
    lateinit var binding : ActivityGuncelleBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGuncelleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        val currentUser = auth.currentUser
        binding.guncelleemail.setText(currentUser?.email)
        //real time databasede bulunan kullanıcı id sine erişip ad ve soyad alma
        val userReference = databaseReference?.child(currentUser?.uid!!)
        userReference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.guncelleadsoyad.setText(snapshot.child("adisoyadi").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //bilgilerimi güncelle butonu
        binding.guncellebilgilerimiguncelle.setOnClickListener {
            val guncellemail = binding.guncelleemail.text.toString().trim()
            val guncelmail = guncellemail + "@gmail.com"
            currentUser!!.updateEmail(guncelmail)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext, "E-mail güncellendi", Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(applicationContext, "E-mail Güncellenemedi", Toast.LENGTH_LONG).show()
                    }

                }
            //parola güncelleme
            val guncelleparola = binding.guncelleparola.text.toString().trim()
            currentUser!!.updatePassword(guncelleparola)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext, "Parola Güncellendi", Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(applicationContext, "Parola Güncellenemedi", Toast.LENGTH_LONG).show()
                    }

                }
            //ad soyadguncelleme
            val  currentUserDb = currentUser?.let { it1 -> databaseReference?.child(it1.uid) }//child kısımları önemli user id ile kaydediyo böylece
            currentUserDb?.removeValue()
            currentUserDb?.child("tcno")?.setValue(binding.guncelleemail.text.subSequence(0,11).toString())
            Toast.makeText(applicationContext, "TC kimlik no güncellendi", Toast.LENGTH_LONG).show()

            currentUserDb?.child("mail")?.setValue(binding.guncelleTC.text.toString())
            Toast.makeText(applicationContext, "TC kimlik no güncellendi", Toast.LENGTH_LONG).show()

            currentUserDb?.child("adisoyadi")?.setValue(binding.guncelleadsoyad.text.toString())
            Toast.makeText(applicationContext, "Ad ve soyad güncellendi", Toast.LENGTH_LONG).show()

            currentUserDb?.child("telefonnumarasi")?.setValue(binding.guncelleTelNo.text.toString())
            Toast.makeText(applicationContext, "Telefon numarası güncellendi", Toast.LENGTH_LONG).show()

            if (binding.guncelleOgrenciRadio.isChecked){
                currentUserDb?.child("unvan")?.setValue(binding.guncelleOgrenciRadio.text.toString())
            }else{
                currentUserDb?.child("unvan")?.setValue(binding.guncellerOgretmenRadio.text.toString())
            }
            Toast.makeText(this@GuncelleActivity, "Guncelleme Başarılı",Toast.LENGTH_LONG).show()

        }
        //Giriş yap butonu ile giriş sayfasına dönme
        binding.guncellegirisyap.setOnClickListener {
            intent = Intent(applicationContext, GirisActivity::class.java)
            startActivity(intent)
            finish()
        }
        //navigationView
        binding.apply {
            toggle = ActionBarDrawerToggle(this@GuncelleActivity, drawerlayout, R.string.open, R.string.close)
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
    //navigation bar ın sayfada tekrar açılması için
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }
}
package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alarcin.login4.databinding.ActivityProfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilBinding
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference ?= null
    var database : FirebaseDatabase ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)  //activity xml dosyasını burdaki sınıfın içinde çalıştırmış olduk

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")


        //giriş yapan kullanıcı currentUser oldu
        val currentUser = auth.currentUser  //kullanıcı giriş yaptıysa onu ddinledim ve onu currentUser a aktardım
        binding.profilemail.text = "TC kimlik numaranız: " + currentUser?.email.toString().subSequence(0,11)

        //realtime database deki id ye ulaşıp altındaki childlerin içindeki veriyi sayfaya aktarıcaz
        val userReference = databaseReference?.child(currentUser?.uid!!) //ünlem user id nin boş geçilmemesi için, id yi ele aldık burda artık işlem yapabiliriz
        userReference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.profilTc.text = "Mail adresiniz: " + snapshot.child("mail").value.toString()
                binding.profiladsoyad.text = "Tam adınız: " + snapshot.child("adisoyadi").value.toString()
                binding.profilTelefonNo.text = "Telefon numaranız: " + snapshot.child("telefonnumarasi").value.toString()
                binding.profilUnvan.text = "Ünvanınız: " + snapshot.child("unvan").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        //çıkış yap botunu
        binding.profilcikisyapbutonu.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@ProfilActivity,GirisActivity::class.java))
            finish()
        }
        //hesabımı sil butonu
        binding.profilhesabimisil.setOnClickListener {
            currentUser?.delete()?.addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(applicationContext,"Hesabınız silindi",Toast.LENGTH_LONG).show()
                    auth.signOut()
                    startActivity(Intent(this@ProfilActivity, GirisActivity::class.java))
                    finish()
                    val  currentUserDb = currentUser?.let { it1 -> databaseReference?.child(it1.uid) }//child kısımları önemli user id ile kaydediyo böylece
                    currentUserDb?.removeValue()
                    Toast.makeText(applicationContext,"adı ve soyadı silindi",Toast.LENGTH_LONG).show()
                }
            }

        }
        //profil bilgilerimi güncelle butonu ile sayfa değiştirme
        binding.profilbilgilerimiguncellebutonu.setOnClickListener {
            intent = Intent(applicationContext, GuncelleActivity::class.java)
            startActivity(intent)
            finish()
        }
        //navigation bar olayı
        binding.apply {
            toggle = ActionBarDrawerToggle(this@ProfilActivity, drawerlayout, R.string.open, R.string.close)
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
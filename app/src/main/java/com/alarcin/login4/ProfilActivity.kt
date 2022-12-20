package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alarcin.login4.databinding.ActivityProfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilBinding
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
        binding.profilemail.text = "E-mail : " + currentUser?.email

        //realtime database deki id ye ulaşıp altındaki childlerin içindeki veriyi sayfaya aktarıcaz
        val userReference = databaseReference?.child(currentUser?.uid!!) //ünlem user id nin boş geçilmemesi için, id yi ele aldık burda artık işlem yapabiliriz
        userReference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.profiladsoyad.text = "Tam adınız :" + snapshot.child("adisoyadi").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //çıkış yap botunu
        binding.profilcikisyapbutonu.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@ProfilActivity,giris::class.java))
            finish()
        }
        //hesabımı sil butonu
        binding.profilhesabimisil.setOnClickListener {
            currentUser?.delete()?.addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(applicationContext,"Hesabınız silindi",Toast.LENGTH_LONG).show()
                    auth.signOut()
                    startActivity(Intent(this@ProfilActivity, giris::class.java))
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
    }
}
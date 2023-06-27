package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.alarcin.login4.databinding.ActivityGirisBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class GirisActivity : AppCompatActivity() {
    lateinit var binding: ActivityGirisBinding
    private lateinit var auth: FirebaseAuth   //auth değişkenini kullanarak FirebaseAuth u referans olarak kullandık
    var databaseReference : DatabaseReference?= null
    var database : FirebaseDatabase ?= null
    override fun onCreate(savedInstanceState: Bundle?) {                  //binding olayı activity den veri alma işleri için
        super.onCreate(savedInstanceState)
        val binding = ActivityGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()  // FirebaseAuth için konum izni almış oluyoruz getInstance ile
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        /* //kullanıcının oturum açıp açmadığını kontrol etmece, açtıysa daha önce profil activitye gitcek
          val currentUser =auth.currentUser
          if (currentUser != null){
              startActivity(Intent(this@giris, AnasayfaActivity::class.java))
              finish()
          } */
        //realtime database deki id ye ulaşıp altındaki childlerin içindeki veriyi sayfaya aktarıcaz


        //giriş yap butonuna tıklandığında
        binding.giriskaydetbutonu.setOnClickListener{
            val girismail = binding.girisemail.text.toString() + "@gmail.com"
            val girisparola = binding.girisparola.text.toString()

            if (binding.girisemail.text.toString() == "admin" && girisparola== "1957"){
                intent = Intent(applicationContext, AdminActivity::class.java)
                startActivity(intent)
                finish()
            }
            if (TextUtils.isEmpty(girismail)){
                binding.girisemail.error = "Lütfen TC kimlik numaranızı giriniz!!"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(girisparola)){
                binding.girisparola.error = "Lütfen parolanızı giriniz!!"
                return@setOnClickListener
            }


               if(binding.girisemail.text.toString() == girisparola) {
                   intent = Intent(applicationContext, PsifirlaActivity::class.java)
                   startActivity(intent)
               }else{

                   auth.signInWithEmailAndPassword(girismail, girisparola)
                       .addOnCompleteListener(this){
                           if (it.isSuccessful){
                               val currentUser = auth.currentUser  //kullanıcı giriş yaptıysa onu ddinledim ve onu currentUser a aktardım
                               val userReference = databaseReference?.child(currentUser?.uid!!) //ünlem user id nin boş geçilmemesi için, id yi ele aldık burda artık işlem yapabiliriz
                               userReference?.addValueEventListener(object : ValueEventListener {
                                   override fun onDataChange(snapshot: DataSnapshot) {
                                       val girisUnvan = snapshot.child("unvan").value.toString()
                                       val onaydurumu = snapshot.child("onaydurumu").value.toString()
                                       if (onaydurumu == "onaylanmadı") {
                                           Toast.makeText(this@GirisActivity, "Admin onayı bekleniyor", Toast.LENGTH_SHORT).show()
                                       }else {
                                           if (girisUnvan == ("Öğrenci")) {
                                               intent = Intent(
                                                   applicationContext,
                                                   AnasayfaActivity::class.java
                                               )
                                               startActivity(intent)
                                               finish()
                                           } else if (girisUnvan == ("Öğretim Üyesi")) {
                                               intent = Intent(
                                                   applicationContext,
                                                   OgretmenActivity::class.java
                                               )
                                               startActivity(intent)
                                               finish()
                                           }

                                       }
                                   }

                                   override fun onCancelled(error: DatabaseError) {
                                       TODO("Not yet implemented")
                                   }

                               })



                           } else {
                               if (girismail== "admin" && girisparola== "1957"){
                                   Toast.makeText(applicationContext, "Admin Girişi Başarılı.", Toast.LENGTH_LONG).show()
                               }else {
                                   Toast.makeText(
                                       applicationContext,
                                       "Giriş Hatalı Lütfen Tekrar Deneyiniz.",
                                       Toast.LENGTH_LONG
                                   ).show()
                               }
                           }
                       }


               }
        }
        //yeni üyelik sayfasına gitmek için
        binding.girisyeniuyelik.setOnClickListener{
            intent = Intent(applicationContext, ActivityUye::class.java)
            startActivity(intent)
            finish()
        }
        /*//parolamı unuttum sayfasına gitmek için
        binding.girisparolaunuttum.setOnClickListener{
            intent = Intent(applicationContext, PsifirlaActivity::class.java)
            startActivity(intent)
            finish()
        }*/
    }
}
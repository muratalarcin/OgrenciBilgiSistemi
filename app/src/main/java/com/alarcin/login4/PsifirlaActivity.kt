package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alarcin.login4.databinding.ActivityPsifirlaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PsifirlaActivity : AppCompatActivity() {
    lateinit var binding: ActivityPsifirlaBinding
    private lateinit var auth: FirebaseAuth //firebase e erişim izni
    var databaseReference : DatabaseReference? = null       //standart olucak bunlar, realtime database için ve authentication için
    var database : FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPsifirlaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

       // auth = FirebaseAuth.getInstance() //firebaseauthla aldığım erişim ismini auth değişkenine atadım

        /*binding.psifirlaparolasifirlabutonu.setOnClickListener {
            val psifirlaemail = binding.psifirlaemail.text.toString()
            if (TextUtils.isEmpty(psifirlaemail)){
                binding.psifirlaemail.error = "Lütfen TC kimlik numaranızı giriniz"
            }else {
                auth.sendPasswordResetEmail(psifirlaemail)
                    .addOnCompleteListener(this){ sifirlama ->
                        if (sifirlama.isSuccessful){
                            binding.psifirlamesaj.text = "E-mail adresinize sıfırlama bağlantısı gönderildi, lütfen kontrol ediniz"
                        }else {
                            Toast.makeText(applicationContext,"Sıfırlama işlemi başarısız", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }*/

        binding.psifirlaparolasifirlabutonu.setOnClickListener {
            val currentUser = auth.currentUser
        val guncelleparola = binding.psifirlaemail.text.toString().trim()
        currentUser!!.updatePassword(guncelleparola)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    val currentUser = auth.currentUser
                    val  currentUserDb = currentUser?.let { it1 -> databaseReference?.child(it1.uid) }//child kısımları önemli user id ile kaydediyo böylece

                    Toast.makeText(applicationContext, "Parola Güncellendi", Toast.LENGTH_LONG).show()
                    currentUserDb?.child("parola")?.setValue(guncelleparola)

                }else {
                    Toast.makeText(applicationContext, "Parola Güncellenemedi, Admin Üye Onayı Bekleniyor", Toast.LENGTH_LONG).show()
                }
            }
        }
        //giriş sayfasına dönme
        binding.psifirlagirisyapbutonu.setOnClickListener {
            intent = Intent(applicationContext,GirisActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
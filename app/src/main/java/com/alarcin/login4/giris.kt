package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.alarcin.login4.databinding.ActivityGirisBinding
import com.google.firebase.auth.FirebaseAuth

class giris : AppCompatActivity() {
    lateinit var auth: FirebaseAuth         //auth değişkenini kullanarak FirebaseAuth u referans olarak kullandık
    lateinit var binding: ActivityGirisBinding
    override fun onCreate(savedInstanceState: Bundle?) {                  //binding olayı activity den veri alma işleri için
        super.onCreate(savedInstanceState)
        val binding = ActivityGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()  // FirebaseAuth için konum izni almış oluyoruz getInstance ile

        //kullanıcının oturum açıp açmadığını kontrol etmece, açtıysa daha önce profil activitye gitcek
        val currentUser =auth.currentUser
        if (currentUser != null){
            startActivity(Intent(this@giris, ProfilActivity::class.java))
            finish()
        }
        //giriş yap butonuna tıklandığında
        binding.giriskaydetbutonu.setOnClickListener{
            val girisemail = binding.girisemail.text.toString()
            val girisparola = binding.girisparola.text.toString()
            if (TextUtils.isEmpty(girisemail)){
                binding.girisemail.error = "Lütfen e-mail adresinizi giriniz!!"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(girisparola)){
                binding.girisparola.error = "Lütfen parolanızı giriniz!!"
                return@setOnClickListener
            }
            //Giriş bilgilerini doğrulayıp giriş yapma, kayıtlarla karşılaştırma
            auth.signInWithEmailAndPassword(girisemail, girisparola)
                .addOnCompleteListener(this){
                    if (it.isSuccessful){
                        intent = Intent(applicationContext,ProfilActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext,"Giriş Hatalı Lütfen Tekrar Deneyiniz."
                        , Toast.LENGTH_LONG).show()
                    }
                }
        }
        //yeni üyelik sayfasına gitmek için
        binding.girisyeniuyelik.setOnClickListener{
            intent = Intent(applicationContext, uye::class.java)
            startActivity(intent)
            finish()
        }
        //parolamı unuttum sayfasına gitmek için
        binding.girisparolaunuttum.setOnClickListener{
            intent = Intent(applicationContext, PsifirlaActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
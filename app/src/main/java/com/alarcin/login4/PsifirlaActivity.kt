package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import com.alarcin.login4.databinding.ActivityProfilBinding
import com.alarcin.login4.databinding.ActivityPsifirlaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PsifirlaActivity : AppCompatActivity() {
    lateinit var binding: ActivityPsifirlaBinding
    private lateinit var auth: FirebaseAuth //firebase e erişim izni
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPsifirlaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() //firebaseauthla aldığım erişim ismini auth değişkenine atadım

        binding.psifirlaparolasifirlabutonu.setOnClickListener {
            var psifirlaemail = binding.psifirlaemail.text.toString().trim()//trim baştaki ve sondaki boşlukları siler hatayı önler
            if (TextUtils.isEmpty(psifirlaemail)){
                binding.psifirlaemail.error = "Lütfen e-mail adresinizi yazınız"
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
        }
        //giriş sayfasına dönme
        binding.psifirlagirisyapbutonu.setOnClickListener {
            intent = Intent(applicationContext,giris::class.java)
            startActivity(intent)
            finish()
        }
    }
}
package com.alarcin.login4
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.alarcin.login4.databinding.ActivityUyeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class uye : AppCompatActivity() {
    lateinit var binding : ActivityUyeBinding     //aktiviteden bir şeyler alabilmek için
    private lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? = null       //standart olucak bunlar, realtime database için ve authentication için
    var database : FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUyeBinding.inflate(layoutInflater)    //binding olayları
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        //kaydet butonu ile kaydetme
        binding.uyekaydetbutonu.setOnClickListener{
            val uyeadsoydad = binding.uyeadsoyad.text.toString()
            val uyeemail = binding.uyeemail.text.toString()
            val uyeparola = binding.uyeparola.text.toString()
            if (TextUtils.isEmpty(uyeadsoydad)){
                binding.uyeadsoyad.error = "Lütfen adınızı ve soy  adınızı giriniz."
                return@setOnClickListener
            }else if (TextUtils.isEmpty(uyeemail)){
                binding.uyeemail.error = "Lütfen E-mail adresibizi giriniz."
                return@setOnClickListener
            }else if (TextUtils.isEmpty(uyeparola)){
                binding.uyeparola.error = "Lütfen parolanızı giriniz."
                return@setOnClickListener
            }

            //email, parola, kullanıcı bilgilerini veritabanına ekleme
            auth.createUserWithEmailAndPassword(binding.uyeemail.text.toString(), binding.uyeparola.text.toString())
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        //şu an ki kullanıcı bilgilerini alma
                        val currentUser = auth.currentUser
                        //kullanıcı id sini alıp o id altında ad ve soy ad kaydedicez
                        val  currentUserDb = currentUser?.let { it1 -> databaseReference?.child(it1.uid) }//child kısımları önemli user id ile kaydediyo böylece
                        currentUserDb?.child("adisoyadi")?.setValue(binding.uyeadsoyad.text.toString())
                        Toast.makeText(this@uye, "Kayıt Başarılı",Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(this@uye, "Kayıt Başarısız",Toast.LENGTH_LONG).show()
                    }
                }

        }
        //Giriş sayfasına gitmek için
        binding.uyegirisyapbutonu.setOnClickListener {
            intent = Intent(applicationContext,giris::class.java)
            startActivity(intent)
            finish()
        }

    }
}
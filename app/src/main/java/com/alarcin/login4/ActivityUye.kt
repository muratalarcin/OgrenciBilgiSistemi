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

class ActivityUye : AppCompatActivity() {
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
        val id = databaseReference!!.push()

        //kaydet butonu ile kaydetme
        binding.uyekaydetbutonu.setOnClickListener{
            val uyeTC = binding.uyeTc.text.toString()
            val uyeadsoydad = binding.uyeadsoyad.text.toString()
            val uyeTelNo = binding.uyeTelNo.text.toString()
            val uyemail = binding.uyeemail.text.toString() + "@gmail.com"

            /*val uyeparola = binding.uyeparola.text.toString()*/

            if (TextUtils.isEmpty(uyeTC)){
                binding.uyeemail.error = "TC kimlik numaranızı giriniz!"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(uyeadsoydad)){
                binding.uyeadsoyad.error = "Adınızı ve soy  adınızı giriniz!"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(uyeTelNo)){
                binding.uyeTelNo.error = "Telefon numaranızı giriniz!"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(uyemail)){
                binding.uyeTc.error = "E-mail adresinizi giriniz!"
                return@setOnClickListener
            }/*else if (TextUtils.isEmpty(uyeparola)){
                binding.uyeparola.error = "Parolanızı giriniz!"
                return@setOnClickListener
            }*/else  if (binding.uyeOgrenciRadio.isChecked.not() &&  binding.uyeOgretmenRadio.isChecked.not()){
                Toast.makeText(this@ActivityUye, "Unvan bilgisi giriniz!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val onaydurumu = "onaylanmadı"
            //email, parola, kullanıcı bilgilerini veritabanına ekleme
            auth.createUserWithEmailAndPassword(uyemail, binding.uyeemail.text.toString())
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        val onaydurumu = "onaylanmadı"
                        //şu an ki kullanıcı bilgilerini alma
                        val currentUser = auth.currentUser
                        //kullanıcı id sini alıp o id altında ad ve soy ad kaydedicez
                        val  currentUserDb = currentUser?.let { it1 -> databaseReference?.child(it1.uid) }//child kısımları önemli user id ile kaydediyo böylece
                        currentUserDb?.child("mail")?.setValue(binding.uyeTc.text.toString())
                        currentUserDb?.child("adisoyadi")?.setValue(binding.uyeadsoyad.text.toString())
                        currentUserDb?.child("telefonnumarasi")?.setValue(binding.uyeTelNo.text.toString())
                        currentUserDb?.child("tcno")?.setValue(binding.uyeemail.text.subSequence(0,11).toString())
                        currentUserDb?.child("onaydurumu")?.setValue(onaydurumu)
                        currentUserDb?.child("id")?.setValue(id.key.toString())
                        if (binding.uyeOgrenciRadio.isChecked){
                            currentUserDb?.child("unvan")?.setValue(binding.uyeOgrenciRadio.text.toString())
                            Toast.makeText(this@ActivityUye, "Kayıt Başarılı",Toast.LENGTH_LONG).show()
                        }else if(binding.uyeOgretmenRadio.isChecked){
                            currentUserDb?.child("unvan")?.setValue(binding.uyeOgretmenRadio.text.toString())
                            Toast.makeText(this@ActivityUye, "Kayıt Başarılı",Toast.LENGTH_LONG).show()
                        }
                    }
                }

        }
        binding.uyegirisyapbutonu.setOnClickListener {
            intent = Intent(applicationContext, GirisActivity::class.java)
            startActivity(intent)
            finish()
        }
        //Giriş sayfasına gitmek için
       /*  binding.uyegirisyapbutonu.setOnClickListener {
            val uyeOgrenciRadio = binding.uyeOgrenciRadio.text.toString()
            val uyeOgretmenRadio = binding.uyeOgretmenRadio.text.toString()
            val currentUser =auth.currentUser
            if (uyeOgrenciRadio.isEmpty() &&  currentUser != null){
                intent = Intent(applicationContext, OgretmenActivity::class.java)
                startActivity(intent)
                finish()
            }else if(uyeOgretmenRadio.isEmpty() &&  currentUser != null){
                intent = Intent(applicationContext, AnasayfaActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                intent = Intent(applicationContext, ActivityGirisBinding::class.java)
                startActivity(intent)
                finish()
            }
        }
        val uyeOgrenciRadio = binding.uyeOgrenciRadio.text.toString()
        val uyeOgretmenRadio = binding.uyeOgretmenRadio.text.toString()
        binding.uyegirisyapbutonu.setOnClickListener {
            intent.putExtra("Key", uyeOgretmenRadio) //veri gönderiliyor
            intent.putExtra("Pas", uyeOgrenciRadio) //veri gönderiliyor
            intent = Intent(applicationContext, giris::class.java)
            startActivity(intent)
            finish()
        }
        */

    }
}
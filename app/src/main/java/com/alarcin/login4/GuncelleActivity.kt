package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alarcin.login4.databinding.ActivityGuncelleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class GuncelleActivity : AppCompatActivity() {
    lateinit var binding : ActivityGuncelleBinding
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
            val guncelleemail = binding.guncelleemail.text.toString().trim()
            currentUser!!.updateEmail(guncelleemail)
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
            currentUserDb?.child("adisoyadi")?.setValue(binding.guncelleadsoyad.text.toString())
            Toast.makeText(applicationContext, "Ad ve Soyad Güncellendi", Toast.LENGTH_LONG).show()
        }
        //Giriş yap butonu ile giriş sayfasına dönme
        binding.guncellegirisyap.setOnClickListener {
            intent = Intent(applicationContext, giris::class.java)
            startActivity(intent)
            finish()
        }
    }
}
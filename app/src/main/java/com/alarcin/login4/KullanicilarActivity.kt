package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alarcin.login4.databinding.ActivityKullanicilarBinding
import com.google.firebase.database.*

class KullanicilarActivity : AppCompatActivity() {
    lateinit var binding: ActivityKullanicilarBinding
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    lateinit var userArrayList: ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityKullanicilarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userRecyclerView = binding.kullaniciist

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<User>()
        getUserData()

        //navigation bar olayý
        binding.apply {
            toggle = ActionBarDrawerToggle(this@KullanicilarActivity, drawerlayout1, R.string.open, R.string.close)
            drawerlayout1.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            ogretmenNavigationView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.firstItem -> {
                        intent = Intent(applicationContext, OgretmenActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.secondItem -> {
                        intent = Intent(applicationContext, KullanicilarActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.thirdItem -> {
                        intent = Intent(applicationContext, NotlarActivity::class.java)
                        startActivity(intent)
                        finish()
                    }


                }
                true
            }
        }
    }
    //navigation bar ýn sayfada tekrar açýlmasý için
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("profile")
        dbref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
              //  if(snapshot.child("unvan").value.toString()=="Öðrenci") {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {

                            val user = userSnapshot.getValue(User::class.java)
                            userArrayList.add(user!!)
                        }
                        userRecyclerView.adapter = CustomAdapter(userArrayList)
                    }
                }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
package com.alarcin.login4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.alarcin.login4.databinding.ActivityNotlarBinding

class NotlarActivity : AppCompatActivity() {
    lateinit var binding: ActivityNotlarBinding
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotlarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //navigation bar olayý
        binding.apply {
            toggle = ActionBarDrawerToggle(this@NotlarActivity, drawerlayout1, R.string.open, R.string.close)
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
}
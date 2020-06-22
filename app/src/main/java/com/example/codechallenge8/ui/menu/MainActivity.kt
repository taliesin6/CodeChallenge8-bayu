package com.example.codechallenge8.ui.menu

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.codechallenge8.R
import com.example.codechallenge8.ui.history.HistoryFragment
import com.example.codechallenge8.ui.login.LoginActivity
import com.example.codechallenge8.ui.profile.ProfileFragment
import com.example.codechallenge8.ui.battle.SaveBattleActivity
import com.example.codechallenge8.util.SharedPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.itemIconTintList = null
        nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = MenuFragment.newInstance()
        addFragment(fragment)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.nav_menu -> {
                val menuFragment = MenuFragment.newInstance()
                addFragment(menuFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_history -> {
                val historyFragment =
                    HistoryFragment()
                addFragment(historyFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile -> {
                val profileFragment =
                    ProfileFragment()
                addFragment(profileFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }

    private fun addFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.save_battle ->{
                Toast.makeText(this, "Save Battle", Toast.LENGTH_SHORT).show()
                Intent(this, SaveBattleActivity::class.java).apply {
                    startActivity(this)
                }
                return true
            }
            R.id.close_app -> {
                Toast.makeText(this, "Keluar Aplikasi", Toast.LENGTH_SHORT).show()
                val alertDialog = this.let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->
                            exitProcess(0)
                        }
                        setNegativeButton("Cancel"){ dialogInterface: DialogInterface, i: Int ->
                            dialogInterface.cancel()
                        }
                    }
                    builder.create()
                }
                alertDialog.setTitle("Exit")
                alertDialog.setMessage("Are you sure want to exit?")
                alertDialog.show()
                return true
            }
            R.id.logout -> {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                SharedPrefManager.getInstance(this).clear()
                Intent(this, LoginActivity::class.java).apply {
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(this)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
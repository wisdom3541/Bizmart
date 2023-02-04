package com.example.bizmart


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView


class FragmentHolder : AppCompatActivity(R.layout.fragment_holder) {

    private lateinit var bottomNav: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNav = findViewById(R.id.bottomNavigation)

        if (savedInstanceState == null) {
            replaceFragment(Home())
            bottomNav.menu.getItem(1).isChecked = true
        }



        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.home -> {
                    replaceFragment(Home())
                    it.isChecked = true
                    return@setOnItemSelectedListener true
                }
                R.id.category -> {
                    replaceFragment(Category())
                    it.isChecked = true
                    return@setOnItemSelectedListener true
                }

                R.id.profile -> {
                    replaceFragment(Profile())
                    it.isChecked = true
                    return@setOnItemSelectedListener true
                }

                else -> {
                    replaceFragment(Home())
                    it.isChecked = true
                    return@setOnItemSelectedListener true
                }
            }

//                R.id.profile -> {
//                    supportFragmentManager.commit {
//                        setReorderingAllowed(true)
//                        add<Home>(R.id.fragment_container)
//                    }
//            }

        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
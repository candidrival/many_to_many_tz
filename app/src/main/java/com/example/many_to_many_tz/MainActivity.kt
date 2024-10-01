package com.example.many_to_many_tz

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.many_to_many_tz.presentation.all_items.AllItemsFragment
import com.example.many_to_many_tz.utils.doubleClickExit
import com.example.many_to_many_tz.R

class MainActivity : AppCompatActivity() {

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentContainerViewMain)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            when (getCurrentFragment()) {
                is AllItemsFragment -> {
                    doubleClickExit(this@MainActivity)
                }

                else -> navHostFragment?.findNavController()?.popBackStack()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    fun getCurrentFragment(): Fragment? {
        navHostFragment?.childFragmentManager?.backStackEntryCount
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }
}
package com.world.horizon.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.world.horizon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        binding.ModifyToolbar.setTitle("Horizon")
    }
}
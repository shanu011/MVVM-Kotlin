package com.rajat.mvvmkotlin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajat.mvvmkotlin.databinding.ActivityMainBinding
import com.rajat.mvvmkotlin.viewmodel.ApiStatus
import com.rajat.mvvmkotlin.viewmodel.EmployeeViewModel
import com.rajat.mvvmkotlin.viewmodel.SealedClass
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
     val viewModel: EmployeeViewModel by viewModels()
    lateinit var employeeAdapter: EmployeeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.observeConnectivity()
        viewModel.employeeAs.observe(this) { state ->
            when (state) {
                is SealedClass.Loading -> {
                    Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                }
                is SealedClass.Success -> {
                    var response = state.data
                    employeeAdapter = EmployeeAdapter(this, response?.data as ArrayList)
                    binding.rvList.layoutManager = LinearLayoutManager(this)
                    binding.rvList.adapter = employeeAdapter
                }
                is SealedClass.Error -> {
                    var error = state.toString()
                    println("SHow Error: ${error}")
                }
                else -> {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopObserving()
    }
}
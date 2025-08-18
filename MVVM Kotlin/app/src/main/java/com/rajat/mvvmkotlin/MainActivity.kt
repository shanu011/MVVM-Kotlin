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

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var  viewModel: EmployeeViewModel
    lateinit var employeeAdapter: EmployeeAdapter
    var employeeList = ArrayList<EmployeeList>()
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
         viewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]
        viewModel.fetchEmployees()
        viewModel.employeeAs.observe(this) { state ->
         if(state == ApiStatus.IsBeingHit){

             Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
         }else if(state == ApiStatus.ApiHit){
             employeeAdapter = EmployeeAdapter(this,viewModel.employeeList)
             binding.rvList.layoutManager = LinearLayoutManager(this)
             binding.rvList.adapter = employeeAdapter
           var data =   viewModel.employeeData.data?.size
             Toast.makeText(this, "${data}", Toast.LENGTH_SHORT).show()
         }else {
             println("SHow Error: ${viewModel.employeeData.message}")
         }
        }
    }
}
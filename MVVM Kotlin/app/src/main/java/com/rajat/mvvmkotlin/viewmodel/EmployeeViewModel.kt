package com.rajat.mvvmkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rajat.mvvmkotlin.EmployeeData
import com.rajat.mvvmkotlin.EmployeeList
import com.rajat.mvvmkotlin.repositeries.EmployeeRepository
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response

class EmployeeViewModel (): ViewModel(){
    private val repo = EmployeeRepository()
    val employeeAs: MutableLiveData<ApiStatus> = MutableLiveData<ApiStatus>(ApiStatus.NotHitOnce)
    var employeeData = EmployeeData()
    var employeeList = ArrayList<EmployeeList>()

    fun fetchEmployees() {
        employeeAs.value = ApiStatus.IsBeingHit
        repo.getEmployee().enqueue(object : retrofit2.Callback<EmployeeData>{
            override fun onResponse(
                call: Call<EmployeeData>,
                response: Response<EmployeeData>
            ) {
                if (response.isSuccessful && response.body() != null) {
                   employeeData = response.body()!!
                    val safeList = response.body()!!.data.orEmpty().filterNotNull()
                    val newItems = safeList.filter { newEmp ->
                        employeeList.none { it.id == newEmp.id }
                    }
                    employeeList.addAll(newItems)
                    employeeAs.value = ApiStatus.ApiHit
                }else{
                    employeeAs.value = ApiStatus.ApiHitWithError
                }
            }

            override fun onFailure(call: Call<EmployeeData>, t: Throwable) {
                println("Check OnFailure of FetchEmployees: ${t.message}")
                employeeAs.value = ApiStatus.ApiHitWithError

            }

        })
    }
}
package com.rajat.mvvmkotlin.repositeries

import com.rajat.mvvmkotlin.ApiClient
import com.rajat.mvvmkotlin.EmployeeData
import retrofit2.Call

class EmployeeRepository {

    fun getEmployee(): Call<EmployeeData> {
        return ApiClient.api.getUsers()
    }
}
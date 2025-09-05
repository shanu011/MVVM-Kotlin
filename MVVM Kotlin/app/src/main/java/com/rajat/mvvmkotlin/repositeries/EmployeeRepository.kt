package com.rajat.mvvmkotlin.repositeries

import com.rajat.mvvmkotlin.ApiClient
import com.rajat.mvvmkotlin.ApiInterface
import com.rajat.mvvmkotlin.EmployeeData
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class EmployeeRepository @Inject constructor(private val api: ApiInterface) {

    suspend fun getEmployee(): Response<EmployeeData> {
        return api.getUsers()
    }
}
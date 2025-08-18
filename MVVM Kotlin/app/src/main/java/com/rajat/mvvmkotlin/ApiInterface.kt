package com.rajat.mvvmkotlin

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("api/v1/employees") // Example endpoint
    fun getUsers(): Call<EmployeeData>

}

package com.rajat.mvvmkotlin

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("api/v1/employees") // Example endpoint
    suspend  fun getUsers(): Response<EmployeeData>

}

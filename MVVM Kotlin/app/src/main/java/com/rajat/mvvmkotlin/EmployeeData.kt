package com.rajat.mvvmkotlin


import com.squareup.moshi.Json

data class EmployeeData(
    @Json(name = "data")
    var `data`: List<EmployeeList?>? = null,
    @Json(name = "message")
    var message: String?= null,
    @Json(name = "status")
    var status: String?= null
)
data class Data(
    @Json(name = "employee_age")
    var employeeAge: Int? = null,
    @Json(name = "employee_name")
    var employeeName: String?=null,
    @Json(name = "employee_salary")
    var employeeSalary: Int?=null,
    @Json(name = "id")
    var id: Int?=null,
    @Json(name = "profile_image")
    var profileImage: String? = null
)
package com.rajat.mvvmkotlin


import com.squareup.moshi.Json

data class EmployeeList(
    @Json(name = "employee_age")
    var employeeAge: Int?,
    @Json(name = "employee_name")
    var employeeName: String?,
    @Json(name = "employee_salary")
    var employeeSalary: Int?,
    @Json(name = "id")
    var id: Int?,
    @Json(name = "profile_image")
    var profileImage: String?
)
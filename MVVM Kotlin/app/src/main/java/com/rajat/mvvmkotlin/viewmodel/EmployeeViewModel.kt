package com.rajat.mvvmkotlin.viewmodel

import android.Manifest
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rajat.mvvmkotlin.CheckConnectivity
import com.rajat.mvvmkotlin.EmployeeData
import com.rajat.mvvmkotlin.EmployeeList
import com.rajat.mvvmkotlin.repositeries.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(private val repo : EmployeeRepository, private val checkConnectivity: CheckConnectivity): ViewModel(){
   // private val repo = EmployeeRepository()
    private val joinEmployeeState : MutableLiveData<SealedClass<EmployeeData?>> = MutableLiveData<SealedClass<EmployeeData?>>(SealedClass.NoHit)
    val employeeAs : LiveData<SealedClass<EmployeeData?>> = joinEmployeeState
    private var callback: ConnectivityManager.NetworkCallback? = null


    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun observeConnectivity() {
        callback = checkConnectivity.registerNetworkCallback { isConnected->
            if(isConnected){
                fetchEmployees()
            }else{
                joinEmployeeState.value = SealedClass.ErrorInternet("No Internet Connection")
            }
        }
    }
    fun stopObserving() {
        callback?.let { checkConnectivity.unregisterNetworkCallback(it) }
        callback = null
    }
    fun fetchEmployees() {
        viewModelScope.launch {
            try {
                joinEmployeeState.value = SealedClass.Loading
                val response = repo.getEmployee()
                if (response.isSuccessful && response.body()?.data!=null) {
                    joinEmployeeState.value = SealedClass.Success(response.body())
                    // handle success
                    println("Employee Data: ${response.body()?.data}")
                } else {
                    // handle error response
                    joinEmployeeState.value = SealedClass.Error(response.message())
                }
            } catch (e: Exception) {
                // handle network error
                joinEmployeeState.value = SealedClass.Error(e.message.toString())
            //    println("CHeck All employee Fetch Exception: ${e.message}")
            }
        }
    }


}
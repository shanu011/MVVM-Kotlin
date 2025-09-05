package com.rajat.mvvmkotlin

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.telecom.ConnectionService
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckConnectivity @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun checkInternet(onResult: (Boolean) -> Unit) {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        onResult(isConnected)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun registerNetworkCallback(onResult: (Boolean) -> Unit): ConnectivityManager.NetworkCallback {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onResult(true)  // internet aya
            }

            override fun onLost(network: Network) {
                onResult(false) // internet gaya
            }
        }

        connectivityManager.registerNetworkCallback(request, callback)
        networkCallback = callback
        return callback
    }

    fun unregisterNetworkCallback(callback: ConnectivityManager.NetworkCallback?) {
        callback?.let {
            connectivityManager.unregisterNetworkCallback(it)
        }
        networkCallback = null
    }
}

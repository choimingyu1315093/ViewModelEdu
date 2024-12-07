package com.example.buildconfigedu.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodeledu.RetrofitInstance
import com.example.viewmodeledu.model.Album
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _albums = MutableStateFlow<List<Album>?>(null)
    val albums: StateFlow<List<Album>?>
        get() = _albums

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage

    init {
        fetchAlbums()
    }

    fun fetchAlbums() {
        val call = RetrofitInstance.api.getAlbums()
        call.enqueue(object : Callback<List<Album>> {
            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                if (response.isSuccessful) {
                    _albums.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.code()}"
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                _errorMessage.value = "Failed to load data: ${t.message}"
                _isLoading.value = false
            }
        })
    }
}
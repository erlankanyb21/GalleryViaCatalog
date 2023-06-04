package com.example.galleryviacatalog.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.galleryviacatalog.ui.home.api.ApiService
import com.example.galleryviacatalog.ui.home.api.RetrofitClient
import com.example.galleryviacatalog.ui.home.base.BaseViewModel
import com.example.galleryviacatalog.ui.home.model.GetProfile
import com.example.galleryviacatalog.ui.home.model.PhotoResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : BaseViewModel() {

    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }

    fun getStats(): LiveData<GetProfile> {
        return getS()
    }

    fun delete(id: Int): LiveData<Unit> {
        return deleteById(id)
    }

    private fun deleteById(id: Int): MutableLiveData<Unit> {
        val data = MutableLiveData<Unit>()
        viewModelScope.launch {
            apiService.deletePhoto(id).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    Log.e("jopa", "onResponse: ${response.body()}")
                    data.postValue(response.body())
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.wtf("alo", t.localizedMessage)
                }
            })
        }
        return data
    }

    private fun getS(): MutableLiveData<GetProfile> {
        val data = MutableLiveData<GetProfile>()
        viewModelScope.launch {
            apiService.getProf().enqueue(object : Callback<GetProfile> {
                override fun onResponse(call: Call<GetProfile>, response: Response<GetProfile>) {
                    Log.e("jopa", "onResponse: ${response.body()}")
                    data.postValue(response.body())
                }

                override fun onFailure(call: Call<GetProfile>, t: Throwable) {
                    Log.wtf("alo", t.localizedMessage)
                }
            })
        }
        return data
    }

    fun addGalleryPhoto(editBanner: ByteArray): MutableLiveData<PhotoResponse> {
        val data = MutableLiveData<PhotoResponse>()
        val body = editBanner.toRequestBody("image/png".toMediaTypeOrNull(), 0, editBanner.size)
        val photoBody = body.let { MultipartBody.Part.createFormData("photo", "photo.png", it) }
        viewModelScope.launch {
            apiService.addGalleryPhoto(photoBody).enqueue(object : Callback<PhotoResponse> {
                override fun onResponse(
                    call: Call<PhotoResponse>, response: Response<PhotoResponse>
                ) {
                    Log.e("jopa", "onResponse: ${response.body()}")
                    data.postValue(response.body())
                }

                override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                    Log.wtf("alo", t.localizedMessage)
                }
            })
        }
        return data
    }
}

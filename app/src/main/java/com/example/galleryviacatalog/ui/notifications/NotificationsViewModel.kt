package com.example.galleryviacatalog.ui.notifications

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.galleryviacatalog.ui.home.api.ApiService
import com.example.galleryviacatalog.ui.home.api.RetrofitClient
import com.example.galleryviacatalog.ui.home.base.BaseViewModel
import com.example.galleryviacatalog.ui.home.model.CatalogResponse
import com.example.galleryviacatalog.ui.home.model.GetProductsDto
import com.example.galleryviacatalog.ui.home.model.GetProfile
import com.example.galleryviacatalog.ui.home.model.PhotoResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsViewModel : BaseViewModel() {

    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }

    fun createCatalog(id:Int,name:String) : MutableLiveData<CatalogResponse>{
        val data = MutableLiveData<CatalogResponse>()
        viewModelScope.launch {
            apiService.createCatalog(businessId = id, name = name).enqueue(object : Callback<CatalogResponse> {
                override fun onResponse(
                    call: Call<CatalogResponse>, response: Response<CatalogResponse>
                ) {
                    if (response.isSuccessful){
                        Log.e("jopa", "onResponse: ${response.body()}")
                        data.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<CatalogResponse>, t: Throwable) {
                    Log.wtf("alo", t.localizedMessage)
                }
            })
        }
        return data
    }

    fun getProducts() : MutableLiveData<GetProductsDto>{
        val data = MutableLiveData<GetProductsDto>()
        viewModelScope.launch {
            apiService.getProducts().enqueue(object : Callback<GetProductsDto> {
                override fun onResponse(call: Call<GetProductsDto>, response: Response<GetProductsDto>) {
                    Log.e("jopa", "onResponse: ${response.body()}")
                    if (response.isSuccessful){
                        data.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<GetProductsDto>, t: Throwable) {
                    Log.wtf("alo", t.localizedMessage)
                }
            })
        }
        return data
    }

}
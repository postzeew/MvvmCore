package com.postzeew.mvvmcore.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.postzeew.mvvmcore.BaseViewModel
import com.postzeew.mvvmcore.BaseViewModelImpl
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

private const val BASE_URL = "https://worldtimeapi.org/api/"


interface MainViewModel : BaseViewModel {
    val dateTime: LiveData<String>

    fun onViewCreated()
}

class MainViewModelImpl @Inject constructor() : BaseViewModelImpl(), MainViewModel {
    override val dateTime = MutableLiveData<String>()

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WorldTimeApi::class.java)

    override fun onViewCreated() {
        viewModelScope.launch {
            executeBlockingAction(dateTime) {
                getDateTime()
            }
        }
    }

    private suspend fun getDateTime(): String {
        return api.getWorldTime().datetime
    }
}
package com.postzeew.mvvmcore.sample

import retrofit2.http.GET

interface WorldTimeApi {

    @GET("ip")
    suspend fun getWorldTime(): WorldTime
}
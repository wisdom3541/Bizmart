package com.example.bizmart

import com.example.bizmart.data.GeocodingResponse
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Query
import retrofit2.http.Url

interface GeocodingApi {
        @GET
        suspend fun reposList(@Url url: String?): Response<GeocodingResponse?>?

}
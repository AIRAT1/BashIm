package de.android.ayrathairullin.bashim.data

import retrofit2.http.GET
import retrofit2.http.Query


interface BashImApiService {
    @GET("api/get")
    fun searchQuotes(
            @Query("site") site : String,
            @Query("name") name : String,
            @Query("num") num : Int
            ): io.reactivex.Observable<List<Quote>>

    @GET("api/sources")
    fun searchSources(): io.reactivex.Observable<List<SourceOfQuotes>>
}
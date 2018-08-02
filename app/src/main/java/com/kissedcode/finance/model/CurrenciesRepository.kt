package com.kissedcode.finance.model

import com.kissedcode.finance.model.dto.CbrResponse
import com.kissedcode.finance.model.dto.Valute
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CbrApi {
    @GET("daily_json.js")
    fun getCurrencies(): Observable<CbrResponse>
}

object RemoteRepository {

    val cbrApi by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.cbr-xml-daily.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val api = retrofit.create(CbrApi::class.java)
        api
    }

    // interface ///////////////////////////////////////////////////////////////////////////////

    fun getValute() = cbrApi.getCurrencies().map { it.valute }
}

object CacheRepository {

    val remote = RemoteRepository

    var cache: Valute? = null

    // interface ///////////////////////////////////////////////////////////////////////////////

    fun getValute(): Observable<Valute?> {
        return if (cache != null) Observable.just(cache) else remote.getValute().doAfterNext { cache = it }
    }
}
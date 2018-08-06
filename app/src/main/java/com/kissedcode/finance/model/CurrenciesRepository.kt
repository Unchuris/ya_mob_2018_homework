package com.kissedcode.finance.model

import com.kissedcode.finance.model.dto.CbrResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface CbrApi {
    @GET("daily_json.js")
    fun getCurrencies(): Observable<CbrResponse>
}

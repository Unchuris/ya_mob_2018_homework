package com.kissedcode.finance.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Valute {

    @SerializedName("USD")
    @Expose
    var usd: USD? = null
    @SerializedName("EUR")
    @Expose
    var eur: EUR? = null
    @SerializedName("CNY")
    @Expose
    var cny: CNY? = null
}

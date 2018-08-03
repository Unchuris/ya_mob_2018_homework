package com.kissedcode.finance.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CbrResponse {

    @SerializedName("Date")
    @Expose
    var date: String? = null
    @SerializedName("PreviousDate")
    @Expose
    var previousDate: String? = null
    @SerializedName("PreviousURL")
    @Expose
    var previousURL: String? = null
    @SerializedName("Timestamp")
    @Expose
    var timestamp: String? = null
    @SerializedName("Valute")
    @Expose
    var valute: Valute? = null

}
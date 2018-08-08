package com.kissedcode.finance.model.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

data class IdleWallet(
        @PrimaryKey(autoGenerate = true) var IdleWalletId: Int? = null,
        var walletName: String,
        var walletValue: Double,
        @Embedded
    val currency: Currency
) : Serializable

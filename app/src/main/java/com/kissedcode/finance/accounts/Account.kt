package com.kissedcode.finance.accounts

import android.support.annotation.DrawableRes
import com.kissedcode.finance.R

class Account(val name: String,
              @DrawableRes
              var iconRes: Int = R.drawable.ic_launcher_foreground,
              var balance: Double = 0.0)
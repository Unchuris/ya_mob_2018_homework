package com.kissedcode.finance.statistics

import android.graphics.Color
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

fun getChart(yEntrys: ArrayList<PieEntry>): PieData {
    val pieDataSet = PieDataSet(yEntrys, "")
    pieDataSet.sliceSpace = 2f
    pieDataSet.valueTextSize = 12f

    val colors = ArrayList<Int>()
    colors.add(Color.BLUE)
    colors.add(Color.MAGENTA)
    colors.add(Color.GREEN)
    colors.add(Color.CYAN)
    colors.add(Color.YELLOW)
    colors.add(Color.RED)
    colors.add(Color.GRAY)

    pieDataSet.colors = colors

    val pieData = PieData(pieDataSet)
    pieData.setValueFormatter(PercentFormatter())

    return pieData
}

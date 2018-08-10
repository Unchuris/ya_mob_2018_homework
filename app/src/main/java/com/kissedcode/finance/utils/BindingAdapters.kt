package com.kissedcode.finance.utils

import android.arch.lifecycle.Observer
import android.arch.lifecycle.MutableLiveData
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.databinding.BindingAdapter
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.kissedcode.finance.utils.extension.getParentActivity

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value ?: View.VISIBLE })
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
    }
}

@BindingAdapter("data")
fun setData(view: PieChart, data: PieData?) {
    if (data != null) {
        view.holeRadius = 30f
        view.transparentCircleRadius = 40f
        view.setHoleColor(Color.WHITE)
        view.setTransparentCircleColor(Color.WHITE)
        view.setTransparentCircleAlpha(90)
        view.setUsePercentValues(true)
        view.setExtraOffsets(8f, 8f, 8f, 8f)
        view.legend.isEnabled = true
        view.legend.orientation = Legend.LegendOrientation.VERTICAL
        view.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        view.legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        view.description.isEnabled = false
        view.dragDecelerationFrictionCoef = 0.95f
        view.rotationAngle = 0f
        view.isHighlightPerTapEnabled = true
        view.animateY(1000, Easing.EasingOption.EaseInOutQuad)
        view.setEntryLabelColor(Color.BLACK)
        view.setEntryLabelTextSize(14f)
        view.isRotationEnabled = true
        view.holeRadius = 25f
        view.setTransparentCircleAlpha(0)
        view.isDrawHoleEnabled = false
        view.data = data
        view.isHighlightPerTapEnabled = true
        view.highlightValues(null)
        view.invalidate()
    }
}
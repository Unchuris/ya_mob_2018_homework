package com.kissedcode.finance.statistics

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kissedcode.finance.R
import com.kissedcode.finance.databinding.FragmentStatisticsBinding
import com.kissedcode.finance.injection.ViewModelFactory
import com.kissedcode.finance.main_screen.MainActivity
import com.kissedcode.finance.model.OperationType
import com.kissedcode.finance.utils.autoCleared
import kotlinx.android.synthetic.main.fragment_statistics.createButton
import kotlinx.android.synthetic.main.fragment_statistics.edEndDate
import kotlinx.android.synthetic.main.fragment_statistics.etStartDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class StatisticsFragment : Fragment() {

    private enum class CalendarRange { Start, End }

    private var dateInput = CalendarRange.Start

    private var calendar = Calendar.getInstance()

    private lateinit var viewModel: StatisticsViewModel

    private var startDate = Date()
    private var endDate = Date()

    var date: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        update()
    }

    private var binding: FragmentStatisticsBinding by autoCleared()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_statistics,
                container,
                false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).updateToolBar(R.string.screen_title_statistics)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity as AppCompatActivity)).get(StatisticsViewModel::class.java)
        binding.chartModel = viewModel
        binding.setLifecycleOwner(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etStartDate.setOnClickListener { v ->
            dateInput = CalendarRange.Start
            DatePickerDialog(v.context, R.style.AppTheme, date,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH))
                    .show()
        }

        edEndDate.setOnClickListener { v ->
            dateInput = CalendarRange.End
            DatePickerDialog(v.context, R.style.AppTheme, date,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH))
                    .show()
        }

        createButton.setOnClickListener {
            loadStatistics()
        }
    }

    private fun loadStatistics() {
        val dateStart = etStartDate.text.toString()
        val dateFinish = edEndDate.text.toString()

        if (!dateStart.isBlank() && !dateFinish.isBlank()) {
            viewModel.createDiagram(startDate, endDate, OperationType.SPEND)
        }

    }

    private fun update() {
        val format = "dd.MM.yyyy"
        val date = SimpleDateFormat(format, Locale.US)
        when (dateInput) {
            StatisticsFragment.CalendarRange.Start -> {
                etStartDate.setText(date.format(calendar.time))
                startDate = calendar.time
            }
            StatisticsFragment.CalendarRange.End -> {
                edEndDate.setText(date.format(calendar.time))
                endDate = calendar.time
            }
        }
    }

    companion object {
        fun newInstance(): StatisticsFragment {
            return StatisticsFragment()
        }
    }

}

package com.example.ageinhours

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.measureTimeMillis
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MainActivity : AppCompatActivity() {

    private lateinit var tvSelectedDate: TextView
    private lateinit var tvHours: TextView


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.tvButton)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvHours = findViewById(R.id.tvHours)

        button.setOnClickListener(){
            openDialog()
         }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun openDialog(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, MyActivityHelper.getDialogListener(this, dateView = tvSelectedDate, hours = tvHours), year, month, day)
        with(datePickerDialog) {
            datePicker.maxDate = System.currentTimeMillis()
            show()
        }
    }
}

object MyActivityHelper{
    fun getDialogListener(context: Context, dateView: TextView, hours: TextView) = DatePickerDialog.OnDateSetListener{_, selectedYear, selectedMonth, selectedDay ->
        Toast.makeText(context, "Year is $selectedYear, Month is ${selectedMonth+1}, Day is $selectedDay", Toast.LENGTH_LONG).show()

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val dateString = "$selectedDay/${selectedMonth+1}/$selectedYear"
        dateView.text = dateString
        val selectedDate = dateFormat.parse(dateString)?.time
        val currentDate = System.currentTimeMillis()
        selectedDate?.let {
            val difference = (currentDate - it) / 3600000
            hours.text = difference.toString()
        }

    }

}
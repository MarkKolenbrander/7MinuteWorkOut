package com.markkolenbrander.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNITS_VIEW"

    var currentVisibleView: String = METRIC_UNITS_VIEW

    private lateinit var toolbarBMI: Toolbar
    private lateinit var etMetricUnitWeight: EditText
    private lateinit var etMetricUnitHeight: EditText
    private lateinit var btnCalculateUnits: Button
    private lateinit var tvYourBMI: TextView
    private lateinit var tvBMIValue: TextView
    private lateinit var tvBMIType: TextView
    private lateinit var tvBMIDescription: TextView

    private lateinit var tilMetricUnitWeight: TextInputLayout
    private lateinit var tilMetricUnitHeight: TextInputLayout
    private lateinit var tilUSUnitWeight: TextInputLayout
    private lateinit var tilUSUnitHeightFeet: TextInputLayout

    private lateinit var llUSUnitsHeight: LinearLayout
    private lateinit var llDisplayBMIResults: LinearLayout
    private lateinit var etUSUnitWeight: EditText
    private lateinit var etUSUnitHeightFeet: EditText
    private lateinit var etUSUnitHeightInch: EditText

    private lateinit var rgUnits: RadioGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        toolbarBMI = findViewById(R.id.toolbar_bmi_activity)
        etMetricUnitWeight = findViewById(R.id.etMetricUnitWeight)
        etMetricUnitHeight = findViewById(R.id.etMetricUnitHeight)
        btnCalculateUnits = findViewById(R.id.btnCalculateUnits)
        tvYourBMI = findViewById(R.id.tvYourBMI)
        tvBMIValue = findViewById(R.id.tvBMIValue)
        tvBMIType = findViewById(R.id.tvBMIType)
        tvBMIDescription = findViewById(R.id.tvBMIDescription)
        tilMetricUnitWeight = findViewById(R.id.tilMetricUnitWeight)
        tilMetricUnitHeight = findViewById(R.id.tilMetricUnitHeight)
        tilUSUnitWeight = findViewById(R.id.tilUSUnitWeight)
        tilUSUnitHeightFeet = findViewById(R.id.tilUSUnitHeightFeet)
        llUSUnitsHeight = findViewById(R.id.llUSUnitsHeight)
        llDisplayBMIResults = findViewById(R.id.llDisplayBMIResult)
        etUSUnitWeight = findViewById(R.id.etUSUnitWeight)
        etUSUnitHeightFeet = findViewById(R.id.etUSUnitHeightFeet)
        etUSUnitHeightInch = findViewById(R.id.etUSUnitHeightInch)
        rgUnits = findViewById(R.id.rgUnits)

        window.statusBarColor = ContextCompat.getColor(this,R.color.colorMain)

        setSupportActionBar(toolbarBMI)

        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"
        }
        toolbarBMI.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            if (currentVisibleView.equals(METRIC_UNITS_VIEW)){
                metricCalculator()
            }else{
                usCalculator()
            }
        }

        makeVisibleMetricUnitsView()
        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUSUnitsView()
            }
        }

    }

    private fun makeVisibleUSUnitsView(){
        currentVisibleView = US_UNITS_VIEW
        tilMetricUnitWeight.visibility = View.GONE
        tilMetricUnitHeight.visibility = View.GONE

        etUSUnitWeight.text!!.clear()
        etUSUnitHeightFeet.text!!.clear()
        etUSUnitHeightInch.text!!.clear()

        tilUSUnitWeight.visibility = View.VISIBLE
        llUSUnitsHeight.visibility = View.VISIBLE

        llDisplayBMIResults.visibility =View.INVISIBLE

    }

    private fun makeVisibleMetricUnitsView(){
       currentVisibleView = METRIC_UNITS_VIEW
        tilMetricUnitWeight.visibility = View.VISIBLE
        tilMetricUnitHeight.visibility = View.VISIBLE

        etMetricUnitHeight.text!!.clear()
        etMetricUnitWeight.text!!.clear()

        tilUSUnitWeight.visibility = View.GONE
        llUSUnitsHeight.visibility = View.GONE

        llDisplayBMIResults.visibility =View.INVISIBLE

    }


    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        llDisplayBMIResults.visibility = View.VISIBLE
//        tvYourBMI.visibility = View.VISIBLE
//        tvBMIValue.visibility = View.VISIBLE
//        tvBMIType.visibility = View.VISIBLE
//        tvBMIDescription.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,
            RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue
        tvBMIType.text= bmiLabel
        tvBMIDescription.text = bmiDescription

    }


    private fun validateMetricUnits(): Boolean{
        var isValid = true

        if (etMetricUnitWeight.text.toString().isEmpty())
            isValid = false
        else if (etMetricUnitHeight.text.toString().isEmpty())
            isValid = false


        return isValid
    }

    private fun validateUSUnits(): Boolean{
        var isValid = true

        when {
            etUSUnitWeight.text.toString().isEmpty() -> isValid = false
            etUSUnitHeightFeet.text.toString().isEmpty() -> isValid = false
            etUSUnitHeightInch.text.toString().isEmpty() -> isValid = false
        }


        return isValid
    }

    private fun metricCalculator(){
        if (validateMetricUnits()) {
            val heightValue: Float = etMetricUnitHeight.text.toString().toFloat() / 100
            val weightValue: Float = etMetricUnitWeight.text.toString().toFloat()

            val bmi = weightValue / (heightValue * heightValue)
            displayBMIResult(bmi)
        } else {
            Toast.makeText(this@BMIActivity, "Please enter valid values.",
                    Toast.LENGTH_SHORT).show()
        }
    }

    private fun usCalculator(){
        if (validateUSUnits()) {
            val usUnitheightValueFeet: Float = etUSUnitHeightFeet.text.toString().toFloat()
            val usUnitheightValueInch: Float = etUSUnitHeightInch.text.toString().toFloat()
            val usUnitweightValue: Float = etUSUnitWeight.text.toString().toFloat()

            val heightValue = usUnitheightValueInch + usUnitheightValueFeet * 12

            val bmi = 703 * (usUnitweightValue / (heightValue * heightValue))

            displayBMIResult(bmi)
        } else {
            Toast.makeText(this@BMIActivity, "Please enter valid values.",
                    Toast.LENGTH_SHORT).show()

        }
    }



}
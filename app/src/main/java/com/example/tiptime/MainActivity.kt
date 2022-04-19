package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding
            : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nilaiMataUang.setOnKeyListener{ view, keyCode, _ -> handleKeyEvent(view, keyCode)}

        binding.textRupiah.isVisible = false
        binding.convertButton.setOnClickListener{convert()}
    }

    private fun convert() {
        val stringInTextField = binding.nilaiMataUang.text.toString()
        val nilaiUang = stringInTextField.toDoubleOrNull()
        if (nilaiUang == null) {
            binding.textRupiah.text =" "
            return
        }

        val convert = when (binding.moneyGroup.checkedRadioButtonId){
            R.id.euro -> 15481.40
            R.id.usd -> 14354.50
            R.id.yen -> 112.92
            else -> 3820.68
        }

        val totalRupiah = nilaiUang * convert
        val indonesianLocale = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(indonesianLocale).format(totalRupiah)
        binding.textRupiah.text = getString(R.string.convert_result, formatRupiah)
        binding.textRupiah.isVisible = true

    }

    private fun handleKeyEvent(view: View, keyCode:Int): Boolean{
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}
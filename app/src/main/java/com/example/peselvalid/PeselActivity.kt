package com.example.peselvalid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peselvalid.ui.theme.PeselValidTheme

class PeselActivity : AppCompatActivity() {

    val weights = listOf(9,7,3,1,9,7,3,1,9,7)
    var pesel: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pesel = intent.getStringExtra("pesel").toString()
        setContent {
            PeselValidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    peselInfo()
                }
            }
        }
    }

    @Composable
    fun peselInfo() {
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BasicText(text = "Your pesel: $pesel",
                    style = TextStyle(
                        //color = Red,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.W800,
                        fontStyle = FontStyle.Italic,
                        textDecoration = TextDecoration.Underline
                    )
                )
                if (pesel.length == 11 && pesel.toBigIntegerOrNull() != null) {
                    val result = validPesel(pesel = pesel)
                    Text(text = "Płeć: ${result[3]}")
                    Text(text = "Data urodzenia: ${result[0]}/${result[1]}/${result[2]}")
                    Text(text = "Pesel poprawny: ${result[4]}")
                } else {
                    Text(text = "Pesel jest niepoprawny")
                }
            }
        }
    }

    fun validPesel(pesel: String): ArrayList<String> {
        val result = ArrayList<String>()
        val number = pesel.toBigIntegerOrNull()
        if (pesel.length == 11 && number != null){
            var year = pesel.subSequence(0, 2).toString().toInt()
            var month = pesel.subSequence(2, 4).toString().toInt()
            if (month > 20) {
                month -= 20
                year += 2000
            } else {
                year += 1900
            }
            val day = pesel.subSequence(4, 6).toString().toInt()
            val sexInt = pesel.subSequence(9, 10).toString().toInt()
            var sex = "Woman"
            if (sexInt % 2 != 0)
                sex = "Man"
            //val validnumber = pesel.subSequence(10, 11).toString().toInt()
            //Log.i("Pesel", "Year: $year")
            //Log.i("Pesel", "Month: $month")
            //Log.i("Pesel", "Day: $day")
            //Log.i("Pesel", "Birthday: $day/$month/$year")
            //Log.i("Pesel", "Sex: $sex")
            //Log.i("Pesel", "isValid: ${verifyPesel(pesel = pesel)}")
            result.add(day.toString())
            result.add(month.toString())
            result.add(year.toString())
            result.add(sex)
            result.add(verifyPesel(pesel = pesel).toString())
        } else {
            //Log.i("Pesel", "Sex: $pesel")
        }
        return result
    }

    fun verifyPesel(pesel: String): Boolean {
        var acum = 0
        for (i in 0..9) {
            acum += weights[i] * pesel.subSequence(i, i+1).toString().toInt()
            //Log.i("Pesel", "pesel iter: ${pesel.subSequence(i, i+1).toString().toInt()}")
            //Log.i("Pesel", "pesel iter: $acum")
        }
        return acum % 10 == pesel.subSequence(10, 11).toString().toInt()
    }
}
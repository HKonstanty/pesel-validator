package com.example.peselvalid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peselvalid.ui.theme.PeselValidTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeselValidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    peselInput()
                }
            }
        }
    }

    @Composable
    fun peselInput() {
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 30.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val modifier = Modifier.padding(vertical = 4.dp)
                val peselState = remember { mutableStateOf(TextFieldValue()) }
                BasicText(text = "Enter your pesel and see what happens",
                        style = TextStyle(
                                //color = Red,
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.W800,
                                fontStyle = FontStyle.Italic,
                                textDecoration = TextDecoration.Underline
                        )
                )
                OutlinedTextField(
                        value = peselState.value,
                        onValueChange = { peselState.value = it },
                        label = { Text("PESEL") },
                        modifier = modifier
                )
                Button(
                        onClick = {
                            val pesel = peselState.value.text
                            val intent = Intent(this@MainActivity, PeselActivity::class.java)
                            intent.putExtra("pesel", pesel)
                            startActivity(intent)
                        },
                        modifier = modifier.then(Modifier.drawShadow(elevation = 3.dp))
                ) {
                    Text("Show info")
                }

            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        PeselValidTheme {
            peselInput()
        }
    }
}
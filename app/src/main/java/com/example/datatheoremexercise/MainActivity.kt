package com.example.datatheoremexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.datatheoremexercise.ui.theme.DataTheoremExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val instruction = "Once this app is installed, enable the xposed module and force restart sample app " +
                "to activate module. Uninstall this app to remove module"
        setContent {
            DataTheoremExerciseTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DisplayInstructions(
                        instructions = instruction
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayInstructions(instructions: String, modifier: Modifier = Modifier) {
    Text(
        text = "$instructions",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DataTheoremExerciseTheme {
        DisplayInstructions("Android")
    }
}
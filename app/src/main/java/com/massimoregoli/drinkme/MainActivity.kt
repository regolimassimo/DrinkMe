package com.massimoregoli.drinkme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.massimoregoli.drinkme.ui.theme.DrinkMeTextStyles
import com.massimoregoli.drinkme.ui.theme.DrinkMeTheme
import com.massimoregoli.drinkme.views.CocktailList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrinkMeTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Column {
                            Text(
                                modifier = Modifier.fillMaxWidth().padding(8.dp),
                                text = "DrinkMe",
                                textAlign = TextAlign.Center,
                                style = DrinkMeTextStyles.titleVeryHuge
                            )
                            Text(modifier = Modifier.fillMaxWidth(),
                                text="thecocktaildb.com",
                                textAlign = TextAlign.Center,
                                style = DrinkMeTextStyles.bodyLarge)
                        }}) { innerPadding ->
                    CocktailList(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DrinkMeTheme {
        Greeting("Android")
    }
}
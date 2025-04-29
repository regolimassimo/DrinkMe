package com.massimoregoli.drinkme.views

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.massimoregoli.drinkme.R
import com.massimoregoli.drinkme.model.CocktailViewModel
import com.massimoregoli.drinkme.model.CocktailViewModelFactory
import com.massimoregoli.drinkme.model.LargeCocktail
import com.massimoregoli.drinkme.model.SmallCocktail
import com.massimoregoli.drinkme.ui.theme.DrinkMeTextStyles
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun CocktailList(
    modifier: Modifier = Modifier,
    application: Application = LocalContext.current.applicationContext as Application
) {
    val factory = remember { CocktailViewModelFactory(application) }
    val filter = rememberSaveable { mutableStateOf("") }
    val lastId = rememberSaveable { mutableIntStateOf(-1) }
    val viewModel: CocktailViewModel = viewModel(factory = factory)
    val cocktails by viewModel.cocktails.observeAsState(emptyList())
    val drinks by viewModel.drinks.observeAsState(emptyList())

    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = filter.value, onValueChange = {
                filter.value = it
            },
            keyboardActions = KeyboardActions (onSearch={
                focusManager.clearFocus()
                viewModel.loadCocktails(filter.value)
            }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search),
            maxLines = 1,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            trailingIcon = {
                Icon(
                    Icons.Default.Search, contentDescription = "Search",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel.loadCocktails(filter.value)
                        })
            }
        )
        LazyColumn {
            if (cocktails.isEmpty()) {
                item {
                    Text(
                        text = "No cocktails found",
                        style = DrinkMeTextStyles.titleHuge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(cocktails) { cocktail ->
                    CocktailItem(cocktail, drinks, lastId) {
                        if (it == lastId.intValue)
                            lastId.intValue = -1
                        else {
                            viewModel.removeDrink()
                            viewModel.loadDrinks(it)
                            lastId.intValue = it
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CocktailItem(
    cocktail: SmallCocktail,
    largeCocktails: List<LargeCocktail>,
    lastId: MutableState<Int>,
    onClick: (id: Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFEE9)
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.clickable {
                    onClick(cocktail.idDrink.toInt())
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(cocktail.strDrinkThumb),
                    contentDescription = cocktail.strDrink,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    text = cocktail.strDrink,
                    style = DrinkMeTextStyles.titleHuge
                )
            }
            if (lastId.value == cocktail.idDrink.toInt()) {
                if (largeCocktails.isNotEmpty()) {
                    ShowInstructions(largeCocktails)
                }
            }
        }
    }
}

@Composable
fun ShowInstructions(largeCocktails: List<LargeCocktail>) {
    HorizontalInfo(largeCocktails)
    Title("Ingredients")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Ingredients(largeCocktails)
        Measures(largeCocktails)
    }
    Title("Instructions")
    Text(
        color = Color.Black,
        text = largeCocktails[0].strInstructions,
        style = DrinkMeTextStyles.bodyLarge
    )
}

@Composable
fun HorizontalInfo(largeCocktails: List<LargeCocktail>) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
            .padding(4.dp)
    ) {
        DrinkProperty(painterResource(id = R.drawable.ic_local_drink),
            largeCocktails[0].strAlcoholic)
        DrinkProperty(painterResource(id = R.drawable.ic_category),
            largeCocktails[0].strCategory)
        DrinkProperty(painterResource(id = R.drawable.ic_local_bar),
            largeCocktails[0].strGlass)
    }
}

@Composable
fun DrinkProperty(painterRes: Painter, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterRes,
            contentDescription = "Glass",
            tint = Color.Black
        )
        Text(
            color = Color.Black,
            text = label,
            style = DrinkMeTextStyles.bodyMedium
        )

    }
}

@Composable
fun Ingredients(largeCocktails: List<LargeCocktail>) {
    Column {
        for (ingredient in 1..15) {
            val c = LargeCocktail::class
            val field = c.members.find { it.name == "strIngredient$ingredient" }
            val value = field?.call(largeCocktails[0])
            if (value != null) {
                Text(
                    color = Color.Black,
                    text = "$value", textAlign = TextAlign.End,
                    style = DrinkMeTextStyles.bodyLarge
                )
            }
        }
    }
}

@Composable
fun Measures(largeCocktails: List<LargeCocktail>) {
    Column {
        for (measure in 1..15) {
            val c = LargeCocktail::class
            val field = c.members.find { it.name == "strMeasure$measure" }
            val value = field?.call(largeCocktails[0])
            if (value != null && value != "") {
                Text(
                    color = Color.Black,
                    text = "$value",
                    style = DrinkMeTextStyles.bodyLarge
                )
            }
        }
    }
}

@Composable
fun Title(text: String) {
    Text(
        color = Color.Black,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = text,
        style = DrinkMeTextStyles.titleLarge
    )
}

@Composable
fun SplashScreen() {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier=Modifier.fillMaxWidth(0.8f),
            contentScale = ContentScale.FillWidth)
    }
}
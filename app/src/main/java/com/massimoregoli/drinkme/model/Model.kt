package com.massimoregoli.drinkme.model

data class SmallCocktail(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String
)

data class SmallCocktailResponse(
    val drinks: List<SmallCocktail>?
)

data class LargeCocktail(
    val dateModified: String,
    val idDrink: String,
    val strAlcoholic: String,
    val strCategory: String,
    val strCreativeCommonsConfirmed: String,
    val strDrink: String,
    val strDrinkAlternate: Any,
    val strDrinkThumb: String,
    val strGlass: String,
    val strIBA: String,
    val strImageAttribution: String,
    val strImageSource: String,
    val strIngredient1: String,
    val strIngredient10: Any,
    val strIngredient11: Any,
    val strIngredient12: Any,
    val strIngredient13: Any,
    val strIngredient14: Any,
    val strIngredient15: Any,
    val strIngredient2: String,
    val strIngredient3: String,
    val strIngredient4: String,
    val strIngredient5: Any,
    val strIngredient6: Any,
    val strIngredient7: Any,
    val strIngredient8: Any,
    val strIngredient9: Any,
    val strInstructions: String,
    val strInstructionsDE: String,
    val strInstructionsES: String,
    val strInstructionsFR: String,
    val strInstructionsIT: String,
    val strMeasure1: String,
    val strMeasure10: Any,
    val strMeasure11: Any,
    val strMeasure12: Any,
    val strMeasure13: Any,
    val strMeasure14: Any,
    val strMeasure15: Any,
    val strMeasure2: String,
    val strMeasure3: String,
    val strMeasure4: Any,
    val strMeasure5: Any,
    val strMeasure6: Any,
    val strMeasure7: Any,
    val strMeasure8: Any,
    val strMeasure9: Any,
    val strTags: String,
    val strVideo: Any
)

data class CocktailDetailsResponse(
    val drinks: List<LargeCocktail>
)


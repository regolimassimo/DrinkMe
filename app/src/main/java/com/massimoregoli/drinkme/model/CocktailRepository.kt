package com.massimoregoli.drinkme.model

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
class CocktailRepository(private val context: Context) {

    private val url = "https://www.thecocktaildb.com/api/json/v1/1/search.php"
    private val urlById = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php"

    fun fetchCocktails(filter: String, onResult: (List<SmallCocktail>?) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val search = "$url?s=$filter"

        val request = StringRequest(Request.Method.GET, search,
            { response ->
                val cocktails = Gson().fromJson(response, SmallCocktailResponse::class.java).drinks
                onResult(cocktails)
            },
            { error ->
                error.printStackTrace()
                onResult(null)
            })

        queue.add(request)
    }

    fun fetchCocktail(id: Int, onResult: (List<LargeCocktail>?) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val search = "$urlById?i=$id"

        val request = StringRequest(Request.Method.GET, search,
            { response ->
                val cocktails = Gson().fromJson(response, CocktailDetailsResponse::class.java).drinks
                onResult(cocktails)
            },
            { error ->
                error.printStackTrace()
                onResult(null)
            })

        queue.add(request)
    }
}

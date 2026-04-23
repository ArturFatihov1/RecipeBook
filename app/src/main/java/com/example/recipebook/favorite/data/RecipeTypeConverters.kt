package com.example.recipebook.favorite.data

import androidx.room.TypeConverter
import com.example.recipebook.detail.data.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeTypeConverters {

    private val gson = Gson()
    private val stringList = object : TypeToken<List<String>>() {}.type
    private val ingredientsList = object : TypeToken<List<Ingredient>>() {}.type

    @TypeConverter
    fun fromInstructionsJson(value: String): List<String> = gson.fromJson(value, stringList)

    @TypeConverter
    fun instructionsToJson(v: List<String>): String = gson.toJson(v)

    @TypeConverter
    fun fromIngredientsJson(value: String): List<Ingredient> = gson.fromJson(value, ingredientsList)

    @TypeConverter
    fun ingredientsToJson(v: List<Ingredient>): String = gson.toJson(v)
}

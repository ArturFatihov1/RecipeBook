package com.example.recipebook.favorite.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipebook.detail.data.Ingredient

@Entity(tableName = "recipes")
data class RecipeEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "ingredients")
    val ingredients: List<Ingredient>,

    @ColumnInfo(name = "instructions")
    val instructions: List<String>
)

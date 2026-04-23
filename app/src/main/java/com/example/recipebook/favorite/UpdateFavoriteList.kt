package com.example.recipebook.favorite

import com.example.recipebook.detail.data.Recipe

interface UpdateFavoriteList {
    fun update(recipes: List<Recipe>)
}

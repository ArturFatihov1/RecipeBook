package com.example.recipebook.recipelist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.recipebook.recipelist.data.RecipeWithSettings

class RecipesDiffCallback(
    private val oldList: List<RecipeWithSettings>,
    private val newList: List<RecipeWithSettings>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldArticle = oldList[oldItemPosition]
        val newArticle = newList[newItemPosition]
        return oldArticle.id == newArticle.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldArticle = oldList[oldItemPosition]
        val newArticle = newList[newItemPosition]
        return oldArticle == newArticle
    }
}
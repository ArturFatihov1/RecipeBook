package com.example.recipebook.recipelist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipebook.R
import com.example.recipebook.databinding.ItemRecipeLayoutBinding
import com.example.recipebook.recipelist.data.RecipeWithSettings

class RecipeListAdapter(
    private val actionListener: RecipeActionListener
) : RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {

    var recipeList: List<RecipeWithSettings> = emptyList()
        set(newValue) {
            val diffCallback = RecipesDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeLayoutBinding.inflate(inflater, parent, false)
        return RecipeViewHolder(binding, actionListener::toggleFavoriteRecipe)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipeEntity = recipeList[position]
        holder.bind(recipeEntity)

        if (position == recipeList.size - PRELOAD_ARTICLES) {
            actionListener.loadMoreRecipes(recipeList.size)
        }

        holder.itemView.setOnClickListener {
            actionListener.detailRecipe(recipeEntity.id)
        }
    }

    override fun getItemCount(): Int = recipeList.size

    class RecipeViewHolder(
        private val binding: ItemRecipeLayoutBinding,
        private val onLikeClick: (recipeId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeWithSettings) {
            with(binding) {
                Glide.with(detailImageShortCard.context)
                    .load(recipe.imageUrl)
                    .placeholder(R.drawable.progress_animated)
                    .error(R.drawable.ic_error)
                    .into(detailImageShortCard)

                recipeLike.setImageResource(if (recipe.isFavorite) R.drawable.ic_like_selected else R.drawable.ic_like_unselected)
                recipeLike.setOnClickListener {
                    onLikeClick(recipe.id)
                }

                headerShortCard.text = recipe.title
                textShortCard.text = itemView.context.getString(
                    R.string.recipe_info,
                    recipe.ingredients.size,
                    0
                )
            }
        }
    }

    companion object {
        private const val PRELOAD_ARTICLES = 5
    }
}
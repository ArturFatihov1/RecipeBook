package com.example.recipebook.recipelist.presentation.search

import android.content.Context
import android.os.Parcelable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.core.widget.doAfterTextChanged
import com.example.recipebook.R
import com.example.recipebook.databinding.SearchBinding

class SearchView : FrameLayout, UpdateSearch {
    private lateinit var state: SearchUiState
    private val binding = SearchBinding.inflate(LayoutInflater.from(context), this, true)
    private val adapter = ArrayAdapter(context, R.layout.item_dropdown_search, emptyList<String>())

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        binding.searchInput.threshold = 3
        binding.searchInput.setAdapter(adapter)
    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = SearchSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as SearchSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(state: SearchUiState) {
        this.state = state
        state.update(this)
    }

    override fun update(text: String) {
        binding.searchInput.setText(text, false)
    }

    override fun update(variants: List<String>) {
        adapter.clear()
        adapter.addAll(variants)
        adapter.notifyDataSetChanged()
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        binding.searchInput.addTextChangedListener(textWatcher)
    }

    fun removeTextChangedListener(textWatcher: TextWatcher) {
        binding.searchInput.removeTextChangedListener(textWatcher)
    }

    fun onSufficient(onAction: (String) -> Unit) {
        binding.searchInput.doAfterTextChanged { text ->
            if (text != null && text.length >= 3) onAction.invoke(text.toString())
        }
    }

    fun onItemClicked(onClick: (String) -> Unit) {
        with(binding.searchInput) {
            setOnItemClickListener { parent, view, position, id ->
                val item = parent.getItemAtPosition(position) as String
                binding.searchInput.apply {
                    setText(item, false)
                    clearFocus()
                    dismissDropDown()
                }
                onClick(item)
            }
        }
    }
}

interface UpdateSearch {
    fun update(state: SearchUiState)
    fun update(text: String)
    fun update(variants: List<String>)
}
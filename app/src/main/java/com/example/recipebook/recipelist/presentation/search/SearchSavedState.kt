package com.example.recipebook.recipelist.presentation.search

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.os.Build

class SearchSavedState : View.BaseSavedState {

    private lateinit var state: SearchUiState

    constructor(superState: Parcelable) : super(superState)

      private constructor(parcelIn: Parcel) : super(parcelIn) {
       state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
             parcelIn.readSerializable(SearchUiState::class.java.classLoader, SearchUiState::class.java) as SearchUiState
        } else {
           parcelIn.readSerializable() as SearchUiState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): SearchUiState = state

    fun save(uiState: SearchUiState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<SearchSavedState> {
        override fun createFromParcel(parcel: Parcel): SearchSavedState = SearchSavedState(parcel)

        override fun newArray(size: Int): Array<SearchSavedState?> = arrayOfNulls(size)
    }
}
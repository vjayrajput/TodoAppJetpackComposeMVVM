package com.app.todo.common.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class NavigationResult : Parcelable {
    @Parcelize
    object Back : NavigationResult()

    @Parcelize
    data class Error(val message: String) : NavigationResult()

    companion object {
        const val KEY_RESULT = "navigation_result"
    }
}



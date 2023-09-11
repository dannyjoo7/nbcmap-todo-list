package com.jess.camp.todo.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoModel(
    val id:Int,
    val title: String?,
    val description: String?,
    var bookmark: Boolean = false,
) : Parcelable
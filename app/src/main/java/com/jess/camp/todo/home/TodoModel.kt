package com.jess.camp.todo.home

import android.os.Parcelable
import com.jess.camp.bookmark.BookmarkModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoModel(
    val id: Long,
    val title: String?,
    val description: String?,
    var bookmark: Boolean = false,
) : Parcelable

fun TodoModel.toBookmarkModel(): BookmarkModel {
    return BookmarkModel(
        id = id,
        title = title,
        description = description,
        isBookmark = bookmark
    )
}
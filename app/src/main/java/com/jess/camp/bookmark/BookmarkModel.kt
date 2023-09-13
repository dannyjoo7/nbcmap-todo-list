package com.jess.camp.bookmark

import com.jess.camp.todo.home.TodoModel

data class BookmarkModel(
    val id: Long,
    val title: String?,
    val description: String?,
    val isBookmark: Boolean = false
)

fun BookmarkModel.toTodoModel(): TodoModel {
    return TodoModel(
        id = id,
        title = title,
        description = description,
        bookmark = isBookmark
    )
}
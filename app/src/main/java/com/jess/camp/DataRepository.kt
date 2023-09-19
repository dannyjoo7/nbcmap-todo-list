package com.jess.camp

import com.jess.camp.bookmark.BookmarkModel
import com.jess.camp.todo.home.TodoModel

object DataRepository {
    private val todoList = mutableListOf<TodoModel>()
    private val bookMarkedList = mutableListOf<BookmarkModel>()

    fun getBookMarkedList(): MutableList<BookmarkModel> {
        return bookMarkedList
    }

    fun getTodoList(): MutableList<TodoModel> {
        return todoList
    }

    fun getBookMarkedSize(): Int {
        return bookMarkedList.size
    }

    fun getTodoListSize(): Int {
        return todoList.size
    }
}

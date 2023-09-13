package com.jess.camp

import com.jess.camp.todo.home.TodoModel

object TodoManager {
    private val bookMarkedList = mutableListOf<TodoModel>()
    private val todoList = mutableListOf<TodoModel>()

    fun getBookMarkedList(): MutableList<TodoModel> {
        return bookMarkedList
    }

    fun getNoneBookMarkedList(): MutableList<TodoModel> {
        return todoList
    }

    fun getBookMarkedSize(): Int {
        return bookMarkedList.size
    }

    fun getNoneListSize(): Int {
        return todoList.size
    }

    fun toggleBookmark(item: TodoModel, position: Int) {
        bookMarkedList.remove(item)
        todoList.add(item.copy(bookmark = !item.bookmark))
    }

    fun toggleNoneBookmark(item: TodoModel, position: Int) {
        todoList.remove(item)
        bookMarkedList.add(item.copy(bookmark = !item.bookmark))
    }
}

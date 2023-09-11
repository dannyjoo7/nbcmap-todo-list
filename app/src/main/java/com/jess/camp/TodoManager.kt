package com.jess.camp

import com.jess.camp.todo.home.TodoModel

object TodoManager {
    private val todoBookMarkedList = mutableListOf<TodoModel>()
    private val todoNoneBookMarkedList = mutableListOf<TodoModel>()

    fun getBookMarkedList(): MutableList<TodoModel> {
        return todoBookMarkedList
    }

    fun getNoneBookMarkedList(): MutableList<TodoModel> {
        return todoNoneBookMarkedList
    }

    fun getBookMarkedSize(): Int {
        return todoBookMarkedList.size
    }

    fun getNoneListSize(): Int {
        return todoNoneBookMarkedList.size
    }

    fun toggleBookmark(item: TodoModel, position: Int) {
        todoBookMarkedList.remove(item)
        todoNoneBookMarkedList.add(item.copy(bookmark = !item.bookmark))
    }

    fun toggleNoneBookmark(item: TodoModel, position: Int) {
        todoNoneBookMarkedList.remove(item)
        todoBookMarkedList.add(item.copy(bookmark = !item.bookmark))
    }
}

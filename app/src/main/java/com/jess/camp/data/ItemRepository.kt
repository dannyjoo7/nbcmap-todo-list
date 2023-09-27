package com.jess.camp.data

import com.jess.camp.bookmark.BookmarkModel
import com.jess.camp.todo.home.TodoModel
import java.util.concurrent.atomic.AtomicLong

class ItemRepository {
    private val todoList = mutableListOf<TodoModel>()
    private val bookMarkedList = mutableListOf<BookmarkModel>()

    private val idGenerate: AtomicLong = AtomicLong(0L)

    init {
        todoList.apply {
            for (i in 0 until 3) {
                add(
                    TodoModel(
                        idGenerate.getAndIncrement(),
                        "title $i",
                        "description $i"
                    )
                )
            }
        }
    }

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

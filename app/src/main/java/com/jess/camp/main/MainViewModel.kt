package com.jess.camp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jess.camp.DataRepository
import com.jess.camp.bookmark.BookmarkModel
import com.jess.camp.todo.home.TodoModel

class MainViewModel : ViewModel() {
    private val _todoList: MutableLiveData<MutableList<TodoModel>> =
        MutableLiveData(DataRepository.getTodoList())
    private val _bookMarkList: MutableLiveData<MutableList<BookmarkModel>> =
        MutableLiveData(DataRepository.getBookMarkedList())

    val todoList: LiveData<MutableList<TodoModel>> get() = _todoList
    val bookMarkList: LiveData<MutableList<BookmarkModel>> get() = _bookMarkList

    init {
        initList()
    }

    private fun initList() {
        val currentList = _todoList.value?.toMutableList() ?: mutableListOf()
        for (i in 0..4) {
            currentList.add(TodoModel(i.toLong(), "title $i", "description $i"))
        }
        _todoList.value = currentList
    }

    fun addBookmarkItem(
        bookmarkModel: BookmarkModel?
    ) {
        if (bookmarkModel == null) {
            return
        }

        val currentList = bookMarkList.value.orEmpty().toMutableList()
        currentList.add(bookmarkModel)
        _bookMarkList.value = currentList
    }
    
    fun removeBookmarkItem(
        position: Int?
    ) {
        if (position == null || position < 0) {
            return
        }

        val currentList = todoList.value.orEmpty().toMutableList()
        currentList.removeAt(position)
        _todoList.value = currentList
    }
}
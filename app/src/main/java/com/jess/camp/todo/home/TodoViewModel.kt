package com.jess.camp.todo.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {
    private val _todoList = MutableLiveData<List<TodoModel>>()

    init {
        initList()
    }

    val todoList: LiveData<List<TodoModel>>
        get() = _todoList

    fun initList() {
        val currentList = _todoList.value?.toMutableList() ?: mutableListOf()
        for (i in 0..4) {
            currentList.add(TodoModel(i.toLong(), "title $i", "description $i"))
        }
        _todoList.value = currentList
    }

    fun addTodo(item: TodoModel) {
        val currentList = _todoList.value?.toMutableList() ?: mutableListOf()
        currentList.add(item)
        _todoList.value = currentList
    }

    fun removeTodo(
        position: Int?
    ) {
        if (position == null) {
            return
        }
        val currentList = _todoList.value?.toMutableList() ?: mutableListOf()
        currentList.removeAt(position)
        _todoList.value = currentList
    }

    fun modifyTodo(
        todoModel: TodoModel?
    ) {
        if (todoModel == null) {
            return
        }
        val currentList = _todoList.value?.toMutableList() ?: mutableListOf()
        val position = currentList.indexOf(currentList.find { it.id == todoModel.id })
        currentList[position] = todoModel
        _todoList.value = currentList
    }

    fun bookmarkedItem(todoModel: TodoModel?, position: Int?) {
        if (position == null || position < 0 || todoModel == null) {
            return
        }
        val currentList = _todoList.value?.toMutableList() ?: mutableListOf()
        currentList[position].bookmark = !currentList[position].bookmark
        _todoList.value = currentList
    }

    fun updateList(): MutableList<TodoModel> {
        return _todoList.value?.toMutableList() ?: mutableListOf()
    }
}
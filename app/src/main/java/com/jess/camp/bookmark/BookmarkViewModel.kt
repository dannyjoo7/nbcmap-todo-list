package com.jess.camp.bookmark

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jess.camp.data.ItemRepository

class BookmarkViewModel(
    private val repository: ItemRepository,
) : ViewModel() {

    private val _list: MutableLiveData<List<BookmarkModel>> = MutableLiveData()
    val list: LiveData<List<BookmarkModel>> get() = _list

    init {
        _list.value = repository.getBookMarkedList()
    }

    fun updateBookmarkItems(items: List<BookmarkModel>) {
        _list.value = items
    }

    fun removeBookmarkItem(position: Int) {
        val currentList = list.value.orEmpty().toMutableList()
        currentList.removeAt(position)
        _list.value = currentList
    }
}

class BookmarkViewModelFactory(
    private val repository: ItemRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BookmarkViewModel(
            repository,
        ) as T
    }
}
package com.jess.camp.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jess.camp.DataRepository

class BookmarkViewModel : ViewModel() {

    private val _list: MutableLiveData<MutableList<BookmarkModel>> = MutableLiveData(DataRepository.getBookMarkedList())
    val list: LiveData<MutableList<BookmarkModel>> get() = _list

    fun addBookmarkItem(
        bookmarkModel: BookmarkModel?
    ) {
        if (bookmarkModel == null) {
            return
        }

        val currentList = list.value.orEmpty().toMutableList()
        currentList.add(bookmarkModel)
        _list.value = currentList
    }

    fun removeBookmarkItem(
        position: Int?
    ) {
        if (position == null || position < 0) {
            return
        }

        val currentList = list.value.orEmpty().toMutableList()
        currentList.removeAt(position)
        _list.value = currentList
    }
}
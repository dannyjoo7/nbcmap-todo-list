package com.jess.camp.bookmark


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jess.camp.data.ItemRepository
import com.jess.camp.databinding.BookmarkFragmentBinding
import com.jess.camp.main.MainActivity
import com.jess.camp.main.MainSharedEventForBookmark
import com.jess.camp.main.MainSharedViewModel

class BookmarkFragment : Fragment() {
    companion object {
        fun newInstance() = BookmarkFragment()
    }

    private var _binding: BookmarkFragmentBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainSharedViewModel by lazy {
        ViewModelProvider(requireActivity())[MainSharedViewModel::class.java]
    }

    private val bookmarkViewModel: BookmarkViewModel by lazy {
        ViewModelProvider(
            this,
            BookmarkViewModelFactory(ItemRepository())
        )[BookmarkViewModel::class.java]
    }

    private val listAdapter by lazy {
        BookmarkListAdapter { position, item ->
            modifyItemAtTodoTab(item)
            removeItem(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BookmarkFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initModel()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() = with(binding) {
        bookmarkList.adapter = listAdapter
    }

    private fun initModel() {
        bookmarkViewModel.list.observe(viewLifecycleOwner) {
            listAdapter.submitList(it.toMutableList())
        }
        mainViewModel.bookmarkEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is MainSharedEventForBookmark.UpdateBookmarkItems -> {
                    bookmarkViewModel.updateBookmarkItems(event.items)
//                    Log.d("book", event.items.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    private fun removeItem(
        position: Int
    ) {
        bookmarkViewModel.removeBookmarkItem(position)
    }

    private fun modifyItemAtTodoTab(
        item: BookmarkModel
    ) {
        mainViewModel.updateTodoItem(item)
    }
}
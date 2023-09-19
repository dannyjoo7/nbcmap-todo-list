package com.jess.camp.bookmark


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jess.camp.databinding.BookmarkFragmentBinding
import com.jess.camp.main.MainActivity

class BookmarkFragment : Fragment() {

    companion object {
        fun newInstance() = BookmarkFragment()
    }

    private var _binding: BookmarkFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookmarkViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(BookmarkViewModel::class.java)
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
        viewModel.list.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    fun addItem(item: BookmarkModel) {
        viewModel.addBookmarkItem(item)
    }

    private fun removeItem(
        position: Int
    ) {
        viewModel.removeBookmarkItem(position)
    }

    private fun modifyItemAtTodoTab(
        item: BookmarkModel
    ) {
        (activity as? MainActivity)?.modifyTodoItem(item)
    }
}
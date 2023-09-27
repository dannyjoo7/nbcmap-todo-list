package com.jess.camp.todo.home

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jess.camp.data.ItemRepository
import com.jess.camp.databinding.TodoFragmentBinding
import com.jess.camp.main.MainSharedEventForTodo
import com.jess.camp.main.MainSharedViewModel
import com.jess.camp.todo.content.TodoContentActivity
import com.jess.camp.todo.content.TodoContentType

class TodoFragment : Fragment() {

    companion object {
        fun newInstance() = TodoFragment()
    }

    private var _binding: TodoFragmentBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainSharedViewModel by lazy {
        ViewModelProvider(requireActivity())[MainSharedViewModel::class.java]
    }

    private val todoViewModel by lazy {
        ViewModelProvider(
            this,
            TodoViewModelFactory(ItemRepository())
        )[TodoViewModel::class.java]
    }

    private val editTodoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val entryType =
                    result.data?.getStringExtra(TodoContentActivity.EXTRA_TODO_ENTRY_TYPE)

                val position = result.data?.getIntExtra(TodoContentActivity.EXTRA_TODO_POSITION, -1)
                val todoModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_MODEL,
                        TodoModel::class.java
                    )
                } else {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_MODEL
                    )
                }

                // entry type 에 따라 기능 분리
                when (entryType) {
                    TodoContentType.EDIT.name -> {
                        modifyTodoItem(todoModel)
                    }

                    TodoContentType.REMOVE.name -> removeItemTodoItem(position)
                }
            }
        }

    private val listAdapter by lazy {
        TodoListAdapter(
            onClickItem = { position, item ->
                editTodoLauncher.launch(
                    TodoContentActivity.newIntentForEdit(
                        requireContext(),
                        position,
                        item
                    )
                )
            },
            onBookmarkChecked = { _, item ->
                modifyTodoItem(
                    todoModel = item
                )
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TodoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initModel()
    }

    private fun initView() = with(binding) {
        todoList.adapter = listAdapter
    }

    private fun initModel() {
        todoViewModel.list.observe(viewLifecycleOwner) {
            listAdapter.submitList(it.toMutableList())
            mainViewModel.bookmarkEvent
        }
        mainViewModel.todoEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is MainSharedEventForTodo.UpdateTodoItem -> {
                    todoViewModel.modifyTodoItem(event.item)
                }
            }
        }
    }

    fun addTodoItem(todoModel: TodoModel?) {
        if (todoModel == null) {
            return
        }
        todoViewModel.addTodoItem(todoModel)
    }

    private fun modifyTodoItem(
        todoModel: TodoModel?
    ) {
        todoViewModel.modifyTodoItem(todoModel)
        mainViewModel.updateBookmarkItems(listAdapter.currentList)
    }

    private fun removeItemTodoItem(position: Int?) {
        todoViewModel.removeTodoItem(position)
    }

    private fun addItemToBookmarkTab() {
        mainViewModel.updateBookmarkItems(listAdapter.currentList)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
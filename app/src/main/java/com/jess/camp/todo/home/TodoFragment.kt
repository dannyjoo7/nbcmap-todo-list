package com.jess.camp.todo.home

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jess.camp.databinding.TodoFragmentBinding
import com.jess.camp.main.MainActivity
import com.jess.camp.todo.content.TodoContentActivity
import com.jess.camp.todo.content.TodoContentType

class TodoFragment : Fragment() {

    companion object {
        fun newInstance() = TodoFragment()
    }

    private var _binding: TodoFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TodoViewModel::class.java)
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
            onBookmarkChecked = { position, item ->
                modifyTodoItem(
                    todoModel = item
                )
                addItemToBookmarkTab(item)
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
        viewModel.todoList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }

    fun addTodoItem(todoModel: TodoModel?) {
        if (todoModel == null) {
            return
        }
        viewModel.addTodo(todoModel)
    }

    /**
     * 아이템을 수정합니다.
     */
    fun modifyTodoItem(
        todoModel: TodoModel?
    ) {
        viewModel.modifyTodo(todoModel)
    }

    /**
     * 아이템을 삭제합니다.
     */
    private fun removeItemTodoItem(position: Int?) {
        viewModel.removeTodo(position)
    }

    private fun addItemToBookmarkTab(
        item: TodoModel
    ) {
        (activity as? MainActivity)?.addBookmarkItem(item)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
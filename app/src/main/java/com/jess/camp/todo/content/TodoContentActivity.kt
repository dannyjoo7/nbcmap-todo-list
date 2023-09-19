package com.jess.camp.todo.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.jess.camp.R
import com.jess.camp.DataRepository
import com.jess.camp.databinding.TodoAddActivityBinding
import com.jess.camp.todo.home.TodoModel


class TodoContentActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_TODO_ENTRY_TYPE = "extra_todo_entry_type"
        const val EXTRA_TODO_POSITION = "extra_todo_position"
        const val EXTRA_TODO_MODEL = "extra_todo_model"

        fun newIntentForAdd(
            context: Context
        ) = Intent(context, TodoContentActivity::class.java).apply {
            putExtra(EXTRA_TODO_ENTRY_TYPE, TodoContentType.ADD.name)
        }

        fun newIntentForEdit(
            context: Context,
            position: Int,
            todoModel: TodoModel
        ) = Intent(context, TodoContentActivity::class.java).apply {
            putExtra(EXTRA_TODO_ENTRY_TYPE, TodoContentType.EDIT.name)
            putExtra(EXTRA_TODO_POSITION, position)
            putExtra(EXTRA_TODO_MODEL, todoModel)
        }
    }

    private lateinit var binding: TodoAddActivityBinding

    private val type by lazy {
        intent.getStringExtra(EXTRA_TODO_ENTRY_TYPE)
    }

    private val todoModel by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(
                EXTRA_TODO_MODEL,
                TodoModel::class.java
            )
        } else {
            intent?.getParcelableExtra(
                EXTRA_TODO_MODEL
            )
        }
    }

    private val entryType by lazy {
        intent.getStringExtra(EXTRA_TODO_ENTRY_TYPE)
    }

    private val position by lazy {
        intent.getIntExtra(EXTRA_TODO_POSITION, -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TodoAddActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initView() = with(binding) {

        toolBar.setNavigationOnClickListener {
            finish()
        }

        submit.setOnClickListener {
            val intent = Intent().apply {
                putExtra(
                    EXTRA_TODO_ENTRY_TYPE,
                    entryType
                )
                putExtra(
                    EXTRA_TODO_POSITION,
                    position
                )
                putExtra(
                    EXTRA_TODO_MODEL,
                    TodoModel(
                        DataRepository.getTodoListSize().toLong() - 1,
                        todoTitle.text.toString(),
                        todoDescription.text.toString()
                    )
                )
            }
            setResult(Activity.RESULT_OK, intent)
            finish()

        }

        delete.setOnClickListener {
            AlertDialog.Builder(this@TodoContentActivity).apply {
                setMessage(R.string.todo_add_delete_dialog_message)
                setPositiveButton(
                    R.string.todo_add_delete_dialog_positive
                ) { _, _ ->
                    val intent = Intent().apply {
                        putExtra(
                            EXTRA_TODO_ENTRY_TYPE,
                            TodoContentType.REMOVE.name
                        )
                        putExtra(
                            EXTRA_TODO_POSITION,
                            position
                        )
                    }
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
                setNegativeButton(
                    R.string.todo_add_delete_dialog_negative
                ) { _, _ ->
                    // nothing
                }
            }.create().show()
        }

        // 버튼 이름 변경
        submit.setText(
            if (type == TodoContentType.EDIT.name) {
                R.string.todo_add_edit
            } else {
                R.string.todo_add_submit
            }
        )

        // 추가 버튼이 아닐 경우 삭제 버튼 노출
        delete.isVisible = type != TodoContentType.ADD.name
    }

    private fun initData() = with(binding) {
        if (type == TodoContentType.EDIT.name) {
            todoTitle.setText(todoModel?.title)
            todoDescription.setText(todoModel?.description)
        }
    }
}
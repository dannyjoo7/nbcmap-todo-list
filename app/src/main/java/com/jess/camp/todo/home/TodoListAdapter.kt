package com.jess.camp.todo.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jess.camp.databinding.TodoItemBinding

class TodoListAdapter(
    private val onClickItem: (Int, TodoModel) -> Unit,
    private val onBookmarkChecked: (Int, TodoModel) -> Unit
) : ListAdapter<TodoModel, TodoListAdapter.ViewHolder>(TodoItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickItem
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(
        val binding: TodoItemBinding,
        private val onClickItem: (Int, TodoModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoModel) = with(binding) {
            title.text = item.title
            description.text = item.description

            // 스위치 상태를 현재 아이템의 상태로 설정
            switchView.isChecked = item.bookmark

            container.setOnClickListener {
                onClickItem(adapterPosition, item)
            }

            // 스위치의 체크 상태가 변경될 때마다 아이템의 상태 업데이트
            switchView.setOnCheckedChangeListener { _, isChecked ->
                // 현재 바인딩된 아이템과 checked 된 값 비교 후 전달
                if (item.bookmark != isChecked) {
                    onBookmarkChecked(
                        adapterPosition,
                        item.copy(
                            bookmark = isChecked
                        )
                    )
                }
            }
        }
    }

}
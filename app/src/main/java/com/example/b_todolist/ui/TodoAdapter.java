package com.example.b_todolist.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b_todolist.R;
import com.example.b_todolist.model.Todo;
import com.example.b_todolist.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private final List<Todo> todos = new ArrayList<>();
    private final OnTodoClickListener listener;
    private float textSizeSp = 18f;

    public TodoAdapter(OnTodoClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.bind(todo, textSizeSp, listener);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<Todo> newTodos) {
        todos.clear();
        todos.addAll(newTodos);
        notifyDataSetChanged();
    }

    public Todo getTodoAt(int position) {
        return todos.get(position);
    }

    public void setTextSize(float sizeSp) {
        textSizeSp = sizeSp;
        notifyDataSetChanged();
    }

    public interface OnTodoClickListener {
        void onTodoClick(Todo todo);
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView descriptionText;
        private final TextView priorityText;
        private final TextView statusText;
        private final TextView categoriesText;
        private final TextView dueDateText;

        TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.text_todo_title);
            descriptionText = itemView.findViewById(R.id.text_todo_description);
            priorityText = itemView.findViewById(R.id.text_todo_priority);
            statusText = itemView.findViewById(R.id.text_todo_status);
            categoriesText = itemView.findViewById(R.id.text_todo_categories);
            dueDateText = itemView.findViewById(R.id.text_todo_due_date);
        }

        void bind(final Todo todo, float textSizeSp, final OnTodoClickListener listener) {
            titleText.setText(todo.getTitle());
            descriptionText.setText(todo.getDescription());
            priorityText.setText("Priorität: " + todo.getPriority());
            statusText.setText(todo.isDone() ? "Erledigt" : "Offen");
            categoriesText.setText("Kategorie(n): " + todo.getCategories());
            dueDateText.setText("Fällig bis: " + DateUtils.formatDate(todo.getDueDateMillis()));

            // Die Schriftgröße kommt später aus den Einstellungen.
            titleText.setTextSize(textSizeSp);
            descriptionText.setTextSize(textSizeSp - 4f);
            priorityText.setTextSize(textSizeSp - 4f);
            statusText.setTextSize(textSizeSp - 4f);
            categoriesText.setTextSize(textSizeSp - 4f);
            dueDateText.setTextSize(textSizeSp - 4f);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onTodoClick(todo);
                    }
                }
            });
        }
    }
}

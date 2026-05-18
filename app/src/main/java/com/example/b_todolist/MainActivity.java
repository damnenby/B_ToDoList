package com.example.b_todolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.b_todolist.data.TodoRepository;
import com.example.b_todolist.model.Todo;
import com.example.b_todolist.ui.TodoAdapter;

import java.util.List;

public class MainActivity extends Activity {
    private TodoAdapter todoAdapter;
    private TextView emptyText;
    private RecyclerView todoRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyText = findViewById(R.id.text_empty_todos);
        todoRecyclerView = findViewById(R.id.recycler_todos);

        todoAdapter = new TodoAdapter(new TodoAdapter.OnTodoClickListener() {
            @Override
            public void onTodoClick(Todo todo) {
                // Die Navigation zur Detail-Activity folgt in einem späteren Schritt.
            }
        });

        todoRecyclerView.setAdapter(todoAdapter);
        loadTodos();
    }

    private void loadTodos() {
        List<Todo> todos = TodoRepository.getAll();
        todoAdapter.setTodos(todos);

        if (todos.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            todoRecyclerView.setVisibility(View.GONE);
        } else {
            emptyText.setVisibility(View.GONE);
            todoRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}

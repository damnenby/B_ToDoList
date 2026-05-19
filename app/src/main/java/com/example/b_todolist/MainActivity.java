package com.example.b_todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b_todolist.data.TodoRepository;
import com.example.b_todolist.model.Todo;
import com.example.b_todolist.ui.TodoAdapter;

import java.util.List;

public class MainActivity extends Activity {
    private TodoAdapter todoAdapter;
    private TextView emptyText;
    private RecyclerView todoRecyclerView;
    private Button addTodoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTodoButton = findViewById(R.id.button_add_todo);
        emptyText = findViewById(R.id.text_empty_todos);
        todoRecyclerView = findViewById(R.id.recycler_todos);

        todoAdapter = new TodoAdapter(new TodoAdapter.OnTodoClickListener() {
            @Override
            public void onTodoClick(Todo todo) {
                openDetailActivity(todo.getId());
            }
        });

        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetailActivity(-1);
            }
        });

        todoRecyclerView.setAdapter(todoAdapter);
        setupSwipeToDelete();
        loadTodos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTodos();
    }

    private void openDetailActivity(int todoId) {
        // Explicit Intent: MainActivity startet gezielt die DetailActivity.
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_TODO_ID, todoId);
        startActivity(intent);
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

    private void setupSwipeToDelete() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }

                Todo todo = todoAdapter.getTodoAt(position);

                // Swipe löscht das Todo direkt aus der Laufzeit-Liste.
                TodoRepository.deleteById(todo.getId());
                loadTodos();
                Toast.makeText(MainActivity.this, R.string.todo_deleted, Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(todoRecyclerView);
    }
}

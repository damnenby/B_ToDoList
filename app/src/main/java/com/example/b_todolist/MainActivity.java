package com.example.b_todolist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.preference.PreferenceManager;

import com.example.b_todolist.data.TodoRepository;
import com.example.b_todolist.model.Todo;
import com.example.b_todolist.ui.TodoAdapter;

import java.util.List;

public class MainActivity extends Activity {
    private static final String PREF_FONT_SIZE = "font_size";
    private static final String DEFAULT_FONT_SIZE = "18";

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyFontSizePreference();
        loadTodos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_new_todo) {
            openDetailActivity(-1);
            return true;
        }

        if (itemId == R.id.action_sort_priority) {
            TodoRepository.sortByPriority();
            loadTodos();
            return true;
        }

        if (itemId == R.id.action_sort_date) {
            TodoRepository.sortByDate();
            loadTodos();
            return true;
        }

        if (itemId == R.id.action_settings) {
            openSettingsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSettingsActivity() {
        // Explicit Intent: MainActivity öffnet gezielt die SettingsActivity.
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
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

    private void applyFontSizePreference() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String fontSizeValue = preferences.getString(PREF_FONT_SIZE, DEFAULT_FONT_SIZE);

        try {
            float fontSize = Float.parseFloat(fontSizeValue);
            todoAdapter.setTextSize(fontSize);
        } catch (NumberFormatException exception) {
            todoAdapter.setTextSize(Float.parseFloat(DEFAULT_FONT_SIZE));
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

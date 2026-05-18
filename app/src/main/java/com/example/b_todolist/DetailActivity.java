package com.example.b_todolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.b_todolist.data.TodoRepository;
import com.example.b_todolist.model.Todo;
import com.example.b_todolist.util.DateUtils;

public class DetailActivity extends Activity {
    public static final String EXTRA_TODO_ID = "todo_id";
    private static final int NEW_TODO_ID = -1;

    private EditText titleEdit;
    private EditText descriptionEdit;
    private Spinner prioritySpinner;
    private EditText categoriesEdit;
    private CheckBox doneCheckBox;
    private TextView dueDateText;
    private Button deleteButton;
    private Todo currentTodo;
    private long dueDateMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleEdit = findViewById(R.id.edit_title);
        descriptionEdit = findViewById(R.id.edit_description);
        prioritySpinner = findViewById(R.id.spinner_priority);
        categoriesEdit = findViewById(R.id.edit_categories);
        doneCheckBox = findViewById(R.id.check_done);
        dueDateText = findViewById(R.id.text_due_date);
        deleteButton = findViewById(R.id.button_delete);

        setupPrioritySpinner();
        loadTodoFromIntent();
    }

    private void setupPrioritySpinner() {
        // Die Prioritäten sind in Teil 1 fest vorgegeben und kommen aus strings.xml.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.priority_values,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);
    }

    private void loadTodoFromIntent() {
        int todoId = getIntent().getIntExtra(EXTRA_TODO_ID, NEW_TODO_ID);

        if (todoId == NEW_TODO_ID) {
            prepareNewTodo();
            return;
        }

        currentTodo = TodoRepository.getById(todoId);
        if (currentTodo == null) {
            prepareNewTodo();
            return;
        }

        fillFields(currentTodo);
        deleteButton.setVisibility(View.VISIBLE);
    }

    private void prepareNewTodo() {
        currentTodo = null;
        dueDateMillis = 0L;
        dueDateText.setText(getString(R.string.todo_due_date_placeholder));
        deleteButton.setVisibility(View.GONE);
        setPrioritySelection("Mittel");
    }

    private void fillFields(Todo todo) {
        titleEdit.setText(todo.getTitle());
        descriptionEdit.setText(todo.getDescription());
        setPrioritySelection(todo.getPriority());
        categoriesEdit.setText(todo.getCategories());
        doneCheckBox.setChecked(todo.isDone());
        dueDateMillis = todo.getDueDateMillis();
        dueDateText.setText(getString(R.string.label_due_date) + ": " + DateUtils.formatDate(dueDateMillis));
    }

    private void setPrioritySelection(String priority) {
        SpinnerAdapter adapter = prioritySpinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            Object item = adapter.getItem(i);
            if (item != null && item.toString().equals(priority)) {
                prioritySpinner.setSelection(i);
                return;
            }
        }
    }
}

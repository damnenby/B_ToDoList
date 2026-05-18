package com.example.b_todolist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DetailActivity extends Activity {
    private Spinner prioritySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        prioritySpinner = findViewById(R.id.spinner_priority);
        setupPrioritySpinner();
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
}

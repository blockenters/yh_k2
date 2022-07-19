package com.blockent.actionbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editBody;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().setTitle(R.string.action_add_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTitle = findViewById(R.id.editTitle);
        editBody = findViewById(R.id.editBody);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo
                String title = editTitle.getText().toString().trim();
                String body = editBody.getText().toString().trim();

                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("body", body);

                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.menuSave){

            String title = editTitle.getText().toString().trim();
            String body = editBody.getText().toString().trim();

            Intent intent = new Intent();
            intent.putExtra("title", title);
            intent.putExtra("body", body);

            setResult(RESULT_OK, intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}





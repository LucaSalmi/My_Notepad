package com.khystudent.mynotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Edit extends AppCompatActivity {

    EditText title;
    EditText body;

    Button save;
    Button erase;

    String titleText;
    String bodyText;

    boolean confirm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title = findViewById(R.id.et_note_title);
        body = findViewById(R.id.et_note_body);
        save = findViewById(R.id.btn_save_note);
        erase = findViewById(R.id.btn_erase_note);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getInputText();
                DataManager newNote = new DataManager(Edit.this, titleText, bodyText);
                startActivity(backToMain());

            }
        });

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (confirm == false) {
                    Toast.makeText(Edit.this, "Are you sure?", Toast.LENGTH_LONG).show();
                    confirm = !confirm;
                    return;
                }

                confirm = false;
                startActivity(backToMain());
            }
        });


    }

    public void getInputText() {

        titleText = title.getText().toString();
        bodyText = body.getText().toString();


    }

    public Intent backToMain(){
        Intent goToMain = new Intent(Edit.this, MainActivity.class);
        return goToMain;
    }


}
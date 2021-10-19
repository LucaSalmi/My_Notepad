package com.khystudent.mynotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Edit extends AppCompatActivity {

    EditText title;
    EditText body;

    Button save;
    Button erase;

    String titleText;
    String bodyText;

    boolean confirmDelete = false;
    boolean fieldsNotEmpty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title = findViewById(R.id.et_note_title);
        body = findViewById(R.id.et_note_body);
        save = findViewById(R.id.btn_save_note);
        erase = findViewById(R.id.btn_erase_note);

        onNoteLoad();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getInputText();

                if (fieldsNotEmpty) {
                    DataManager newNote = new DataManager(Edit.this, titleText, bodyText, 0);
                    toaster(0);
                    startActivity(backToMain());
                }
            }
        });

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (confirmDelete == false) {
                    toaster(1);
                    confirmDelete = true;
                    return;
                }

                getInputText();
                DataManager newNote = new DataManager(Edit.this, titleText, bodyText, 1);
                toaster(2);
                confirmDelete = false;
                startActivity(backToMain());
            }
        });
    }

    /**
     * hämtar texten från edit text fält och kollar om de är tomma, i så fall visas ett felmeddellande upp
     */
    public void getInputText() {

        titleText = title.getText().toString();
        bodyText = body.getText().toString();

        if (TextUtils.isEmpty(titleText) && TextUtils.isEmpty(bodyText)) {
            showError(0);
            return;
        } else if (TextUtils.isEmpty(titleText)) {
            showError(1);
            return;
        } else if (TextUtils.isEmpty(bodyText)) {
            showError(2);
            return;
        }

        fieldsNotEmpty = true;
    }

    /**
     * skapar Intent objekt som går tillbaka till Main activity
     * @return Intent
     */
    public Intent backToMain() {
        Intent goToMain = new Intent(Edit.this, MainActivity.class);
        return goToMain;
    }

    /**
     * hämtar datan om man laddar ett fil som redan existerar
     */
    public void onNoteLoad() {

        String t = getIntent().getStringExtra("title");
        String b = getIntent().getStringExtra("body");

        title.setText(t);
        body.setText(b);

    }

    /**
     * visar felmeddelandet om nån fält är tom
     * @param id bestämmer vart ska felet visas upp
     */
    public void showError(int id) {

        switch (id) {

            case 0:
                title.setError("Field cannot be empty");
                body.setError("Field cannot be empty");
                break;
            case 1:
                title.setError("Field cannot be empty");
                break;
            case 2:
                body.setError("Field cannot be empty");
                break;
        }
    }

    /**
     * skapar Toast meddelande när anropad
     * @param id identifiera vilken toast ska visas upp.
     */
    public void toaster(int id) {

        switch (id) {

            case 0:
                Toast.makeText(Edit.this, "Note " + titleText + " saved", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(Edit.this, "Are you sure?", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(Edit.this, "Note " + titleText + " deleted", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
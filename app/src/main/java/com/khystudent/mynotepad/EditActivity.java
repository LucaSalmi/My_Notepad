package com.khystudent.mynotepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText title;
    EditText body;

    ImageButton save;
    ImageButton erase;

    String titleText;
    String bodyText;


    boolean fieldsNotEmpty = false;
    boolean checkIfSame;

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
                sendToSave();

            }
        });

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eraseButtonSecurity();
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

    public void sendToSave(){

        if (fieldsNotEmpty) {
            DataManager newNote = new DataManager(EditActivity.this, titleText, bodyText, true, checkIfSame);
            toaster(0);
            startActivity(backToMain());
        }
    }

    public void eraseButtonSecurity(){

        new AlertDialog.Builder(EditActivity.this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        getInputText();
                        DataManager deleteNote = new DataManager(EditActivity.this, titleText, bodyText, false, checkIfSame);
                        toaster(1);
                        startActivity(backToMain());
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    /**
     * skapar Intent objekt som går tillbaka till Main activity
     * @return Intent
     */
    public Intent backToMain() {
        Intent goToMain = new Intent(EditActivity.this, MainActivity.class);
        return goToMain;
    }

    /**
     * hämtar datan om man laddar ett fil som redan existerar
     */
    public void onNoteLoad() {

        String t = getIntent().getStringExtra("title");
        String b = getIntent().getStringExtra("body");
        checkIfSame = getIntent().getBooleanExtra("loadedNote",true);

        title.setText(t);
        body.setText(b);

        if(!checkIfSame){
            title.setInputType(InputType.TYPE_NULL);
        }

    }

    /**
     * visar felmeddelandet om nån fält är tom
     * @param id bestämmer vart ska felet visas upp
     */
    public void showError(int id) {

        switch (id) {

            case 0:
                title.setError(getString(R.string.error_text));
                body.setError(getString(R.string.error_text));
                break;
            case 1:
                title.setError(getString(R.string.error_text));
                break;
            case 2:
                body.setError(getString(R.string.error_text));
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
                Toast.makeText(EditActivity.this,
                        getString(R.string.toast_note) + titleText + getString(R.string.toast_saved),
                        Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(EditActivity.this,
                        getString(R.string.toast_note) + titleText + getString(R.string.toast_deleted),
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
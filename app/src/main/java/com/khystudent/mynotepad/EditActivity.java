package com.khystudent.mynotepad;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextBody;

    ImageButton imgButtonSave;
    ImageButton imgButtonErase;

    String titleText;
    String bodyText;


    boolean fieldsNotEmpty = false;
    boolean checkIfSame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        setFields(); // initiate Text/Edit views
        onNoteLoad(); // takes in extra elements from Intent if presents
        setListeners(); // initiate OnClickListeners

    }

    private void setFields() {

        editTextTitle = findViewById(R.id.et_note_title);
        editTextBody = findViewById(R.id.et_note_body);
        imgButtonSave = findViewById(R.id.btn_save_note);
        imgButtonErase = findViewById(R.id.btn_erase_note);
    }

    private void setListeners() {

        Animation bounce = AnimationUtils.loadAnimation(EditActivity.this, R.anim.bounce);

        imgButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onInputSave(bounce);
            }
        });

        imgButtonErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eraseButtonSecurity();
                imgButtonErase.startAnimation(bounce);
            }
        });

    }

    private void onInputSave(Animation bounce) {

        getInputText();
        sendToSave();
        imgButtonSave.startAnimation(bounce);
    }

    /**
     * get text from EdiText and converts it to String
     */
    private void getInputText() {

        titleText = editTextTitle.getText().toString();
        bodyText = editTextBody.getText().toString();

        fieldsNotEmpty = checkForErrors(titleText, bodyText);
    }

    /**
     * checks if fields are empty or if string titleText contains "."
     *
     * @param titleText string value of title Entertext
     * @param bodyText  string value of body Entertext
     * @return true if no error are present, false otherwise
     */
    private boolean checkForErrors(String titleText, String bodyText) {

        if (TextUtils.isEmpty(titleText) && TextUtils.isEmpty(bodyText)) {
            showError(0); //both fields are empty shows error in both fields
            return false;
        } else if (TextUtils.isEmpty(titleText)) {
            showError(1);//title field is empty, shows error in title field
            return false;
        } else if (TextUtils.isEmpty(bodyText)) {
            showError(2);//body field is empty, shows error in body field
            return false;
        } else if (titleText.contains(".")) {
            showError(3);//there is a "." in the title, shows error in title field
            return false;
        }

        return true;
    }

    private void sendToSave() {

        if (fieldsNotEmpty) {
            DataManager newNote = new DataManager(EditActivity.this, titleText, bodyText, true, checkIfSame);
            toaster(0);
            finish();
            //startActivity(backToMain());
        }
    }

    /**
     * creates an Alert Dialog to confirm if the user wants to erase a note
     */
    private void eraseButtonSecurity() {

        new AlertDialog.Builder(EditActivity.this)
                .setTitle(getString(R.string.alert_dialog_title))
                .setMessage(getString(R.string.alert_dialog_message))

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        getInputText();
                        DataManager deleteNote = new DataManager(EditActivity.this, titleText, bodyText, false, checkIfSame);
                        toaster(1);
                        finish();
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    /**
     * creates the Intent object that returns to MainActivity
     *
     * @return Intent
     */
    /*
    private Intent backToMain() {
        Intent goToMain = new Intent(EditActivity.this, MainActivity.class);
        return goToMain;
    }

     */



    /**
     * gets eventual data present in the Intent if the user is loading a note
     */
    private void onNoteLoad() {

        String t = getIntent().getStringExtra("title");
        String b = getIntent().getStringExtra("body");
        checkIfSame = getIntent().getBooleanExtra("loadedNote", true);

        editTextTitle.setText(t);
        editTextBody.setText(b);

        if (!checkIfSame) {
            editTextTitle.setInputType(InputType.TYPE_NULL);
        }
    }

    /**
     * Shows error if fields are empty or if a forbidden character is present
     *
     * @param id signals where to show the error message
     */
    private void showError(int id) {

        switch (id) {

            case 0:
                editTextTitle.setError(getString(R.string.error_text));
                editTextBody.setError(getString(R.string.error_text));
                break;
            case 1:
                editTextTitle.setError(getString(R.string.error_text));
                break;
            case 2:
                editTextBody.setError(getString(R.string.error_text));
                break;
            case 3:
                editTextTitle.setError(getString(R.string.error_dot_in_title));
                break;
        }
    }

    /**
     * creates Toast messages
     *
     * @param id signals which Toast to create
     */
    private void toaster(int id) {

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
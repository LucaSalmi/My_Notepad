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
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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


        setFields();
        onNoteLoad();
        setListeners();


    }

    public void setFields(){

        title = findViewById(R.id.et_note_title);
        body = findViewById(R.id.et_note_body);
        save = findViewById(R.id.btn_save_note);
        erase = findViewById(R.id.btn_erase_note);
    }

    public void setListeners(){

        Animation bounce = AnimationUtils.loadAnimation(EditActivity.this, R.anim.bounce);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getInputText();
                sendToSave();
                Log.d(TAG, "onClick: save anim starting");
                save.startAnimation(bounce);
            }
        });

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eraseButtonSecurity();
                Log.d(TAG, "onClick: erase anim starting");
                erase.startAnimation(bounce);

            }
        });
    }

    /**
     * get text from EdiText and converts it to String
     */
    public void getInputText() {

        titleText = title.getText().toString();
        bodyText = body.getText().toString();

        fieldsNotEmpty = checkForErrors(titleText, bodyText);
    }

    /**
     * checks if fields are empty or if string titleText contains "."
     * @param titleText string value of title Entertext
     * @param bodyText string value of body Entertext
     * @return true if no error are present, false otherwise
     */
    private boolean checkForErrors(String titleText, String bodyText){

        if (TextUtils.isEmpty(titleText) && TextUtils.isEmpty(bodyText)) {
            showError(0);
            return false;
        } else if (TextUtils.isEmpty(titleText)) {
            showError(1);
            return false;
        } else if (TextUtils.isEmpty(bodyText)) {
            showError(2);
            return false;
        }else if (titleText.contains(".")){
            showError(3);
            return false;
        }

        return true;
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
     * creates the Intent object that returns to MainActivity
     * @return Intent
     */
    public Intent backToMain() {
        Intent goToMain = new Intent(EditActivity.this, MainActivity.class);
        return goToMain;
    }

    /**
     * gets eventual data present in the Intent if the user is loading a note
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
     * Shows error if fields are empty or if a forbidden character is present
     * @param id signals where to show the error message
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
            case 3:
                title.setError(getString(R.string.error_dot_in_title));
                break;
        }
    }

    /**
     * creates Toast messages
     * @param id signals which Toast to create
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
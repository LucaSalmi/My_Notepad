package com.khystudent.mynotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewNotes;

    ImageButton imgButtonNewFile;

    String title;
    String body;

    boolean checkIfSame = false;
    File folder;

    ArrayList<String> notesMemory = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFields(); // initiate Text/Edit views
        setFolder(); // initiate folder directory
        setListeners(); // initiate OnClickListeners
        setAdapter(); // initiate Array adapter
    }


    private void setAdapter() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_list_view_item, DataManager.fillArray(folder, notesMemory));

        listViewNotes.setAdapter(adapter);
    }

    private void setListeners() {

        Animation bounce = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);

        listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                title = adapterView.getItemAtPosition(i).toString();
                body = DataManager.getFileData(folder, title, getString(R.string.extension_txt));
                toaster(0);
                startActivity(createIntent(1)); //id 1 adds extra information to the Intent
            }
        });


        imgButtonNewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgButtonNewFile.startAnimation(bounce);

                startActivity(createIntent(0)); //id 0 adds nothing to the Intent
            }
        });
    }

    private void setFolder() {

        folder = new File(this.getFilesDir(), "notes");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    private void setFields() {

        listViewNotes = findViewById(R.id.lv_notes_list);
        imgButtonNewFile = findViewById(R.id.btn_new_file);
    }


    /**
     * creates the Intent object that goes to EditActivity
     *
     * @return Intent
     */
    private Intent createIntent(int id) {

        Intent goToEdit = new Intent(MainActivity.this, EditActivity.class);

        switch (id) {
            case 0:
                break;
            case 1:
                goToEdit.putExtra("title", title);
                goToEdit.putExtra("body", body);
                goToEdit.putExtra("loadedNote", checkIfSame);
                break;
        }
        return goToEdit;
    }


    /**
     * creates Toast messages
     *
     * @param id signals which Toast to create
     */
    private void toaster(int id) {

        switch (id) {

            case 0:
                Toast.makeText(MainActivity.this,
                        getString(R.string.toast_note) + title + getString(R.string.toast_loaded),
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
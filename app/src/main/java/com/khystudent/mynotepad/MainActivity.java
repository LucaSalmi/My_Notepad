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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewNotes;

    ImageButton imgButtonNewFile;

    FloatingActionButton floatingExtraButton;
    FloatingActionButton floatingOptionOne;

    String title;
    String body;

    boolean checkIfSame = false;
    boolean isFABOpen = false;
    File folder;

    Intent goToActivity;

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

    @Override
    protected void onResume() {
        super.onResume();
        notesMemory.clear();
        setAdapter();

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
                createIntent(1);
                startActivity(goToActivity); //id 1 adds extra information to the Intent
            }
        });


        imgButtonNewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgButtonNewFile.startAnimation(bounce);
                createIntent(0);
                startActivity(goToActivity); //id 0 adds nothing to the Intent
            }
        });

        floatingExtraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showMenu();
                }else{
                    closeMenu();
                }
            }
        });

        floatingOptionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIntent(2);
                startActivity(goToActivity);
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
        floatingExtraButton = findViewById(R.id.options_button);
        floatingOptionOne = findViewById(R.id.option_one);
        floatingOptionOne.hide();
    }


    /**
     * creates the Intent object that goes to EditActivity
     *
     */
    private void createIntent(int id) {


        switch (id) {
            case 0:
                goToActivity = new Intent(MainActivity.this, EditActivity.class);
                break;
            case 1:
                goToActivity = new Intent(MainActivity.this, EditActivity.class);
                goToActivity.putExtra("title", title);
                goToActivity.putExtra("body", body);
                goToActivity.putExtra("loadedNote", checkIfSame);
                break;
            case 2:
                goToActivity = new Intent(MainActivity.this, ShoppingListActivity.class);
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
                Toast.makeText(MainActivity.this,
                        getString(R.string.toast_note) + title + getString(R.string.toast_loaded),
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showMenu(){
        isFABOpen = true;
        floatingOptionOne.show();
    }

    private void closeMenu(){
        isFABOpen = false;
        floatingOptionOne.hide();
    }


}
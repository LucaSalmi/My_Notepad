package com.khystudent.mynotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ListView notesList;

    Button newFile;

    ArrayList<String> notesMemory = new ArrayList<>();

    String title;
    String body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesList = findViewById(R.id.lv_notes_list);
        newFile = findViewById(R.id.btn_new_file);

        Animation bounce = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);

        File folder = new File(this.getFilesDir(), "notes");
        if (!folder.exists()) {
            folder.mkdir();
        }

//här fylls arrayen upp och kopplas sen till ListView
        fillArray(folder);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_list_view_item, notesMemory);
        notesList.setAdapter(adapter);

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                title = adapterView.getItemAtPosition(i).toString();
                getFileData(folder);

                startActivity(createIntent(1));
            }
        });


        newFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFile.startAnimation(bounce);

                startActivity(createIntent(0));
            }
        });
    }

    public void getFileData(File folder){

        File readFile = new File(folder, title + getString(R.string.extension_txt));
        try {
            Scanner sc = new Scanner(readFile);
            body = sc.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        toaster(0);
    }

    public Intent createIntent(int id){

        Intent goToEdit = new Intent(MainActivity.this, EditActivity.class);

        switch (id){
            case 0:
                break;
            case 1:
                goToEdit.putExtra("title", title);
                goToEdit.putExtra("body", body);
                break;
        }
        return goToEdit;
    }

    /**
     * fyller en Array List med alla txt filers namn för att visa de till användare
     * @param folder positionen av mappen med alla filer
     */
    public void fillArray(File folder) {

        File[] folderContent = folder.listFiles();

        if (folderContent != null) {

            for (File file : folderContent) {
                if (file.isFile()) {
                    String temp = file.getName();
                    temp = temp.substring(0, temp.indexOf("."));
                    notesMemory.add(temp);
                }
            }
        }
        Collections.reverse(notesMemory);
    }

    /**
     * skapar Toast meddelande när anropad
     * @param id identifiera vilken toast ska visas upp.
     */
    public void toaster(int id) {

        switch (id) {

            case 0:
                Toast.makeText(MainActivity.this,
                        getString(R.string.toast_note) + title + getString(R.string.toast_loaded),
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
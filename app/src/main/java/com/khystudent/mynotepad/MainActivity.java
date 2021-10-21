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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ListView notesList;

    ImageButton newFile;

    ArrayList<String> notesMemory = new ArrayList<>();

    String title;
    String body;

    boolean checkIfSame = false;
    File folder;
    File readFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFields();
        setFolder();
        setListeners();
        fillArray();
    }

    private void fillArray(){

        fillArray(folder);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_list_view_item, notesMemory);
        notesList.setAdapter(adapter);
    }

    private void setListeners() {

        Animation bounce = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);

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

    private void setFolder(){

        folder = new File(this.getFilesDir(), "notes");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    private void setFields(){

        notesList = findViewById(R.id.lv_notes_list);
        newFile = findViewById(R.id.btn_new_file);
    }

    /**
     * hämtar dat från txt file när man trycker på en element i lista
     * @param folder adressen på dyrectory där alla txt filer är
     */
    private void getFileData(File folder){

        readFile = new File(folder, title + getString(R.string.extension_txt));
        try {
            Scanner sc = new Scanner(readFile);
            body = sc.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        toaster(0);
    }

    private Intent createIntent(int id){

        Intent goToEdit = new Intent(MainActivity.this, EditActivity.class);

        switch (id){
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
     * fyller en Array List med alla txt filers namn för att visa de till användare
     * @param folder positionen av mappen med alla filer
     */
    private void fillArray(File folder) {

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
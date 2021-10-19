package com.khystudent.mynotepad;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

        File folder = new File(this.getFilesDir(), "notes");
        if (!folder.exists()) {
            folder.mkdir();
        }

        fillArray(folder);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesMemory);
        notesList.setAdapter(adapter);

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                title = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: " + title);

                File readFile = new File(folder, title + ".txt");
                try {
                    Scanner sc = new Scanner(readFile);
                    body = sc.nextLine();
                    Log.d(TAG, "onItemClick: " + body);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //title = title.substring(0, title.indexOf("."));

                toaster(0);

                Intent goToEdit = new Intent(MainActivity.this, Edit.class);
                goToEdit.putExtra("title", title);
                goToEdit.putExtra("body", body);
                startActivity(goToEdit);
            }
        });


        newFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToEdit = new Intent(MainActivity.this, Edit.class);
                startActivity(goToEdit);
            }
        });
    }

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
    }

    public void toaster(int id) {

        switch (id) {

            case 0:
                Toast.makeText(MainActivity.this, "Note " + title + " loaded", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
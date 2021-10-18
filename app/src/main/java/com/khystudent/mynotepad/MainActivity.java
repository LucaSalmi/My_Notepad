package com.khystudent.mynotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity{

    ListView notesList;

    Button newFile;

    ArrayList<String> notesMemory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesList = findViewById(R.id.lv_notes_list);
        newFile = findViewById(R.id.btn_new_file);


        fillArray();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesMemory);
        notesList.setAdapter(adapter);



        newFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToEdit = new Intent(MainActivity.this, Edit.class);
                startActivity(goToEdit);
            }
        });
    }

    public void fillArray(){

        File file = new File(this.getFilesDir(), "notes");
        if (!file.exists()) {
            file.mkdir();
        }



        String[] folderContent = file.list();
        Log.e("s", folderContent.toString());

        /* if (folderContent != null){
            for ( int i = 0; i < folderContent.length; i++) {


                try {
                    File readFile = new File(file,folderContent[i]);
                    Scanner scanner = new Scanner(readFile);
                    notesMemory.add(scanner.nextLine());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

         */
    }
}
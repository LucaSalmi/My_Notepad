package com.khystudent.mynotepad;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

class DataManager{

    private final AppCompatActivity CONTEXT;
    private String title;
    private String body;


    public DataManager(AppCompatActivity context, String title, String body) {

        this.CONTEXT = context;
        this.title = title;
        this.body = body;

        saveToTextFile(this);
    }


    public void saveToTextFile(DataManager obj) {

        File file = new File(obj.CONTEXT.getFilesDir(), "notes");
        if (!file.exists()) {
            file.mkdir();
        }

        try {
            File note = new File(file, obj.title + ".txt");

            PrintWriter writer = new PrintWriter(note);
            writer.write(obj.body);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addToArray(){

    }






}

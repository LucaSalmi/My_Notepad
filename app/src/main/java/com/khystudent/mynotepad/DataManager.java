package com.khystudent.mynotepad;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
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
    private int save;


    public DataManager(AppCompatActivity context, String title, String body, int save) {

        this.CONTEXT = context;
        this.title = title;
        this.body = body;
        this.save = save;
        Log.d(TAG, "DataManager: "+ this.save);
        checkErase(this);

    }

    private void checkErase(DataManager obj){

        if(obj.save == 0){
            Log.d(TAG, "DataManager: save is true");
            saveToTextFile(obj);

        }else if (obj.save == 1){
            Log.d(TAG, "DataManager: save is false");
            eraseTextFile(obj);
        }
    }

    private void eraseTextFile(DataManager obj) {

        File folder = getFolder(obj);
        String s = obj.title+".txt";
        File file = new File(folder, s);
        file.delete();


    }


    public void saveToTextFile(DataManager obj) {

       File folder = getFolder(obj);

        try {
            File note = new File(folder, obj.title + ".txt");

            PrintWriter writer = new PrintWriter(note);
            writer.write(obj.body);
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public File getFolder(DataManager obj){

        File folder = new File(obj.CONTEXT.getFilesDir(), "notes");
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }


}

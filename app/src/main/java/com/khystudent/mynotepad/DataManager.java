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
    private boolean save;
    private boolean checkIfSame;

    String extension = ".txt";

    /**
     * Constructor for DataManager
     * @param context context used to get acces to the files folder
     * @param title title of the note, used as file name
     * @param body the text that will be saved in the file
     * @param save boolean that determines if the file should be saved or erased
     */
    public DataManager(AppCompatActivity context, String title, String body, boolean save, boolean checkIfSame) {

        this.CONTEXT = context;
        this.title = title;
        this.body = body;
        this.save = save;
        this.checkIfSame = checkIfSame;
        Log.d(TAG, "DataManager: "+ this.save);
        checkErase(this);

    }

    /**
     * checks if the user wants to save or delete a file
     * @param obj
     */
    private void checkErase(DataManager obj){

        if(obj.save){
            Log.d(TAG, "DataManager: save is true");
            saveToTextFile(obj);

        }else{
            Log.d(TAG, "DataManager: save is false");
            eraseTextFile(obj);
        }
    }

    /**
     * deletes the file from phone memory
     * @param obj the object Data Manager that contains all the relevant data
     */
    private void eraseTextFile(DataManager obj) {

        File folder = getFolder(obj);
        String s = obj.title+".txt";
        File file = new File(folder, s);
        file.delete();
    }

    /**
     * saves the file in the phone memory
     * @param obj the object Data Manager that contains all the relevant data
     */
    private void saveToTextFile(DataManager obj) {

       File folder = getFolder(obj);
       int n = 0;

        try {
            File note = new File(folder, obj.title + extension);

//loopen kollar om det finns ett fil med samma namn och, i så fall, ändrar namnet till title + en siffra.
            if(obj.checkIfSame){
                while(note.exists()){
                    n++;
                    note = new File(folder, obj.title + n + extension);
                }
            }

            PrintWriter writer = new PrintWriter(note);
            writer.write(obj.body);
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * gets the directory where the files are
     * @param obj the object Data Manager that contains all the relevant data
     * @return the address as a File variable
     */
    private File getFolder(DataManager obj){

        File folder = new File(obj.CONTEXT.getFilesDir(), "notes");
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }
}

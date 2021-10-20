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

    String extension = ".txt";

    /**
     * Constructor till DataManager
     * @param context contextet som används för att får tillgång till filer
     * @param title titel på note som används som fil namn
     * @param body texten som sparas in i filen
     * @param save 0 eller 1 identifierar om filen ska sparas eller radera
     */
    public DataManager(AppCompatActivity context, String title, String body, boolean save) {

        this.CONTEXT = context;
        this.title = title;
        this.body = body;
        this.save = save;
        Log.d(TAG, "DataManager: "+ this.save);
        checkErase(this);

    }

    /**
     * kollar om man vill radera eller spara en fil och anropar rätt metod
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
     * raderar file från minnen
     * @param obj
     */
    private void eraseTextFile(DataManager obj) {

        File folder = getFolder(obj);
        String s = obj.title+".txt";
        File file = new File(folder, s);
        file.delete();
    }

    /**
     * sparar filen i minnnen
     * @param obj
     */
    public void saveToTextFile(DataManager obj) {

       File folder = getFolder(obj);
       int n = 0;

        try {
            File note = new File(folder, obj.title + extension);
//loopen kollar om det finns ett fil med samma namn och, i så fall, ändrar namnet till title + en siffra.
            while(note.exists()){
                n++;
                note = new File(folder, obj.title + n + extension);
            }

            PrintWriter writer = new PrintWriter(note);
            writer.write(obj.body);
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * hämtar mappen där filerna ligger
     * @param obj objektet vi manipulerar, hän används bara CONTEXT variabel
     * @return
     */
    public File getFolder(DataManager obj){

        File folder = new File(obj.CONTEXT.getFilesDir(), "notes");
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }
}

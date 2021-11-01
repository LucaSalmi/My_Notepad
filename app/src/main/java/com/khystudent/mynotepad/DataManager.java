package com.khystudent.mynotepad;

import static android.content.ContentValues.TAG;
import static android.provider.Settings.System.getString;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class DataManager {

    private final AppCompatActivity CONTEXT;
    private String title;
    private String body;
    private boolean save;
    private boolean checkIfSame;


    String extension = ".txt";


    /**
     * Constructor for DataManager
     *
     * @param context context used to get acces to the files folder
     * @param title   title of the note, used as file name
     * @param body    the text that will be saved in the file
     * @param save    boolean that determines if the file should be saved or erased
     */
    public DataManager(AppCompatActivity context, String title, String body, boolean save, boolean checkIfSame) {

        this.CONTEXT = context;
        this.title = title;
        this.body = body;
        this.save = save;
        this.checkIfSame = checkIfSame;
        checkErase(this);

    }

    /**
     * checks if the user wants to save or delete a file
     *
     * @param obj
     */
    private void checkErase(DataManager obj) {

        if (obj.save) {
            saveToTextFile(obj);

        } else {
            eraseTextFile(obj);
        }
    }

    /**
     * deletes the file from phone memory
     *
     * @param obj the object Data Manager that contains all the relevant data
     */
    private void eraseTextFile(DataManager obj) {

        File folder = getFolder(obj);
        String s = obj.title + ".txt";
        File file = new File(folder, s);
        file.delete();
    }

    /**
     * saves the file in the phone memory
     *
     * @param obj the object Data Manager that contains all the relevant data
     */
    private void saveToTextFile(DataManager obj) {

        File folder = getFolder(obj);
        int n = 1;

        try {
            File note = new File(folder, obj.title + extension);

//this loop checks if two files have the same same and in that case, before saving the new one, adds a number to the name.
            if (obj.checkIfSame) {
                while (note.exists()) {
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
     *
     * @param obj the object Data Manager that contains all the relevant data
     * @return the address as a File variable
     */
    private File getFolder(DataManager obj) {

        File folder = new File(obj.CONTEXT.getFilesDir(), "notes");
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }

    /**
     * retrieves the data when user presses an element in the list
     *
     * @param folder    directory where files are
     * @param title     name of the file
     * @param extension String resource ".txt"
     * @return
     */
    public static String getFileData(File folder, String title, String extension) {

        String body = "";
        File readFile;

        readFile = new File(folder, title + extension);
        try {
            Scanner sc = new Scanner(readFile);
            body = sc.nextLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return body;
    }

    /**
     * fills an ArrayList with the names of the text files (the title the user has given them)
     *
     * @param folder      directory where files are
     * @param notesMemory ArrayList containing the files names that will be showed
     * @return
     */
    public static ArrayList<String> fillArray(File folder, ArrayList<String> notesMemory) {

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
        return notesMemory;
    }

    /**
     * gets called to retrieve all elements in a saved shopping list
     * @param folder folder directory
     * @param title title of note
     * @param extension .txt string
     * @return
     */
    public static ArrayList<String> getShopListData(File folder, String title, String extension) {

        ArrayList<String> shopItems = new ArrayList<>();
        String body = "placeholder";
        File readFile;


        readFile = new File(folder, title + extension);
        try {
            Scanner sc = new Scanner(readFile);

            while(sc.hasNext()){

                body = sc.nextLine();
                shopItems.add(body);
            }
            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.reverse(shopItems);
        return shopItems;
    }


}

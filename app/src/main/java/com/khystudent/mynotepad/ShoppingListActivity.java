package com.khystudent.mynotepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ShoppingListActivity extends AppCompatActivity {

    EditText item;

    Button add;
    FloatingActionButton multiFuncBtn;
    FloatingActionButton btnFunctionOne;
    FloatingActionButton btnFunctionTwo;

    ListView itemsList;

    ArrayList<String> shopItems = new ArrayList<>();

    ArrayAdapter<String> adapter;

    String date = new SimpleDateFormat("dd-MM", Locale.getDefault()).format(new Date());

    boolean checkIfSame;
    boolean isFABOpen = false;
    boolean emptyList;

    String listName;
    String listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_two);

        setFields();
        setListeners();
        onNoteLoad();
        setAdapter();

    }

    private void setFields(){
        item = findViewById(R.id.et_new_item);
        add = findViewById(R.id.btn_add_item);
        itemsList = findViewById(R.id.shop_list_items);
        multiFuncBtn = findViewById(R.id.multi_function_btn);
        btnFunctionOne = findViewById(R.id.save_floating);
        btnFunctionTwo = findViewById(R.id.erase_floating);
        closeMenu();

    }

    private void setListeners(){

        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                shopItems.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputField();
                adapter.notifyDataSetChanged();

            }
        });

        btnFunctionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkEmptyList()){

                    prepareToSave();
                }

            }
        });

        btnFunctionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eraseButtonSecurity();
            }
        });

        multiFuncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFABOpen) {
                    showMenu();
                } else {
                    closeMenu();
                }
            }
        });
    }

    /**
     * reads the input field and adds the element to the array
     */
    private void getInputField(){

        String toAdd = item.getText().toString();

        if (toAdd.isEmpty()){

            return;

        }else if (!checkForDoubles(toAdd)){

            Toast.makeText(ShoppingListActivity.this, getString(R.string.toast_double_item_input), Toast.LENGTH_SHORT).show();
            return;

        }else {

            shopItems.add(toAdd);
            item.setText("");
        }

    }

    private boolean checkEmptyList(){

        if (shopItems.isEmpty()){

            Toast.makeText(ShoppingListActivity.this, getString(R.string.toast_empty_list), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setAdapter(){

        adapter = new ArrayAdapter<>(this, R.layout.my_list_view_item, shopItems);
        itemsList.setAdapter(adapter);

    }

    /**
     * saves all elements in array to a string with a line breaker
     */
    private void prepareToSave(){

        listName = getString(R.string.shopping_list_baseline) + date;

        String temp = "";
        for (String s: shopItems) {

            temp = s + "\n" + temp;
        }

        listItems = temp;
        createSaveObj();

    }

    /**
     * creates the obj of DataManager class to save the list
     */
    private void createSaveObj(){

        DataManager shopList = new DataManager(ShoppingListActivity.this,
                listName, listItems,true, checkIfSame);
        finish();

    }

    /**
     * creates the obj of DataManager class to delete the list
     */
    private void createDeleteObj(){

        DataManager deleteNote = new DataManager(ShoppingListActivity.this,
                listName, listItems, false, checkIfSame);
        finish();

    }

    private void eraseButtonSecurity() {

        new AlertDialog.Builder(ShoppingListActivity.this)
                .setTitle(getString(R.string.alert_dialog_title))
                .setMessage(getString(R.string.alert_dialog_message))

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        createDeleteObj();
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private boolean checkForDoubles(String toAdd){

        for (String s: shopItems) {

            if (s.equals(toAdd)){
                return false;
            }
        }
        return true;
    }

    private void onNoteLoad() {

        shopItems = getIntent().getStringArrayListExtra("loaded array");
        checkIfSame = getIntent().getBooleanExtra("loadedNote", true);
        listName = getIntent().getStringExtra("listName");

    }

    private void showMenu() {

        isFABOpen = true;
        btnFunctionOne.show();
        btnFunctionTwo.show();
    }

    private void closeMenu() {

        isFABOpen = false;
        btnFunctionOne.hide();
        btnFunctionTwo.hide();
    }

}
package com.khystudent.mynotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    EditText title;
    EditText item;

    Button add;
    ImageButton saveBtn;

    ListView itemsList;

    ArrayList<String> shopItems = new ArrayList<>();

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_two);

        setFields();
        setListeners();
        setAdapter();

    }

    private void setFields(){
        title = findViewById(R.id.et_note_title);
        item = findViewById(R.id.et_new_item);
        add = findViewById(R.id.btn_add_item);
        itemsList = findViewById(R.id.shop_list_items);
        saveBtn = findViewById(R.id.save_button);
    }

    private void setListeners(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputField();
                adapter.notifyDataSetChanged();

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(ShoppingListActivity.this, R.anim.bounce);
                saveBtn.startAnimation(bounce);
                prepareToSave();

            }
        });
    }

    private void getInputField(){
        String toAdd = item.getText().toString();
        if (toAdd.isEmpty()){
            return;
        }else{
            shopItems.add(toAdd);
            item.setText("");
        }
    }

    private void setAdapter(){
         adapter = new ArrayAdapter<>(this, R.layout.my_list_view_item, shopItems);
        itemsList.setAdapter(adapter);
    }

    private void prepareToSave(){

        String temp = "";
        for (String s: shopItems) {
            temp = s + "/n" + temp;
        }

        DataManager shopList = new DataManager(ShoppingListActivity.this,
                title.getText().toString(), temp,true, true);
    }

}
package com.khystudent.mynotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    EditText title;
    EditText item;

    Button add;

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
    }

    private void setListeners(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputField();
                adapter.notifyDataSetChanged();

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

}
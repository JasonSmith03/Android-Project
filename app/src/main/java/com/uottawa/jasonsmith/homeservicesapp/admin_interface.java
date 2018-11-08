package com.uottawa.jasonsmith.homeservicesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class admin_interface extends AppCompatActivity {

    EditText editText;
    Button btn;
    ListView lv;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_interface);

        editText = (EditText) findViewById(R.id.service);
        btn = (Button) findViewById(R.id.addServiceBtn);
        lv = (ListView) findViewById(R.id.listViewServices);

        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(arrayAdapter);

        addServiceClick();
    }

    public void addServiceClick(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = editText.getText().toString();
                arrayList.add(result);
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

}

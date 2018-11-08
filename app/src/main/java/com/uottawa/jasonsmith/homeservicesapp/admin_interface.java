package com.uottawa.jasonsmith.homeservicesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class admin_interface extends AppCompatActivity {

    EditText editText;
    Button addBtn, removeBtn;
    ListView lv;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_interface);

        editText = (EditText) findViewById(R.id.service);
        addBtn = (Button) findViewById(R.id.addServiceBtn);
        removeBtn = (Button) findViewById(R.id.removeServiceBtn);
        lv = (ListView) findViewById(R.id.listViewServices);

        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arrayList);
        lv.setAdapter(arrayAdapter);
        addServiceClick();
        removeServiceClick();
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    EditText serviceInput = (EditText) findViewById(R.id.service);
//    String service = serviceInput.getText().toString();

    public void addServiceClick(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = editText.getText().toString();
                arrayList.add(result);
                arrayAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }

    public void removeServiceClick(){
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray positionChecker = lv.getCheckedItemPositions();
                int ctr = lv.getCount();
                for(int item = ctr - 1; item >= 0; item--){
                    if(positionChecker.get(item)){
                        arrayAdapter.remove(arrayList.get(item));
                    }
                }
                positionChecker.clear();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

}

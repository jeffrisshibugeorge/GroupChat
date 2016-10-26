package com.maps.jeffris.groupchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class chatroom extends AppCompatActivity {

    private Button btnAdd;
    private EditText roomname;
    private ListView roomlist;
    private ArrayAdapter<String> roomAdapter;
    private ArrayList<String> roomArray = new ArrayList<>();

    String name;
    private DatabaseReference root =
            FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        btnAdd = (Button) findViewById(R.id.buttonAdd);
        roomname = (EditText) findViewById(R.id.txtRoom);
        roomlist = (ListView) findViewById(R.id.listRoom);
        name = getIntent().getExtras().get("user_name").toString();

        roomAdapter = new
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, roomArray);
        roomlist.setAdapter(roomAdapter);

        //request_user_name();

        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(roomname.getText().toString().trim(), "");
                        root.updateChildren(map);
                    }
                }

        );

        root.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Set<String> set = new HashSet<String>();
                        Iterator i = dataSnapshot.getChildren().iterator();

                        while (i.hasNext()) {
                            set.add(((DataSnapshot) i.next()).getKey());
                        }
                        roomArray.clear();
                        roomArray.addAll(set);
                        roomAdapter.notifyDataSetChanged();
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

        roomlist.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new
                                Intent(getApplicationContext(),chatcomm.class);
                        intent.putExtra("room_name",((TextView)view).getText().toString());
                        intent.putExtra("user_name",name);
                        startActivity(intent);
                    }
                }
        );

    }

    private void request_user_name() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Name");

        final EditText inputfield = new EditText(this);
        builder.setView(inputfield);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name = inputfield.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();

    }

}
package com.example.ft_hangouts;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static int COLOR_ID = 0xFFCCCCCC;
    private ListView list;
    private DBContacts db;
    private FloatingActionButton addButton;
    private String dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            dateTime = savedInstanceState.getString("param");   //.getLong("param");
            Toast.makeText(this, "Time : " + dateTime, Toast.LENGTH_SHORT).show();
            Log.i("DATABASE", String.format("Insert not done : " + dateTime,"HH:mm:ss"));
        }

        addButton = findViewById(R.id.addButton);
        addButton.setBackgroundTintList(ColorStateList.valueOf(COLOR_ID));

        //Configuting Toolbar
        this.configureToolbar();

        db = new DBContacts(this);
        List<ListContacts> listContacts = db.getAllContacts();

        list = findViewById(R.id.listView1);
        list.setAdapter(new ListAdapter(this, listContacts));


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DetailContacts.class);

                intent.putExtras(dataBundle);
                startActivity(intent);

            }
        });

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", (int)id);

                Intent intent = new Intent(getApplicationContext(),DetailContacts.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }

        });
    }


    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(COLOR_ID));
        addButton.setBackgroundTintList(ColorStateList.valueOf(COLOR_ID));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {

            case R.id.blue:
                COLOR_ID = getResources().getColor(R.color.colorPrimary);
                break;
            case R.id.red:
                COLOR_ID = getResources().getColor(R.color.colorAccent);
                break;
            case R.id.green:
                COLOR_ID = getResources().getColor(R.color.colorPrimaryDark);
                break;
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(COLOR_ID));
        addButton.setBackgroundTintList(ColorStateList.valueOf(COLOR_ID));
        return super.onOptionsItemSelected(item);
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date()); // Find todays date
        savedInstanceState.putString("param", currentDateTime);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDelegate().onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, dateTime, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dateTime = getCurrentTimeStamp();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}
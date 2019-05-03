package com.example.ft_hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;



import java.util.List;

public class MainActivity extends Activity {
    private ListView obj;
    private DBContacts mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBContacts(this);
        List<ListContacts> listContacts = mydb.getAllContacts();

        obj = findViewById(R.id.listView1);
        obj.setAdapter(new ListAdapter(this, listContacts));


        ImageButton imgbut = findViewById(R.id.addButton);

        imgbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DetailContacts.class);

                intent.putExtras(dataBundle);
                startActivity(intent);

            }
        });

        obj.setOnItemClickListener(new OnItemClickListener() {

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DetailContacts.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}
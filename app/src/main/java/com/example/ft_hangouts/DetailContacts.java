package com.example.ft_hangouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailContacts extends Activity {

    private DBContacts mydb ;

    TextView name ;
    TextView phone;
    TextView email;
    TextView street;
    TextView place;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        name   =  findViewById(R.id.editTextName);
        phone  =  findViewById(R.id.editTextPhone);
        email  =  findViewById(R.id.editTextStreet);
        street =  findViewById(R.id.editTextEmail);
        place  =  findViewById(R.id.editTextCity);

        mydb = new DBContacts(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBContacts.CONTACTS_COLUMN_NAME));
                String phon = rs.getString(rs.getColumnIndex(DBContacts.CONTACTS_COLUMN_PHONE));
                String emai = rs.getString(rs.getColumnIndex(DBContacts.CONTACTS_COLUMN_EMAIL));
                String stree = rs.getString(rs.getColumnIndex(DBContacts.CONTACTS_COLUMN_STREET));
                String plac = rs.getString(rs.getColumnIndex(DBContacts.CONTACTS_COLUMN_CITY));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                name.setText(nam);
                name.setFocusable(false);
                name.setClickable(false);

                phone.setText(phon);
                phone.setFocusable(false);
                phone.setClickable(false);

                email.setText(emai);
                email.setFocusable(false);
                email.setClickable(false);

                street.setText(stree);
                street.setFocusable(false);
                street.setClickable(false);

                place.setText(plac);
                place.setFocusable(false);
                place.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.detail_contact, menu);
            } else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                phone.setEnabled(true);
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);

                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);

                street.setEnabled(true);
                street.setFocusableInTouchMode(true);
                street.setClickable(true);

                place.setEnabled(true);
                place.setFocusableInTouchMode(true);
                place.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Log.i("DATABASE", "Deleted Successufally");
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateContact(id_To_Update,name.getText().toString(),
                        phone.getText().toString(), email.getText().toString(),
                        street.getText().toString(), place.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                    Log.i("DATABASE", "not Updated");
                }
            } else{
                if(mydb.insertContact(name.getText().toString(), phone.getText().toString(),
                        email.getText().toString(), street.getText().toString(),
                        place.getText().toString())){
                    Toast.makeText(getApplicationContext(), "insert done",
                            Toast.LENGTH_SHORT).show();
                    Log.i("DATABASE", "Insert done");
                } else{
                    Toast.makeText(getApplicationContext(), "insert not done",
                            Toast.LENGTH_SHORT).show();
                    Log.i("DATABASE", "Insert not done");
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }


    //Button b = findViewById(R.id.button2);

    public void sms(View view) {
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", 1);

        Intent intent = new Intent(getApplicationContext(),SendSMS.class);

        intent.putExtras(dataBundle);
        startActivity(intent);
    }



}
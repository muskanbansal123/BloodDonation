package com.example.keshav.projecttcs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.jar.Attributes;

import static com.example.keshav.projecttcs.DatabaseHelper.COLUMN_EMAIL;

/**
 * Created by keshav on 25-06-2017.
 */

public class Profile extends MainActivity {


    static TextView name,age,height,weight,ldate,phone;
    Button btnviewAll;

    Button display;

    DatabaseHelper helper = new DatabaseHelper(this);

    Profile profile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        name = (TextView)findViewById(R.id.prname);
        age=(TextView)findViewById(R.id.prage);
        height=(TextView)findViewById(R.id.prheight);
        weight=(TextView)findViewById(R.id.prweight);
        ldate=(TextView)findViewById(R.id.prdate);
        phone=(TextView)findViewById(R.id.prphone);

        btnviewAll = (Button)findViewById(R.id.btn_change);

        viewAll();

    }

    public void onButtonClick(View view)
    {
        if(view.getId() == R.id.btn_change)
        {
            Intent intent = new Intent(this, Update.class);
            startActivity(intent);

        }
    }

        public void viewAll()
        {

           Cursor res = helper.get_info();
           //String a;

            /* if (res.moveToFirst())
            {
            do {
                a = res.getString(res.getColumnIndex(COLUMN_EMAIL));
                if (a.equals(loginn.str))
                {
                    //Log.d("Error: ","Email matched");
                    //b = cursor.getString(cursor.getColumnIndex(COLUMN_PASS));
                    //break;
                }

            }while (res.moveToNext());
            }*/

            res.moveToFirst();
            //res.moveToPosition(DatabaseHelper.pos);
                if (res.getCount() == 0) {
                    Toast.makeText(Profile.this, "No data Found", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                       while (res.moveToNext()) {
                            age.setText(res.getString(4));
                            height.setText(res.getString(1));
                            weight.setText(res.getString(2));
                            ldate.setText(res.getString(3));
                            phone.setText(res.getString(5));

                        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        super.onBackPressed();
    }

}

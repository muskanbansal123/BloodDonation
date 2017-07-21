package com.example.keshav.projecttcs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by keshav on 19-06-2017.
 */

public class Update extends MainActivity  {

    DatabaseHelper helper = new DatabaseHelper(this);
    Button update;

    //private static final String TAG = "Update";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String date;


    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        update = (Button) findViewById(R.id.btn_update);

        mDisplayDate = (EditText)findViewById(R.id.editText_date);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Update.this, android.R.style.Theme_Holo_Dialog_MinWidth,mDateSetListener,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
            });

            mDateSetListener = new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day){

                    month = month+1;
                    date = day + "/" + month + "/" + year;
                    mDisplayDate.setText(date);

                }
            };


        DatabaseHelper db = new DatabaseHelper(this);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    EditText etAge = (EditText) findViewById(R.id.editText_age);
                    String strA = etAge.getText().toString();

                    EditText eth = (EditText) findViewById(R.id.editText_height);
                    String strH = eth.getText().toString();

                    EditText etw = (EditText) findViewById(R.id.editText_weight);
                    String strW = etw.getText().toString();

                    EditText etd = (EditText) findViewById(R.id.editText_date);
                    String strD = date;

                    EditText etp = (EditText) findViewById(R.id.editText_phone);
                    String strP = etp.getText().toString();

                    if (TextUtils.isEmpty(strA)) {
                        etAge.setError("Please specify your age");
                        return;
                    }

                    if (TextUtils.isEmpty(strH)) {
                        eth.setError("Please specify your height");
                        return;
                    }

                    if (TextUtils.isEmpty(strW)) {
                        etw.setError("Please specify your weight");
                        return;
                    }

                   if (TextUtils.isEmpty(strD)) {
                       etd.setError("Please specify the date because you can not donate before 3 months of last donation");
                       return;
                    }
                    else {

                        UpdateDB c1 = new UpdateDB();
                        c1.setHeight(strH);
                        c1.setWeight(strW);
                        c1.setLdate(strD);
                        c1.setUage(strA);
                        c1.setUphone(strP);
                       // DatabaseHelper helper12 = new DatabaseHelper(this);
                        //helper.Createdb(helper.db);
                        helper.insertContact1(c1);

                        Toast tem = Toast.makeText(Update.this, "Your profile has been updated!", Toast.LENGTH_SHORT);
                        tem.show();

                       // Profile.viewAll();

                        Intent intent = new Intent(Update.this, MainActivity.class);
                        //intent.putExtra("1", st);
                        startActivity(intent);
                    }


        }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Update.this, MainActivity.class));
        super.onBackPressed();
    }
}

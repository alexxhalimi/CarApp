package com.example.carapp.myapplication.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carapp.R;
import com.example.carapp.myapplication.db.DbHelper;
import com.example.carapp.myapplication.model.Car;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CarDetails extends AppCompatActivity {
    TextView brand,model,year,price;
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_details1);
        brand = findViewById(R.id.detailsbrand);
        model = findViewById(R.id.detailsLastName);
        year = findViewById(R.id.detailsMobileNumber);
        price = findViewById(R.id.detailsEmailId);
        imageView = findViewById(R.id.imageViewDetails);


        Car car = getData();
        brand.setText(car.getBrand());
        model.setText(car.getModel());
        year.setText(car.getYear());
        price.setText(car.getPrice());
        if(car.getImage() == null){
            imageView.setImageResource(R.drawable.contacts_icon);
        }
        else {
            byte [] arr = Base64.decode(car.getImage(),Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(arr,0,arr.length);
            imageView.setImageBitmap(bitmap);
        }



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(car.getBrand());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.saveFlaotingButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
                finish();
            }
        });
    }

    public Car getData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("contact", 0);
        DbHelper helper = new DbHelper(this, DbHelper.DB_NAME, null, DbHelper.DB_VERSION);
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Car> cars = new ArrayList<>();

        String query = "SELECT * FROM " + DbHelper.TABLE_NAME + " WHERE ID=" + id;
        Cursor cursor = db.rawQuery(query, null);
        Car car = null;
        if (cursor.moveToFirst()) {
            do {
                car = new Car();
                car.setId(Integer.parseInt(cursor.getString(0)));
                car.setBrand(cursor.getString(1));
                car.setModel(cursor.getString(2));
                car.setYear(cursor.getString(3));
                car.setPrice(cursor.getString(4));
                car.setImage(cursor.getString(5));

                cars.add(car);

            } while (cursor.moveToNext());
        }

        return car;
    }


    public void updateData(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("contact",0);
        Intent intent1 = new Intent(this, UpdateCar.class);
        intent1.putExtra("updateContact",id);
        startActivity(intent1);
    }

    public void deleteData(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("contact",0);
        DbHelper helper = new DbHelper(this,DbHelper.DB_NAME,null,DbHelper.DB_VERSION);
        SQLiteDatabase db = helper.getWritableDatabase();
        int result = db.delete(DbHelper.TABLE_NAME,"ID="+id,null);
        Log.v("TAG","Item deleted "+result+" "+id);
        Toast.makeText(this,"Item deleted "+id,Toast.LENGTH_SHORT).show();
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            deleteData();
            finish();
            return true;
        }
        finish();
        return super.onOptionsItemSelected(item);
    }


}

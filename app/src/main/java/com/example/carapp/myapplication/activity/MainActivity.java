package com.example.carapp.myapplication.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carapp.R;
import com.example.carapp.myapplication.adapter.CarAdapterRecyclerView;
import com.example.carapp.myapplication.common.ContactClickListner;
import com.example.carapp.myapplication.db.DbHelper;
import com.example.carapp.myapplication.model.Car;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactClickListner {

    //ListView listView;
    RecyclerView               recyclerView;
    ArrayList<Car> cars = new ArrayList<>();
    //ContactAdapter adapter;
    CarAdapterRecyclerView adapter;
    CoordinatorLayout coordinatorLayout;
    //ContactClickListner listner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main_recyclerview);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.saveFlaotingButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
        Car car = new Car();
        car.setId( 1 );
        car.setBrand("Toyota");
        car.setModel( "2016" );
        car.setYear( "2016" );
        car.setPrice("200000" );

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        //contacts = getData();
        super.onResume();
        cars = getData();

        /*listView = findViewById(R.id.listView);
        adapter = new ContactAdapter(this,R.layout.contact_list_items,contacts);
        listView.setAdapter(adapter);*/

        recyclerView = findViewById(R.id.recyclerView);
        //adapter = new ContactAdapterRecyclerView(this,contacts, listner);
        adapter = new CarAdapterRecyclerView(this, cars,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager (this));


        adapter.notifyDataSetChanged();

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contacts.get(position);
                Intent intent = new Intent(MainActivity.this,ContactDetails.class);
                intent.putExtra("contact",contact);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"item clicked"+position,Toast.LENGTH_SHORT).show();

            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(this,AddActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Car> getData(){
        this.cars.clear();
        DbHelper helper = new DbHelper(this,DbHelper.DB_NAME,null,DbHelper.DB_VERSION);
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Car> cars = new ArrayList<>();

        String query = "SELECT * FROM "+DbHelper.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Car car = new Car();
    

                car.setId( Integer.parseInt(cursor.getString(0)) );
                car.setBrand( cursor.getString(1) );
                car.setModel( cursor.getString(2) );
                car.setYear( cursor.getString(3) );
                car.setPrice( cursor.getString(4) );
                car.setImage( cursor.getString(5) );

                cars.add(car);

            }while (cursor.moveToNext());
        }
        return cars;
    }

    @Override
    public void onContactClick(Car car) {
        Intent intent = new Intent(MainActivity.this, CarDetails.class);
        intent.putExtra("contact", car.getId());
        startActivity(intent);
        Toast.makeText(MainActivity.this,"item clicked",Toast.LENGTH_SHORT).show();
    }



}

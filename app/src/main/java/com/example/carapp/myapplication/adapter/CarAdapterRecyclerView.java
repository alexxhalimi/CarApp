package com.example.carapp.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carapp.R;
import com.example.carapp.myapplication.activity.CarDetails;
import com.example.carapp.myapplication.common.ContactClickListner;
import com.example.carapp.myapplication.model.Car;

import java.util.ArrayList;

 public class CarAdapterRecyclerView extends RecyclerView.Adapter<CarAdapterRecyclerView.ViewHolder>  {

    private final Context             context;
    private final ArrayList<Car> cars;
    private final ContactClickListner listner;
    private CoordinatorLayout coordinatorLayout;

    public CarAdapterRecyclerView(Context context, ArrayList<Car> cars, ContactClickListner listner ) {
        this.context = context;
        this.cars = cars;
        this.listner = listner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout layout= (RelativeLayout) inflater.inflate(R.layout.contact_list_items1,null);
        return new ViewHolder(layout);*/
        View view =LayoutInflater.from(parent.getContext()).inflate( R.layout.contact_list_items1,parent,false);
        ViewHolder holder = new ViewHolder(view,context, cars);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Car car = cars.get(position);
        holder.fName.setText(car.getBrand());
       holder.number.setText(car.getModel());
        if(car.getImage() == null){
            holder.imageView.setImageResource(R.drawable.contacts_icon);
        }
        else{
            byte [] arr = Base64.decode(car.getImage(),Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(arr,0,arr.length);
            holder.imageView.setImageBitmap(bitmap);
        }

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        ImageView imageView;
        TextView fName,number;
        ArrayList<Car> cars = new ArrayList<Car>();
        Context context;
        public ViewHolder(View itemView,Context context,ArrayList<Car> cars) {
            super(itemView);
            this.cars = cars;
            this.context=context;
            itemView.setOnClickListener(this);
//itemView.setOnLongClickListener(new View.OnLongClickListener() {
//    @Override
//    public boolean onLongClick(View v) {
//        int position = getAdapterPosition();
//        cars.remove(position);
//        return false;
//    }
//});


            imageView = itemView.findViewById(R.id.imageView);
            fName = itemView.findViewById(R.id.textName);
            number = itemView.findViewById(R.id.textNumber);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Car car = this.cars.get(position);
            Intent intent= new Intent(this.context, CarDetails.class);
//            intent.putExtra("contact",contact.getId());
            intent.putExtra("contact", car.getId());
            this.context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            cars.remove(position);
        return true;
        }
    }

//    @Override
//    public void onRowSwiped(final int position) {
//        // store item list
//        final Car list = cars.get(position);
//        // remove item from the list
//        cars.remove(position);
//        notifyItemRemoved(position);
//        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Item removed from the list.", Snackbar.LENGTH_LONG);
//        snackbar.setAction("UNDO",new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // add item in list
//                cars.add(position, list);
//                notifyItemInserted(position);
//            }
//        });
//        snackbar.show();// display snackbar
//    }

}

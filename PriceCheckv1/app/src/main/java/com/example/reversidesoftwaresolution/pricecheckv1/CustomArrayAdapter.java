package com.example.reversidesoftwaresolution.pricecheckv1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by reversidesoftwaresolution on 2017/09/19.
 */

public class CustomArrayAdapter extends ArrayAdapter<Stock>{
    private List<Stock> StockList;

    public CustomArrayAdapter(@NonNull Context context, @LayoutRes int resource, List<Stock> objects) {
        super(context, resource, objects);
        this.StockList =objects;
    }
    @Override
    public int getCount(){
        return super.getCount();
    }

    @Override
    public View getView(int position ,View convertView, ViewGroup parent){

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.myrow, null);

        }

        TextView textView= (TextView) v.findViewById(R.id.myrow_Text);
        TextView tdescription=(TextView) v.findViewById(R.id.myrow_description);
        TextView tcategory=(TextView) v.findViewById(R.id.myrow_category);
        ImageView imageView =(ImageView) v.findViewById(R.id.myrow_Image);
        TextView tvprice =(TextView) v.findViewById(R.id.myrow_price);

        textView.setText(StockList.get(position).getName());
        tdescription.setText(StockList.get(position).getDescription());
        tcategory.setText(StockList.get(position).getCategory());
        tvprice.setText("R " + StockList.get(position).getPrice());




        byte[] b = StockList.get(position).getImage();
        System.out.println(b + " ");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        imageView.setImageBitmap(bmp);
        return v;

    }


}

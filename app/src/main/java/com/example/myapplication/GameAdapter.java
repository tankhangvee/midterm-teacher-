package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameAdapter extends ArrayAdapter {

    private ArrayList<GameModel> data;
    private Context mContext;

    public GameAdapter(Context context, ArrayList<GameModel> data){
        super(context, R.layout.singlegamelistitem);
        this.data = data;
        this.mContext = context;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.singlegamelistitem,null);
        ViewHolder holder = new ViewHolder();;
        if(convertView == null){
            holder.tvTitle = v.findViewById(R.id.tv_title);
            holder.tvRating = v.findViewById(R.id.tv_rating);
            holder.tvPrice = v.findViewById(R.id.tv_price);

            v.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
            //v = convertView;
        }
        GameModel model = (GameModel) getItem(position);
        holder.tvTitle.setText(model.getTitle());
        holder.tvRating.setText(model.getRating());
        holder.tvPrice.setText(model.getPrice());

        return v;
    }

    class ViewHolder{
        TextView tvTitle,tvRating,tvPrice;
    }
}

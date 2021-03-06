package io.elpoisterio.smartlnmiit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.elpoisterio.smartlnmiit.utilities.AndroidVersion;
import io.elpoisterio.smartlnmiit.R;

/**
 * Created by lenovo on 28-02-2017.
 */

public class HomeRecylerAdapter extends RecyclerView.Adapter<HomeRecylerAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android;
    private Context context;

    public HomeRecylerAdapter(Context context, ArrayList<AndroidVersion> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public HomeRecylerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeRecylerAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_android.setText(android.get(i).getAndroid_version_name());
        Picasso.with(context).load(android.get(i).getAndroid_image_url()).resize(240, 240).into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_android;
        private ImageView img_android;
        ViewHolder(View view) {
            super(view);

            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView) view.findViewById(R.id.img_android);
        }
    }

}
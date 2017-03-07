package id.ac.its.anif.pikti.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import id.ac.its.anif.pikti.resepmasakanku.R;
import id.ac.its.anif.pikti.sqlite.Resep;

/**
 * Created by Abdul Munif on 3/5/2017.
 */

public class ResepAdapter extends RecyclerView.Adapter<ResepAdapter.ResepViewHolder> {

    private List<Resep> resepList;
    private final Context mContext;

    public ResepAdapter(List<Resep> resepList, Context mContext) {
        this.resepList = resepList;
        this.mContext = mContext;
    }

    @Override
    public ResepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resep_card, parent, false);
        return new ResepViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResepViewHolder holder, int position) {
        Resep resep = resepList.get(position);
        holder.tvResepNama.setText(resep.getResepNama());

        Resources res = mContext.getResources();
        String mDrawableName = resep.getResepGambar();
        int resId = res.getIdentifier(mDrawableName, "drawable", mContext.getPackageName());
        holder.ivResepCover.setImageResource(resId);


    }

    @Override
    public int getItemCount() {
        return resepList.size();
    }

    public void swap(List<Resep> resepList) {
        this.resepList.clear();
        this.resepList.addAll(resepList);
        notifyDataSetChanged();
    }

    public class ResepViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivResepCover;
        public TextView tvResepNama;

        public ResepViewHolder(View itemView) {
            super(itemView);
            ivResepCover = (ImageView)itemView.findViewById(R.id.ivResepCover);
            tvResepNama = (TextView)itemView.findViewById(R.id.tvResepNama);
        }
    }
}

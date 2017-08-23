package com.nonsobiose.lagdev.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nonsobiose.lagdev.R;
import com.nonsobiose.lagdev.model.Developer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by slimb on 12/07/2017.
 */

public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.ViewHolder> {

    private List<Developer> developers;
    private Context context;


    public DeveloperAdapter(List<Developer> developers, Context context) {
        this.developers = developers;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View developerView = layoutInflater.inflate(R.layout.developers_list_item, parent, false);
        return new ViewHolder(developerView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Developer developer = developers.get(position);

        ImageView imageView = holder.developersImage;
        Picasso.with(context)
                .load(developer.getAvatarUrl())
                .into(imageView);

        TextView textView = holder.developersUsername;
        textView.setText(developer.getLogin());
    }

    @Override
    public int getItemCount() {
        return developers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView developersImage;
        public TextView developersUsername;

        public ViewHolder(View itemView) {
            super(itemView);
            this.developersImage = (ImageView) itemView.findViewById(R.id.developer_image);
            this.developersUsername = (TextView) itemView.findViewById(R.id.developer_username);
        }
    }
}

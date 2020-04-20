package ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marathinokari.R;
import com.example.marathinokari.nokari;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NokariRecyclerAdapter extends RecyclerView.Adapter<NokariRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<nokari> nokariList;

    public NokariRecyclerAdapter(Context context, List<nokari> nokariList) {
        this.context = context;
        this.nokariList = nokariList;
    }

    @NonNull
    @Override
    public NokariRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.nokari_row, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull NokariRecyclerAdapter.ViewHolder viewHolder, int position) {

        viewHolder.title.setText(nokariList.get(position).getTitle());
        viewHolder.description.setText(nokariList.get(position).getDescription());



        Picasso.get()
                .load(nokariList.get(position).getAdvertise()).into(viewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return nokariList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView
                title,
                description;
        public ImageView imageView;
        String userId;
        String userName;
        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.link);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}

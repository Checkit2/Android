package ir.Peaky.checkit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.Peaky.checkit.R;
import ir.Peaky.checkit.model.CheckModel;
import ir.Peaky.checkit.utils.RegularTextView;

public class ChecksAdapter extends RecyclerView.Adapter<ChecksAdapter.ViewHolder> {

    private Context context;
    private List<CheckModel> list;

    public ChecksAdapter(Context context, List<CheckModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckModel checkModel=list.get(position);
        holder.txtTitle.setText(checkModel.getCheck_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RegularTextView txtTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txt_title);
        }
    }
}

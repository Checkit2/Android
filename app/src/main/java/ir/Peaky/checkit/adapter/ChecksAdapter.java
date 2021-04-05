package ir.Peaky.checkit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.Peaky.checkit.R;
import ir.Peaky.checkit.activity.AnalysisActivity;
import ir.Peaky.checkit.model.CheckModel;
import ir.Peaky.checkit.utils.RegularTextView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AnalysisActivity.class);
                intent.putExtra("check_result",checkModel.getCheck_result().toString());
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("flag",true);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RegularTextView txtTitle;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txt_title);
            relativeLayout=itemView.findViewById(R.id.rel_main);
        }
    }
}

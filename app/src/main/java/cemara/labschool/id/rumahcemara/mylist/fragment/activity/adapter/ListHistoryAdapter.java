package cemara.labschool.id.rumahcemara.mylist.fragment.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.model.HistoryListModel;
import cemara.labschool.id.rumahcemara.mylist.fragment.activity.DetailHistoryAppointment;

public class ListHistoryAdapter extends RecyclerView.Adapter<ListHistoryAdapter.ViewHolder> {
    private List<HistoryListModel> articleModels;
    private Context context;

    public ListHistoryAdapter(List<HistoryListModel> articleModels, Context context) {
        this.articleModels = articleModels;
        this.context = context;
    }

    @Override
    public ListHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_appointment_history, parent, false);

        return new ListHistoryAdapter.ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ListHistoryAdapter.ViewHolder holder, int position) {
        final HistoryListModel articleModel = articleModels.get(position);
        final String id = articleModel.getId();
        final String name = articleModel.getProvider_worker().getProfile().getFullname();
        final String srcImg = articleModel.getProvider_worker().getProfile().getPicture();
        final String phone = articleModel.getProvider_worker().getProfile().getPhoneNumber();
        final String serviceName = articleModel.getService_type().getName();
        final String date = articleModel.getUpdated_at();
        final String providerId = articleModel.getProvider_id();
        final String mStatus = articleModel.getStatus();
        String status = "";

        switch (mStatus){
            case "2":
                status = "Rejected";
                break;
            case "3":
                status = "Cancelled";
                break;
            case "4":
                status = "Completed";
                break;
        }

        holder.textViewName.setText(name);
//        if (!status.equals("Rejected") && !status.equals("Cancelled")){
//            holder.textViewStatus.setText(status);
//            holder.textViewStatus.setTextColor(R.color.red_text);
//        } else {
//            holder.textViewStatus.setText(status);
//            holder.textViewStatus.setTextColor(R.color.green_text);
//        }
        holder.textViewStatus.setText(status);
        Glide.with(context).load(srcImg).apply(RequestOptions.circleCropTransform()).into(holder.imageViewNearest);

        String finalStatus = status;
        holder.linearLayout.setOnClickListener(view -> {

            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putString("name", name);
            bundle.putString("srcImg", srcImg);
            bundle.putString("phone", phone);
            bundle.putString("serviceName", serviceName);
            bundle.putString("date", date);
            bundle.putString("status", finalStatus);
            bundle.putString("providerId", providerId);
            Intent intent = new Intent(view.getContext(), DetailHistoryAppointment.class);
            intent.putExtra("myData", bundle);
            view.getContext().startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return articleModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageViewNearest;
        public TextView textViewStatus;
        public FrameLayout linearLayout;

        public ViewHolder(View v) {
            super(v);

            textViewName = v.findViewById(R.id.tvFullname);
            imageViewNearest = v.findViewById(R.id.imgProfile);
            textViewStatus = v.findViewById(R.id.tvStatus);
            linearLayout = v.findViewById(R.id.layout_article);
        }
    }

}
package cemara.labschool.id.rumahcemara.home.service.behavioral.FindServiceProvider.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import cemara.labschool.id.rumahcemara.home.service.behavioral.FindServiceProvider.AppointmentFormActivity;
import cemara.labschool.id.rumahcemara.model.NearestProviderModel;

public class AdapterListProviderNearMe extends RecyclerView.Adapter<AdapterListProviderNearMe.ViewHolder> {
    private List<NearestProviderModel> articleModels;
    private Context context;

    public AdapterListProviderNearMe(List<NearestProviderModel> articleModels, Context context){
        this.articleModels = articleModels;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterListProviderNearMe.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_nearest_service, parent, false);

        return new AdapterListProviderNearMe.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListProviderNearMe.ViewHolder holder, int position){
        final NearestProviderModel articleModel = articleModels.get(position);
        final String id = articleModel.getId();
        final String srcImage = articleModel.getGroup().getGroupProfile().getPicture();
        final String name = articleModel.getName();
        final String address = articleModel.getAddress();
        final String phoneNumber = articleModel.getPhoneNumber();
        final String group_id = articleModel.getGroup_id();
        final String worker_id = articleModel.getGroup().getId();
        final String mDistance = articleModel.getDistance();
        String distance;
        if (mDistance.length()>1){
            distance = mDistance.substring(0,4) + " km";
        } else {
            distance = mDistance + " km";
        }

        holder.textViewName.setText(name);
        holder.textViewRange.setText(distance);
        Glide.with(context).load(srcImage).apply(RequestOptions.circleCropTransform()).into(holder.imageViewNearest);

        String finalDistance = distance;
        holder.linearLayout.setOnClickListener(view -> {
            @SuppressLint("InflateParams") View viewSheet = LayoutInflater.from(view.getContext()).inflate(R.layout.find_service_provider_bottom_sheet_dialog, null);
            Log.d( "onClick: ",String.valueOf(viewSheet));
            final BottomSheetDialog dialog = new BottomSheetDialog(view.getContext());
            dialog.setContentView(viewSheet);
            dialog.show();
            ImageView close = dialog.findViewById(R.id.sheet_btn_close);
            ImageView imgProfile = dialog.findViewById(R.id.nearest_img);
            TextView tvname = dialog.findViewById(R.id.nearest_name);
            TextView tvaddress = dialog.findViewById(R.id.nearest_address);
            TextView tvRange = dialog.findViewById(R.id.nearest_range);
            assert tvname != null;
            tvname.setText(name);
            assert tvaddress != null;
            tvaddress.setText(address);
            assert imgProfile != null;
            Glide.with(context).load(srcImage).apply(RequestOptions.circleCropTransform()).into(imgProfile);
            assert tvRange != null;
            tvRange.setText(finalDistance);

            if (close != null) {
                close.setOnClickListener(view12 -> dialog.dismiss());
            }
            Button btnAppointment = dialog.findViewById(R.id.btn_appointment);
            if (btnAppointment != null) {
                btnAppointment.setOnClickListener(view1 -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    bundle.putString("imgUrl", srcImage);
                    bundle.putString("fullname", name);
                    bundle.putString("address", address);
                    bundle.putString("phone", phoneNumber);
                    bundle.putString("group_id", group_id);
                    bundle.putString("worker_id", worker_id);
                    bundle.putString("distance", finalDistance);
                    Intent intent = new Intent(view1.getContext(), AppointmentFormActivity.class);
                    intent.putExtra("myData", bundle);
                    view1.getContext().startActivity(intent);
                });
            }
        });
    }

    @Override
    public int getItemCount(){ return articleModels.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageViewNearest;
        TextView textViewRange;
        FrameLayout linearLayout;

        public ViewHolder(View v){
            super(v);

            textViewName = v.findViewById(R.id.nearest_name);
            imageViewNearest = v.findViewById(R.id.nearest_img);
            textViewRange = v.findViewById(R.id.nearest_range);
            linearLayout = v.findViewById(R.id.layout_article);
        }
    }

}
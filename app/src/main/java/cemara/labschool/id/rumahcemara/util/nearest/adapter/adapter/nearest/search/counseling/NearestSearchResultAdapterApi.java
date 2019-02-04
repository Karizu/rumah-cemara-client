package cemara.labschool.id.rumahcemara.util.nearest.adapter.adapter.nearest.search.counseling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.home.service.structural.FindOutreachWorker.AppointmentFormActivity;
import cemara.labschool.id.rumahcemara.model.NearestOutreachModel;

public class NearestSearchResultAdapterApi extends RecyclerView.Adapter<NearestSearchResultAdapterApi.NearestViewHolder>{
    private List<NearestOutreachModel> nearestsList;
    private Context mContext;
    private Unbinder unbinder;
    BottomSheetBehavior sheetBehavior;

    @NonNull
    @Override
    public NearestSearchResultAdapterApi.NearestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_result_search, viewGroup, false);
        return new NearestSearchResultAdapterApi.NearestViewHolder(itemView);
    }

    public class NearestViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.result_img)
        ImageView imgNearest;
        @BindView(R.id.result_name)
        TextView nearestName;
        @BindView(R.id.result_range)
        TextView nearestRange;
        @BindView(R.id.parentCardView)
        CardView parentLayout;

        public NearestViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    public NearestSearchResultAdapterApi(List<NearestOutreachModel> nearestList, Context mContext) {
        this.mContext = mContext;
        this.nearestsList = nearestList;
        this.unbinder = unbinder;
    }

    @Override
    public void onBindViewHolder(@NonNull NearestSearchResultAdapterApi.NearestViewHolder nearestViewHolder, final int position) {
        final NearestOutreachModel articleModel = nearestsList.get(position);
        final String id = articleModel.getId();
        final String userId = articleModel.getUser_id();
        final String srcImage = articleModel.getUser().getProfile().getPicture();
        final String name = articleModel.getUser().getProfile().getFullname();
        final String description = articleModel.getDescription();
        final String address = articleModel.getUser().getProfile().getAddress();
        final String city = articleModel.getUser().getProfile().getCity();
        final String phoneNumber = articleModel.getUser().getProfile().getPhoneNumber();
        final String group_id = articleModel.getUser().getGroupId();
        final String worker_id = articleModel.getUser().getId();
        final String mDistance = articleModel.getDistance();
        final String distance = mDistance.substring(0,4) + " km";

        Log.d("NearestList: ", String.valueOf(nearestsList.size()));
        nearestViewHolder.nearestName.setText(articleModel.getName());
        nearestViewHolder.nearestRange.setText(distance);
        // loading ImageNews using Glide library
        Glide.with(mContext)
                .load(articleModel.getSrcImage())
                .apply(RequestOptions.circleCropTransform())
                .into(nearestViewHolder.imgNearest);
        //click
        nearestViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewSheet = LayoutInflater.from(view.getContext()).inflate(R.layout.find_outreach_worker_bottom_sheet_dialog, null);
                Log.d( "onClick: ",String.valueOf(viewSheet));
                final BottomSheetDialog dialog = new BottomSheetDialog(view.getContext());
                dialog.setContentView(viewSheet);
                dialog.show();
                ImageView close = dialog.findViewById(R.id.sheet_btn_close);
                ImageView imgProfile = dialog.findViewById(R.id.nearest_img);
                TextView tvname = dialog.findViewById(R.id.nearest_name);
                TextView tvaddress = dialog.findViewById(R.id.nearest_address);
                TextView tvcity = dialog.findViewById(R.id.nearest_city);
                TextView tvphone = dialog.findViewById(R.id.nearest_phone);
                TextView tvRange = dialog.findViewById(R.id.nearest_range);
                Glide.with(mContext).load(articleModel.getSrcImage()).apply(RequestOptions.circleCropTransform()).into(imgProfile);
                tvname.setText(name);
                tvaddress.setText(address);
                tvcity.setText(city);
                tvphone.setText(phoneNumber);
                tvRange.setText(distance);

                if (close != null) {
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
                Button btnAppointment = dialog.findViewById(R.id.btn_appointment);
                if (btnAppointment != null) {
                    btnAppointment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putString("id", id);
                            bundle.putString("user_id", userId);
                            bundle.putString("imgUrl", srcImage);
                            bundle.putString("fullname", name);
                            bundle.putString("address", address);
                            bundle.putString("phone", phoneNumber);
                            bundle.putString("group_id", group_id);
                            bundle.putString("worker_id", worker_id);
                            bundle.putString("distance", distance);
                            Intent intent = new Intent(view.getContext(), AppointmentFormActivity.class);
                            intent.putExtra("myData", bundle);
                            view.getContext().startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return nearestsList.size();
    }


    public void updateData(List<NearestOutreachModel> newUser){
        nearestsList = new ArrayList<>();
        nearestsList.addAll(newUser);
        notifyDataSetChanged();
    }
}
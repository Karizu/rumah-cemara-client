package cemara.labschool.id.rumahcemara.util.nearest.adapter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.util.nearest.modal.Nearest;

public class NearestAdapter extends RecyclerView.Adapter<NearestAdapter.NearestViewHolder>{
    private List<Nearest> nearestsList;
    private Context mContext;
    private Unbinder unbinder;
    BottomSheetBehavior sheetBehavior;
    private String fromId = "";
    cemara.labschool.id.rumahcemara.home.service.biomedical.FindOutreachWorker.AppointmentFormActivity FindOutreachWorkerForm = new cemara.labschool.id.rumahcemara.home.service.biomedical.FindOutreachWorker.AppointmentFormActivity();
    cemara.labschool.id.rumahcemara.home.service.biomedical.FindServiceProvider.AppointmentFormActivity FindServiceProviderForm = new cemara.labschool.id.rumahcemara.home.service.biomedical.FindServiceProvider.AppointmentFormActivity();

    @NonNull
    @Override
    public NearestAdapter.NearestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_nearest_service, viewGroup, false);
        return new NearestViewHolder(itemView);
    }

    public class NearestViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.nearest_img)
        ImageView imgNearest;
        @BindView(R.id.nearest_name)
        TextView nearestName;
        @BindView(R.id.nearest_range)
        TextView nearestRange;
        @BindView(R.id.parentCardView)
        CardView parentLayout;

        public NearestViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    public NearestAdapter(Context mContext, List<Nearest> nearestList) {
        this.mContext = mContext;
        this.nearestsList = nearestList;
        this.unbinder = unbinder;
    }
    public NearestAdapter(Context mContext, List<Nearest> nearestList, String fromId) {
        this.mContext = mContext;
        this.nearestsList = nearestList;
        this.fromId = fromId;
        this.unbinder = unbinder;
    }

    @Override
    public void onBindViewHolder(@NonNull NearestAdapter.NearestViewHolder nearestViewHolder, final int position) {
        final Nearest nearest = nearestsList.get(position);
        Log.d("NearestList: ", String.valueOf(nearestsList.size()));
        nearestViewHolder.nearestName.setText(nearest.getNearestName());
        nearestViewHolder.nearestRange.setText(nearest.getNearestRange());
        // loading ImageNews using Glide library
        Glide.with(mContext)
                .load(nearest.getNearestSrc())
                .apply(RequestOptions.circleCropTransform())
                .into(nearestViewHolder.imgNearest);
        //click
        if(fromId.equals("findserviceprovider")){
            nearestViewHolder.parentLayout.setOnClickListener(view -> {
                Toast toast = Toast.makeText(view.getContext(), nearest.getNearestName(), Toast.LENGTH_SHORT);
                toast.show();
                View viewSheet = LayoutInflater.from(view.getContext()).inflate(R.layout.find_service_provider_bottom_sheet_dialog, null);
                Log.d( "onClick: ",String.valueOf(viewSheet));
                final BottomSheetDialog dialog = new BottomSheetDialog(view.getContext());
                dialog.setContentView(viewSheet);
                dialog.show();
                ImageView close = dialog.findViewById(R.id.sheet_btn_close);
                ImageView imgProfile = dialog.findViewById(R.id.nearest_img);
                TextView name = dialog.findViewById(R.id.nearest_name);
                TextView address = dialog.findViewById(R.id.nearest_address);
                TextView city = dialog.findViewById(R.id.nearest_city);
                TextView phone = dialog.findViewById(R.id.nearest_phone);
                if (close != null) {
                    close.setOnClickListener(view1 -> dialog.dismiss());
                }
                Button btnAppointment = dialog.findViewById(R.id.btn_appointment);
                if (btnAppointment != null) {
                    btnAppointment.setOnClickListener(view12 -> {
                        Intent intent = new Intent(view12.getContext(), FindServiceProviderForm.getClass());
                        view12.getContext().startActivity(intent);
                    });
                }
            });
        }else if(fromId.equals("findoutreachworker")){
            nearestViewHolder.parentLayout.setOnClickListener(view -> {
                Toast toast = Toast.makeText(view.getContext(), nearest.getNearestName(), Toast.LENGTH_SHORT);
                toast.show();
                View viewSheet = LayoutInflater.from(view.getContext()).inflate(R.layout.find_outreach_worker_bottom_sheet_dialog, null);
                Log.d( "onClick: ",String.valueOf(viewSheet));
                final BottomSheetDialog dialog = new BottomSheetDialog(view.getContext());
                dialog.setContentView(viewSheet);
                dialog.show();
                ImageView close = dialog.findViewById(R.id.sheet_btn_close);
                if (close != null) {
                    close.setOnClickListener(view1 -> dialog.dismiss());
                }
                Button btnAppointment = dialog.findViewById(R.id.btn_appointment);
                if (btnAppointment != null) {
                    btnAppointment.setOnClickListener(view12 -> {
                        Intent intent = new Intent(view12.getContext(), FindOutreachWorkerForm.getClass());
                        view12.getContext().startActivity(intent);
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return nearestsList.size();
    }


}

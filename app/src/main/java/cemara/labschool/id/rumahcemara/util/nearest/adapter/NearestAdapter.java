package cemara.labschool.id.rumahcemara.util.nearest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindServiceProvider.AppointmentFormActivity;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindServiceProvider.FindServiceProviderActivity;
import cemara.labschool.id.rumahcemara.util.bottomSheet.BottomSheetFragment;
import cemara.labschool.id.rumahcemara.util.nearest.modal.Nearest;

public class NearestAdapter extends RecyclerView.Adapter<NearestAdapter.NearestViewHolder>{
    private List<Nearest> nearestsList;
    private Context mContext;
    private Unbinder unbinder;
    BottomSheetBehavior sheetBehavior;

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
        nearestViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(view.getContext(), nearest.getNearestName(), Toast.LENGTH_SHORT);
                toast.show();
                View viewSheet = LayoutInflater.from(view.getContext()).inflate(R.layout.bottom_sheet_dialog_fragment, null);
                Log.d( "onClick: ",String.valueOf(viewSheet));
                final BottomSheetDialog dialog = new BottomSheetDialog(view.getContext());
                dialog.setContentView(viewSheet);
                dialog.show();
                ImageView close = dialog.findViewById(R.id.sheet_btn_close);
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
                            Intent intent = new Intent(view.getContext(), AppointmentFormActivity.class);
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


}

package cemara.labschool.id.rumahcemara.mylist.fragment.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.opengl.Visibility;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.model.response.GeneralDataResponse;
import cemara.labschool.id.rumahcemara.mylist.fragment.activity.ChatAppointmentActivity;

public class MyListAppointmentAdapter extends RecyclerView.Adapter<MyListAppointmentAdapter.ViewHolder> {
    private List<GeneralDataResponse> myAppointmentList;
    private Context context;
    private Dialog dialog;

    public MyListAppointmentAdapter(List<GeneralDataResponse> myAppointments, Context context){
        this.myAppointmentList = myAppointments;
        this.context = context;
    }

    @Override
    public MyListAppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_my_appointment, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyListAppointmentAdapter.ViewHolder holder, int position){
        final GeneralDataResponse myAppointmentSelected = myAppointmentList.get(position);

        switch (myAppointmentSelected.getType_provider()){
            case "worker":
                holder.workerName.setText(myAppointmentSelected.getProvider_worker().getProfile().getFullname());
                break;

            case "provider":
                holder.workerName.setText(myAppointmentSelected.getGroup().getName());
                break;
        }

        switch (myAppointmentSelected.getStatus()){
            case 0:
                holder.statusAppointment.setText("On Progress");
                holder.statusAppointment.setTextColor(Color.parseColor("#8f8f8f"));
                holder.chatContainer.setVisibility(View.INVISIBLE);
                break;

            case 1:
                holder.statusAppointment.setText("Accepted");
                break;
        }

        switch (myAppointmentSelected.getService_type().getName()){
            case "Biomedical":
                holder.serviceImageView.setImageResource(R.drawable.icon_biomedical);
                break;

            case "Behavioral":
                holder.serviceImageView.setImageResource(R.drawable.icon_behavioral);
                break;

            case "Legal Counseling":
                holder.serviceImageView.setImageResource(R.drawable.icon_structural);
                break;
        }

        holder.serviceName.setText(myAppointmentSelected.getService_type().getName());
        holder.appointmentContainer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showDialog(R.layout.dialog_appointment_detail, myAppointmentSelected, myAppointmentSelected.getType_provider());
            }
        });

        holder.chatContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatAppointmentActivity.class);
                intent.putExtra("appointment_id", myAppointmentSelected.getId());
                intent.putExtra("worker_id", myAppointmentSelected.getWorker_id());
                intent.putExtra("worker_name", myAppointmentSelected.getProvider_worker().getProfile().getFullname());
                intent.putExtra("worker_image", myAppointmentSelected.getProvider_worker().getProfile().getPicture());
                context.startActivity(intent);
            }
        });
    }

    private void showDialog(int layout, GeneralDataResponse myAppointment, String providerType) {
        dialog = new Dialog(Objects.requireNonNull(context));
        //SET TITLE
        dialog.setTitle("Detail");

        //set content
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        ImageView profilePic = dialog.findViewById(R.id.profilePictureImageView);
        TextView workerName = dialog.findViewById(R.id.worker_name);
        TextView workerAddress = dialog.findViewById(R.id.worker_address);
        TextView workerPhone = dialog.findViewById(R.id.worker_phone);
        EditText dateStart = dialog.findViewById(R.id.appointment_date_start);
        EditText dateEnd = dialog.findViewById(R.id.appointment_date_end);
        EditText desc = dialog.findViewById(R.id.description_appointment);
        AutoCompleteTextView location = dialog.findViewById(R.id.appointment_location);
        TextView tvLocation = dialog.findViewById(R.id.tvLocation);
        LinearLayout layLocation = dialog.findViewById(R.id.layLocation);

        switch (providerType){
            case "worker":
                Glide.with(context)
                        .load(myAppointment.getProvider_worker().getProfile().getPicture())
                        .apply(RequestOptions.circleCropTransform())
                        .into(profilePic);

                workerName.setText(myAppointment.getProvider_worker().getProfile().getFullname());
                workerAddress.setText(myAppointment.getProvider_worker().getProfile().getAddress());
                workerPhone.setText(myAppointment.getProvider_worker().getProfile().getPhoneNumber());
                dateStart.setText(myAppointment.getStart_date());
                dateEnd.setText(myAppointment.getEnd_date());
                desc.setText(myAppointment.getDescription());
                location.setText(myAppointment.getLocation());

                break;

            case "provider":
                Glide.with(context)
                        .load(myAppointment.getGroup().getGroupProfile().getPicture())
                        .apply(RequestOptions.circleCropTransform())
                        .into(profilePic);

                workerName.setText(myAppointment.getGroup().getName());
                workerAddress.setText(myAppointment.getGroup().getGroupProfile().getAddress());
                workerPhone.setText(myAppointment.getGroup().getGroupProfile().getPhone_number());
                dateStart.setText(myAppointment.getStart_date());
                dateEnd.setText(myAppointment.getEnd_date());
                desc.setText(myAppointment.getDescription());
//                location.setText(myAppointment.getLocation());
                tvLocation.setVisibility(View.GONE);
                layLocation.setVisibility(View.GONE);

                break;
        }
    }

    public static String dateFormater(String dateFromJSON, String expectedFormat, String oldFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date date = null;
        String convertedDate = null;
        try {
            date = dateFormat.parse(dateFromJSON);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(expectedFormat);
            convertedDate = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

    @Override
    public int getItemCount(){ return myAppointmentList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout appointmentContainer;
        public ImageView serviceImageView;
        public TextView workerName;
        public TextView statusAppointment;
        public TextView serviceName;
        public ImageButton chatContainer;

        public ViewHolder(View v){
            super(v);

            serviceImageView = (ImageView) v.findViewById(R.id.service_imageView);
            workerName = (TextView) v.findViewById(R.id.worker_name);
            statusAppointment = (TextView) v.findViewById(R.id.appointment_status);
            serviceName = (TextView) v.findViewById(R.id.service_name);
            appointmentContainer = (LinearLayout) v.findViewById(R.id.appointment_container);
            chatContainer = (ImageButton) v.findViewById(R.id.chat_appointment);
        }
    }
}

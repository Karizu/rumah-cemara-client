package cemara.labschool.id.rumahcemara.mylist.fragment.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

public class MyReportStatusAdapter extends RecyclerView.Adapter<MyReportStatusAdapter.ViewHolder> {
    private List<GeneralDataResponse> myReportList;
    private Context context;
    private Dialog dialog;

    public MyReportStatusAdapter(List<GeneralDataResponse> myReports, Context context){
        this.myReportList = myReports;
        this.context = context;
    }

    @Override
    public MyReportStatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_report_status, parent, false);

        return new MyReportStatusAdapter.ViewHolder(v);
    }

    private void showDialog(int layout, GeneralDataResponse myAppointment) {
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

        TextView fileName = dialog.findViewById(R.id.fileName);
        EditText desc = dialog.findViewById(R.id.edit_dsc_material);
        Button btnSubmit = dialog.findViewById(R.id.btn_submit);
        LinearLayout attachmentContainer = (LinearLayout) dialog.findViewById(R.id.attached_file_doc);
        TextView titleToolbar = (TextView) dialog.findViewById(R.id.toolbar_title);

        btnSubmit.setVisibility(View.INVISIBLE);
        fileName.setText(myAppointment.getAttachment());
        desc.setText(myAppointment.getDescription());
        desc.setInputType(InputType.TYPE_NULL);
        attachmentContainer.setVisibility(View.VISIBLE);
        titleToolbar.setText(myAppointment.getService_type().getName());

    }

    @Override
    public void onBindViewHolder(MyReportStatusAdapter.ViewHolder holder, int position){
        final GeneralDataResponse myAppointmentSelected = myReportList.get(position);

        holder.name.setText(myAppointmentSelected.getUser().getProfile().getFullname());


        switch (myAppointmentSelected.getStatus()){
            case 0:
                holder.status.setText("On Progress");
                break;

            case 1:
                holder.status.setText("Done");
                break;
        }

        holder.serviceName.setText(myAppointmentSelected.getService_type().getName());

        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(myAppointmentSelected.getUser().getProfile().getPicture())
                .into(holder.profilePicture);

        holder.reportContainer.setOnClickListener(view -> showDialog(R.layout.structural_legal_aid_activity, myAppointmentSelected));
    }

    @Override
    public int getItemCount(){ return myReportList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profilePicture;
        public TextView name;
        public TextView status;
        public TextView serviceName;
        public LinearLayout reportContainer;

        public ViewHolder(View v){
            super(v);

            profilePicture = (ImageView) v.findViewById(R.id.profile_picture);
            name = (TextView) v.findViewById(R.id.name);
            status = (TextView) v.findViewById(R.id.status);
            serviceName = (TextView) v.findViewById(R.id.appointment_name);
            reportContainer = (LinearLayout) v.findViewById(R.id.report_container);
        }
    }
}

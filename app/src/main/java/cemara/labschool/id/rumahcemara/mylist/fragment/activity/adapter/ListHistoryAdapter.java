package cemara.labschool.id.rumahcemara.mylist.fragment.activity.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import cemara.labschool.id.rumahcemara.MainActivity;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.AppointmentHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.HistoryListModel;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.mylist.fragment.activity.AppointmentListHistory;
import cemara.labschool.id.rumahcemara.mylist.fragment.activity.DetailHistoryAppointment;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ListHistoryAdapter extends RecyclerView.Adapter<ListHistoryAdapter.ViewHolder> {
    private List<HistoryListModel> articleModels;
    private Context context;
    Dialog dialog;
    String ratingValue;

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
        final String name, srcImg, phone;
        if (articleModel.getWorker_id() == null){
            name = articleModel.getProvider_group().getName();
            srcImg = articleModel.getProvider_group().getGroup_profile().getPicture();
            phone = articleModel.getProvider_group().getGroup_profile().getPhone_number();
        } else {
            name = articleModel.getProvider_worker().getProfile().getFullname();
            srcImg = articleModel.getProvider_worker().getProfile().getPicture();
            phone = articleModel.getProvider_worker().getProfile().getPhoneNumber();
        }
        final String serviceName = articleModel.getService_type().getName();
        final String date = articleModel.getUpdated_at();
        final String providerId = articleModel.getProvider_id();
        final String mStatus = articleModel.getStatus();
        final String vRating;
        Float valRating = null;
        String Comment = null;
        String mRating;
        if(articleModel.getRating() == null){
            mRating = "null";
        } else {
            mRating = "notnull";
            vRating = articleModel.getRating().getRating();
            valRating = Float.valueOf(vRating);
            Comment = articleModel.getRating().getDescription();
        }
        String status = "";

        switch (mStatus) {
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

        String finalMRating = mRating;
        Float finalValRating = valRating;
        String finalComment = Comment;

        holder.linearLayout.setOnClickListener(view -> {

            Realm realm = LocalData.getRealm();
            User user = realm.where(User.class).findFirst();
            String user_id = user.getId();

            showRatingDialog(R.layout.my_list_appointment_history_dialog);
            RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
            ratingBar.setRating(4);
            ratingBar.setOnRatingBarChangeListener((ratingBar1, v, b) -> {
                switch ((int) ratingBar1.getRating()) {
                    case 1:
                        ratingValue = "1";
                        break;
                    case 2:
                        ratingValue = "2";
                        break;
                    case 3:
                        ratingValue = "3";
                        break;
                    case 4:
                        ratingValue = "4";
                        break;
                    case 5:
                        ratingValue = "5";
                        break;
                    default:
                }
            });

            ImageView imgClose = dialog.findViewById(R.id.ic_close);
            imgClose.setOnClickListener(v -> {
                ((Activity) context).finish();
            });

            TextView tvPhone = dialog.findViewById(R.id.tvPhone);
            TextView tvName = dialog.findViewById(R.id.tvName);
            TextView tvDate = dialog.findViewById(R.id.tvDate);
            TextView tvServiceName = dialog.findViewById(R.id.tvServiceName);
            EditText komentar = dialog.findViewById(R.id.txt_komentar);
            ImageView imgProfile = dialog.findViewById(R.id.img_user);
            Button btnRate = dialog.findViewById(R.id.btn_rate);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            try {
                java.util.Date dates = sdf.parse(date);
                String formated = new SimpleDateFormat("EEEE, dd MMM yyyy").format(dates);
                tvDate.setText(formated);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvName.setText(name);
            Glide.with(context).load(srcImg).apply(RequestOptions.circleCropTransform()).into(imgProfile);
            tvServiceName.setText(serviceName);
            tvPhone.setText(phone);

            if (finalMRating.equals("notnull")){
                ratingBar.setRating(finalValRating);
                komentar.setText(finalComment);
                btnRate.setText("Completed");
                ratingBar.setEnabled(false);
                komentar.setEnabled(false);
                btnRate.setEnabled(false);
            }

            btnRate.setOnClickListener(v -> {
                Loading.show(context);
                if (komentar.getText().toString().isEmpty() || ratingValue == null) {
                    Loading.hide(context);
                    if (ratingValue == null){
                        Toast.makeText(context, "Please fill in rating bar", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Please fill in comment text box", Toast.LENGTH_LONG).show();
                    }

                } else {

                    RequestBody requestBody;
                    requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("service_transaction_id", id)
                            .addFormDataPart("user_id", user_id)
                            .addFormDataPart("provider_id", providerId)
                            .addFormDataPart("type", "provider")
                            .addFormDataPart("rating", ratingValue)
                            .addFormDataPart("description", komentar.getText().toString())
                            .build();

                    AppointmentHelper.createAppointmentRating(requestBody, new RestCallback<ApiResponse>() {
                        @Override
                        public void onSuccess(Headers headers, ApiResponse body) {
                            Loading.hide(context);
                            Toast.makeText(context, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("frag", "homeFragment");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            view.getContext().startActivity(intent);
                        }

                        @Override
                        public void onFailed(ErrorResponse error) {
                            Loading.hide(context);
                            Log.d("onFailed", error.getMessage());
                        }

                        @Override
                        public void onCanceled() {
                            Loading.hide(context);
                        }
                    });
                }
            });
//            Bundle bundle = new Bundle();
//            bundle.putString("id", id);
//            bundle.putString("name", name);
//            bundle.putString("srcImg", srcImg);
//            bundle.putString("phone", phone);
//            bundle.putString("serviceName", serviceName);
//            bundle.putString("date", date);
//            bundle.putString("status", finalStatus);
//            bundle.putString("providerId", providerId);
//            Intent intent = new Intent(view.getContext(), DetailHistoryAppointment.class);
//            intent.putExtra("myData", bundle);
//            view.getContext().startActivity(intent);

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

    private void showRatingDialog(int layout) {
        dialog = new Dialog(Objects.requireNonNull(context));
        //SET TITLE
        dialog.setTitle(" ");

        //set content
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
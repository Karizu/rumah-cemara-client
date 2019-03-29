package cemara.labschool.id.rumahcemara.util.list.reminder.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cemara.labschool.id.rumahcemara.MainActivity;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.home.highlight.EventDetailActivity;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.event.model.Event;
import cemara.labschool.id.rumahcemara.util.list.reminder.model.ListReminder;
import okhttp3.Headers;

public class ListReminderAdapter extends RecyclerView.Adapter<ListReminderAdapter.EventViewHolder>{
    private List<ListReminder> eventList;
    private Context mContext;
    private Unbinder unbinder;
    private Resources res;


    public class EventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.event_image)
        ImageView imgNews;
        @BindView(R.id.event_title_text)
        TextView txtTitle;
        @BindView(R.id.event_address_text)
        TextView txtAddress;
        @BindView(R.id.event_reminder_icon)
        ImageView imgReminder;
        @BindView(R.id.layout_event)
        LinearLayout parentLayout;
        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    //news adapter for home
    public ListReminderAdapter(Context mContext, List<ListReminder> eventList){
        this.mContext = mContext;
        this.eventList = eventList;
        this.unbinder = unbinder;
    }


    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_events, viewGroup, false);

        res = itemView.getResources();
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventViewHolder eventViewHolder, final int position) {
        ListReminder event = eventList.get(position);
        Log.d("News", ""+eventList.size());
        eventViewHolder.txtTitle.setText(event.getTitle());
        eventViewHolder.txtAddress.setText(event.getAddress());
        eventViewHolder.imgReminder.setImageDrawable(res.getDrawable(R.drawable.ic_bookmark_black_24dp));
        eventViewHolder.imgReminder.setOnClickListener(v -> {
            Loading.show(mContext);
            ListHelper.removeBookmark(event.getIdList(), new RestCallback<ApiResponse>() {
                @Override
                public void onSuccess(Headers headers, ApiResponse body) {
                    Loading.hide(mContext);
                    Toast.makeText(mContext, "Pengingat anda telah dihapus", Toast.LENGTH_SHORT).show();
                    Intent mylist = new Intent(mContext, MainActivity.class);
                    mylist.putExtra("frag", "mylistfragment");
                    mylist.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(mylist);
                }

                @Override
                public void onFailed(ErrorResponse error) {
                    Loading.hide(mContext);
                }

                @Override
                public void onCanceled() {

                }
            });
        });
        Glide.with(mContext).load(event.getBanner()).into(eventViewHolder.imgNews);
        //click
        eventViewHolder.parentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EventDetailActivity.class);
            intent.putExtra("id",eventList.get(position).getEventId());
            intent.putExtra("flag", "1");
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

//    private void showPopupMenu(View view) {
//        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_album, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
//        popup.show();
//    }
}

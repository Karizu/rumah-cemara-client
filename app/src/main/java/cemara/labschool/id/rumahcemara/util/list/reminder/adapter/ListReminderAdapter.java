package cemara.labschool.id.rumahcemara.util.list.reminder.adapter;

import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.home.highlight.EventDetailActivity;
import cemara.labschool.id.rumahcemara.util.event.model.Event;
import cemara.labschool.id.rumahcemara.util.list.reminder.model.ListReminder;

public class ListReminderAdapter extends RecyclerView.Adapter<ListReminderAdapter.EventViewHolder>{
    private List<ListReminder> eventList;
    private Context mContext;
    private Unbinder unbinder;


    public class EventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.event_image)
        ImageView imgNews;
        @BindView(R.id.event_title_text)
        TextView txtTitle;
        @BindView(R.id.event_address_text)
        TextView txtAddress;
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

        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventViewHolder eventViewHolder, final int position) {
        ListReminder event = eventList.get(position);
        Log.d("News", ""+eventList.size());
        eventViewHolder.txtTitle.setText(event.getTitle());
        eventViewHolder.txtAddress.setText(event.getAddress());
        Glide.with(mContext).load(event.getBanner()).into(eventViewHolder.imgNews);
        //click
        eventViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventDetailActivity.class);
                intent.putExtra("id",eventList.get(position).getEventId());
                mContext.startActivity(intent);
            }
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

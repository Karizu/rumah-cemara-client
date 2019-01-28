package cemara.labschool.id.rumahcemara.util.events.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.util.events.model.Events;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {
    private List<Events> eventsList;
    private Context mContext;
    private Unbinder unbinder;
    private String rowType;


    public class EventsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.event_image)
        ImageView eventImage;
        @BindView(R.id.event_title_text)
        TextView eventTitle;
        @BindView(R.id.event_address_text)
        TextView eventAddress;
        @BindView(R.id.event_share_icon)
        ImageView shareIcon;
        @BindView(R.id.event_reminder_icon)
        ImageView reminderIcon;
        @BindView(R.id.cv_event)
        CardView parentLayout;

        EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    //news adapter for home
    public EventsAdapter(Context mContext, List<Events> eventsList) {
        this.mContext = mContext;
        this.eventsList = eventsList;
        this.unbinder = unbinder;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_events, viewGroup, false);
        return new EventsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventsAdapter.EventsViewHolder eventsViewHolder, int position) {
        Events events = eventsList.get(position);
        Log.d("News", "" + eventsList.size());
        eventsViewHolder.eventTitle.setText(events.getEventTitle());
        eventsViewHolder.eventAddress.setText(events.getEventAddress());
        // loading ImageNews using Glide library
        Glide.with(mContext).load(events.getEventImage()).into(eventsViewHolder.eventImage);
        //click
        eventsViewHolder.parentLayout.setOnClickListener(v -> Toast.makeText(mContext, "Event Click", Toast.LENGTH_SHORT).show());
        eventsViewHolder.shareIcon.setOnClickListener(view -> Toast.makeText(mContext, "Share button click", Toast.LENGTH_SHORT).show());
        eventsViewHolder.reminderIcon.setOnClickListener(view -> Toast.makeText(mContext, "Reminder click", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
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

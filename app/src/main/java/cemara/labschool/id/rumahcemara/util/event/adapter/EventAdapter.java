package cemara.labschool.id.rumahcemara.util.event.adapter;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.home.highlight.EventDetailActivity;
import cemara.labschool.id.rumahcemara.util.event.model.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{
    private List<Event> eventList;
    private Context mContext;
    private Unbinder unbinder;
    private String rowType;


    public class EventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_news)
        ImageView imgNews;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_author)
        TextView txtAuthor;
        @BindView(R.id.txt_date_created)
        TextView txtDateCreated;
        @BindView(R.id.cv_news)
        CardView parentLayout;
        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    //news adapter for home
    public EventAdapter(Context mContext, List<Event> eventList){
        this.mContext = mContext;
        this.eventList = eventList;
        this.unbinder = unbinder;
        this.rowType = "home_news";
    }

    //news adapter for highlight and detail
    public EventAdapter(Context mContext, List<Event> eventList, String rowType){
        this.mContext = mContext;
        this.eventList = eventList;
        this.unbinder = unbinder;
        this.rowType = rowType;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = null;
        if (rowType.equals("home_news")){
            itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_news, viewGroup, false);
        }
        if (rowType.equals("highlight_news") || rowType.equals("highlight_event")){
             itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_highlight_news, viewGroup, false);
        }
        if (rowType.equals("highlight_event")){
            itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_highlight_event, viewGroup, false);
        }
        if (rowType.equals("highlight_detail")){
            itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_highlight_detail, viewGroup, false);
        }

        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventViewHolder eventViewHolder, int position) {
        Event event = eventList.get(position);
        Log.d("News", ""+eventList.size());
        eventViewHolder.txtTitle.setText(event.getTitle());
        eventViewHolder.txtAuthor.setText(event.getAuthor());
        eventViewHolder.txtDateCreated.setText(String.valueOf(event.getDateCreated()));
        // loading ImageNews using Glide library
        //Glide.with(mContext).load(news.getNewsImage()).into(eventViewHolder.imgNews);
        Glide.with(mContext).load(event.getBanner()).into(eventViewHolder.imgNews);
        //click
        eventViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventDetailActivity.class);
                intent.putExtra("id",event.getEventId());
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

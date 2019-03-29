package cemara.labschool.id.rumahcemara.util.list.saved.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
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
import cemara.labschool.id.rumahcemara.home.highlight.ArticleDetailActivity;
import cemara.labschool.id.rumahcemara.home.highlight.NewsDetailActivity;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.list.saved.model.ListSaved;
import okhttp3.Headers;

public class ListSavedAdapter extends RecyclerView.Adapter<ListSavedAdapter.EventViewHolder>{
    private List<ListSaved> eventList;
    private Context mContext;
    private Unbinder unbinder;
    private Resources res;


    public class EventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_news)
        ImageView imgNews;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_author)
        TextView txtAuthor;
        @BindView(R.id.txt_date_created)
        TextView txtDateCreated;
        @BindView(R.id.mark_news)
        ImageView imgReminder;
        @BindView(R.id.layout_news)
        LinearLayout parentLayout;
        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    //news adapter for home
    public ListSavedAdapter(Context mContext, List<ListSaved> eventList){
        this.mContext = mContext;
        this.eventList = eventList;
        this.unbinder = unbinder;
    }


    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_news, viewGroup, false);
        res = itemView.getResources();
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventViewHolder eventViewHolder, final int position) {
        ListSaved event = eventList.get(position);
        Log.d("Saved", ""+eventList.size());
        eventViewHolder.txtTitle.setText(event.getTitle());
        eventViewHolder.txtAuthor.setText(event.getAuthor());
        eventViewHolder.txtDateCreated.setText(event.getDateCreated());
        eventViewHolder.imgReminder.setImageDrawable(res.getDrawable(R.drawable.ic_bookmark_black_24dp));
        eventViewHolder.imgReminder.setOnClickListener(v -> {
            Loading.show(mContext);
            ListHelper.removeBookmark(event.getIdd(), new RestCallback<ApiResponse>() {
                @Override
                public void onSuccess(Headers headers, ApiResponse body) {
                    Loading.hide(mContext);
                    Toast.makeText(mContext, "Bookmark anda telah dihapus", Toast.LENGTH_SHORT).show();
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
        eventViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(eventList.get(position).isArticle()){
                    intent = new Intent(mContext, ArticleDetailActivity.class);
                } else {
                    intent = new Intent(mContext, NewsDetailActivity.class);
                }
                intent.putExtra("id",eventList.get(position).getId());
                intent.putExtra("flag", "1");
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

package cemara.labschool.id.rumahcemara.util.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import cemara.labschool.id.rumahcemara.util.news.model.News;
import cemara.labschool.id.rumahcemara.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{
    private List<News> newsList;
    private Context mContext;
    private Unbinder unbinder;


    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_news)
        ImageView imgNews;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_author)
        TextView txtAuthor;
        @BindView(R.id.txt_date_created)
        TextView txtDateCreated;
        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
    public  NewsAdapter(Context mContext, List<News> newsList){
        this.mContext = mContext;
        this.newsList = newsList;
        this.unbinder = unbinder;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_news, viewGroup, false);

        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.NewsViewHolder newsViewHolder, int position) {
        News news = newsList.get(position);
        Log.d("News", ""+newsList.size());
        newsViewHolder.txtTitle.setText(news.getTitle());
        newsViewHolder.txtAuthor.setText(news.getAuthor());
        newsViewHolder.txtDateCreated.setText(String.valueOf(news.getDateCreated()));
        // loading ImageNews using Glide library
        Glide.with(mContext).load(news.getNewsImage()).into(newsViewHolder.imgNews);

    }

    @Override
    public int getItemCount() {
        return newsList.size();
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

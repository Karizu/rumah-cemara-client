package cemara.labschool.id.rumahcemara.util.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.home.highlight.ArticleDetailActivity;
import cemara.labschool.id.rumahcemara.home.highlight.EventDetailActivity;
import cemara.labschool.id.rumahcemara.home.highlight.NewsDetailActivity;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.util.MarkArticleClickListener;
import cemara.labschool.id.rumahcemara.util.MarkNewsClickListener;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.news.model.News;
import cemara.labschool.id.rumahcemara.R;
import io.realm.Realm;
import okhttp3.Headers;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<News> newsList;
    private Context mContext;
    private Unbinder unbinder;
    private String rowType;


    public class NewsViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.share_news)
        ImageView shareNews;
        @BindView(R.id.mark_news)
        ImageView markNews;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    //news adapter for home
    public  NewsAdapter(Context mContext, List<News> newsList){
        this.mContext = mContext;
        this.newsList = newsList;
        this.unbinder = unbinder;
        this.rowType = "home_news";
    }

    //news adapter for highlight and detail
    public  NewsAdapter(Context mContext, List<News> newsList, String rowType){
        this.mContext = mContext;
        this.newsList = newsList;
        this.unbinder = unbinder;
        this.rowType = rowType;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = null;
        if (rowType.equals("home_news")){
            itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_news, viewGroup, false);
        }
        if (rowType.equals("highlight_news") || rowType.equals("highlight_article")){
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
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.NewsViewHolder newsViewHolder, int position) {
        String CategoryId;
        News news = newsList.get(position);
        Log.d("News", "" + newsList.size());
        newsViewHolder.txtTitle.setText(news.getTitle());
        newsViewHolder.txtAuthor.setText(news.getAuthor());
        newsViewHolder.txtDateCreated.setText(String.valueOf(news.getDateCreated()));
        // loading ImageNews using Glide library
        //Glide.with(mContext).load(news.getNewsImage()).into(newsViewHolder.imgNews);
        Glide.with(mContext).load(news.getBanner()).into(newsViewHolder.imgNews);
        //click
        CategoryId = news.getNewsCategoryId();
        Log.d("Category Id", CategoryId);
        newsViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CategoryId.equals("993df927-7e72-555f-9abb-74d695ac172f")){
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra("id",news.getNewsId());
                    mContext.startActivity(intent);
                }
                if (CategoryId.equals("2784d424-99a3-4078-b32f-a0f63b984c86") ||
                        CategoryId.equals("3a11c4b3-cb8a-4dc8-970f-33f5b6132844") ||
                        CategoryId.equals("2750251b-eb91-5e02-a055-52e57983f49e")){
                    Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                    intent.putExtra("id",news.getNewsId());
                    mContext.startActivity(intent);
                }
                if (CategoryId.equals("2750251b-eb91-5e02-a055-52e57983f49e") ||
                        CategoryId.equals("e79c0889-a2aa-5327-868d-e6d91c85ecf4") ||
                        CategoryId.equals("026334b8-47cf-5274-a523-82c9b69cf93e")){
                    Intent intent = new Intent(mContext, EventDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("id",news.getNewsId());
                    mContext.startActivity(intent);
                }

            }
        });
        newsViewHolder.markNews.setOnClickListener(new MarkNewsClickListener(news));

        newsViewHolder.shareNews.setOnClickListener(view -> {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, news.getTitle()+
                    "\n"+ news.getBanner());
            mContext.startActivity(Intent.createChooser(sharingIntent, "Share using:"));
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}

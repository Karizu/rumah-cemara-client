package cemara.labschool.id.rumahcemara.util.broadcast.adapter;

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
import cemara.labschool.id.rumahcemara.home.highlight.ArticleDetailActivity;
import cemara.labschool.id.rumahcemara.home.highlight.BroadcastDetailActivity;
import cemara.labschool.id.rumahcemara.util.MarkArticleClickListener;
import cemara.labschool.id.rumahcemara.util.MarkBroadcastClickListener;
import cemara.labschool.id.rumahcemara.util.article.model.Article;
import cemara.labschool.id.rumahcemara.util.broadcast.model.Broadcast;

public class BroadcastAdapter extends RecyclerView.Adapter<BroadcastAdapter.ArticleViewHolder>{
    private List<cemara.labschool.id.rumahcemara.util.broadcast.model.Broadcast> broadcastList;
    private Context mContext;
    private Unbinder unbinder;
    private String rowType;


    public class ArticleViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.mark_news)
        ImageView markNews;
        @BindView(R.id.share_news)
        ImageView share;

        ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    //news adapter for home
    public BroadcastAdapter(Context mContext, List<cemara.labschool.id.rumahcemara.util.broadcast.model.Broadcast> broadcastList){
        this.mContext = mContext;
        this.broadcastList = broadcastList;
        this.unbinder = unbinder;
        this.rowType = "highlight_broadcast";
    }

    //news adapter for highlight and detail
    public BroadcastAdapter(Context mContext, List<Broadcast> broadcastList, String rowType){
        this.mContext = mContext;
        this.broadcastList = broadcastList;
        this.unbinder = unbinder;
        this.rowType = rowType;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = null;
        if (rowType.equals("home_news")){
            itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_news, viewGroup, false);
        }
        if (rowType.equals("highlight_news") || rowType.equals("highlight_article") || rowType.equals("highlight_broadcast")){
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

        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ArticleViewHolder articleViewHolder, int position) {
        cemara.labschool.id.rumahcemara.util.broadcast.model.Broadcast article = broadcastList.get(position);
        Log.d("News", ""+ broadcastList.size());
        articleViewHolder.txtTitle.setText(article.getTitle());
        articleViewHolder.txtAuthor.setText(article.getAuthor());
        articleViewHolder.txtDateCreated.setText(String.valueOf(article.getDateCreated()));
        // loading ImageNews using Glide library
        //Glide.with(mContext).load(news.getNewsImage()).into(articleViewHolder.imgNews);
        Glide.with(mContext).load(article.getBanner()).into(articleViewHolder.imgNews);
        //click
        articleViewHolder.parentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, BroadcastDetailActivity.class);
            intent.putExtra("id",article.getBroadcastId());
            mContext.startActivity(intent);
        });

        articleViewHolder.markNews.setVisibility(View.GONE);
        articleViewHolder.share.setVisibility(View.GONE);
        articleViewHolder.markNews.setOnClickListener(new MarkBroadcastClickListener(mContext, article));
        articleViewHolder.share.setOnClickListener(view -> {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, article.getTitle()+
                    "\n"+ article.getBanner());
            mContext.startActivity(Intent.createChooser(sharingIntent, "Share using:"));
        });
    }

    @Override
    public int getItemCount() {
        return broadcastList.size();
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

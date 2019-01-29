package cemara.labschool.id.rumahcemara.home.highlight.article.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.Constants;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.ArticleHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.util.ArticleClickListener;
import cemara.labschool.id.rumahcemara.util.MarkArticleClickListener;
import cemara.labschool.id.rumahcemara.util.article.model.adapter.ArticleAdapter;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import cemara.labschool.id.rumahcemara.util.news.adapter.NewsAdapter;
import cemara.labschool.id.rumahcemara.util.news.model.News;
import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleCapacityFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    cemara.labschool.id.rumahcemara.util.article.model.adapter.ArticleAdapter articleAdapter;
    List<cemara.labschool.id.rumahcemara.util.article.model.Article> articleList = new ArrayList<>();
    //carousel
    @BindView(R.id.carousel)
    CarouselView customCarouselView;
    String[] sampleNetworkImageURLs = {
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image1&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image4&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image5&txt=350%C3%97150&w=350&h=150"
    };
    String[] sampleTitles = {"Artikel 1", "Artikel 2", "Artikel 3"};
    List<cemara.labschool.id.rumahcemara.model.Article> articlePager=new ArrayList<>();

    View rootView;

    public ArticleCapacityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.article_capacity_fragment, container, false);
        ButterKnife.bind(this, rootView);
        getListNews();
        return rootView;
    }

    private void getListNews() {
        Loading.show(getContext());
        ArticleHelper.getArticleTraining(new RestCallback<ApiResponse<List<Article>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<cemara.labschool.id.rumahcemara.model.Article>> body) {
                Loading.hide(getContext());
                if (body != null && body.isStatus()) {
                    articleList.clear();
                    List<cemara.labschool.id.rumahcemara.model.Article> articleLists=body.getData();
                    Log.d("Training","Lists");

                    // Insert Pager
                    int maxPager=articleLists.size()> Constants.MAX_NEWS_PAGER?Constants.MAX_NEWS_PAGER: articleLists.size();
                    for(int i=0;i<maxPager;i++){
                        articlePager.add(articleLists.get(0));//Always get position 0 , because always delete item already get below
                        articleLists.remove(0);
                    }
                    for(int i=0;i<articleLists.size();i++){
                        articleList.add(new cemara.labschool.id.rumahcemara.util.article.model.Article(articleLists.get(i).getId(), articleLists.get(i).getTitle(), articleLists.get(i).getUserCreator().getProfile().getFullname(), DateHelper.dateFormat(DateHelper.stringToDate(articleLists.get(i).getCreatedAt())), articleLists.get(i).getBanner()));
                    }
                    //articleList.add(new News("1", "testing", "test", "June 20 2019", R.drawable.img_article));

                    articleAdapter = new ArticleAdapter(getContext(), articleList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(articleAdapter);
                    articleAdapter.notifyDataSetChanged();

                    initSlider(rootView);


                } else {
//                        loadingDialog.dismiss();
                    Toast.makeText(getContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(getContext());
                Toast.makeText(getContext(),"Gagal Ambil Data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled() {
                Loading.hide(getContext());
            }
        });
    }

    private void initSlider(View v) {
        customCarouselView.setViewListener(viewListener);
        //customCarouselView.setPageCount(sampleTitles.length);
        customCarouselView.setPageCount(articlePager.size());
        customCarouselView.setSlideInterval(4000);
    }


    // To set images
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Glide.with(getContext())
                    .load(sampleNetworkImageURLs[position])
                    .into(imageView);
        }
    };

    // To set custom views
    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.carousel_custom_news, null);
            TextView tvLabelCarousel = customView.findViewById(R.id.tv_label_carousel);
            ImageView ivCarousel = customView.findViewById(R.id.iv_carousel);
            ImageView ivShare = customView.findViewById(R.id.iv_carousel_share);
            ImageView ivMark = customView.findViewById(R.id.iv_carousel_mark);

            Glide.with(getContext())
                    .load(articlePager.get(position).getBanner())
                    .into(ivCarousel);
            tvLabelCarousel.setText(articlePager.get(position).getTitle());
            ivShare.setOnClickListener(shareOnClickListener);
            ivMark.setOnClickListener(new MarkArticleClickListener(articlePager.get(position)));

            ivCarousel.setOnClickListener(new ArticleClickListener(articlePager.get(position)));
            return customView;
        }
    };

    //To share
    View.OnClickListener shareOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Shared", Toast.LENGTH_SHORT).show();
        }
    };


}
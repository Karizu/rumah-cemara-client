package cemara.labschool.id.rumahcemara.home.highlight.broadcast.fragment;


import android.content.Context;
import android.content.Intent;
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
import cemara.labschool.id.rumahcemara.api.BroadcastHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Broadcast;
import cemara.labschool.id.rumahcemara.util.ArticleClickListener;
import cemara.labschool.id.rumahcemara.util.BroadcastClickListener;
import cemara.labschool.id.rumahcemara.util.MarkArticleClickListener;
import cemara.labschool.id.rumahcemara.util.MarkBroadcastClickListener;
import cemara.labschool.id.rumahcemara.util.broadcast.adapter.BroadcastAdapter;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 */
public class BroadcastFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    BroadcastAdapter broadcastAdapter;
    List<cemara.labschool.id.rumahcemara.util.broadcast.model.Broadcast> broadcastList = new ArrayList<>();
    String id;
    private Context context;
    //carousel
    @BindView(R.id.carousel)
    CarouselView customCarouselView;
    String[] sampleNetworkImageURLs = {
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image1&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image4&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image5&txt=350%C3%97150&w=350&h=150"
    };
    String[] sampleTitles = {"Artikel 1", "Artikel 2", "Artikel 3"};
    List<cemara.labschool.id.rumahcemara.model.Broadcast> articlePager=new ArrayList<>();

    View rootView;

    public BroadcastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.article_campaign_fragment, container, false);
        ButterKnife.bind(this, rootView);
        context = getActivity();
        try {
            assert getArguments() != null;
            id = getArguments().getString("id", "");
        }catch (Exception ignored){}
        Log.d("IDDDDDD", id);
        getListNews(id);
        return rootView;
    }

    private void getListNews(String id) {
        Loading.show(getContext());
        BroadcastHelper.getArticleCampaign(id, new RestCallback<ApiResponse<List<cemara.labschool.id.rumahcemara.model.Broadcast>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<cemara.labschool.id.rumahcemara.model.Broadcast>> body) {
                Loading.hide(getContext());
                if (body != null && body.isStatus()) {
                    broadcastList.clear();
                    List<Broadcast> articleLists = body.getData();
                    Log.d("Training","Lists");

                    // Insert Pager
                    int maxPager=articleLists.size()> Constants.MAX_NEWS_PAGER?Constants.MAX_NEWS_PAGER: articleLists.size();
                    for(int i=0;i<maxPager;i++){
                        articlePager.add(articleLists.get(0));//Always get position 0 , because always delete item already get below
                        articleLists.remove(0);
                    }
                    for(int i=0;i<articleLists.size();i++){
                        broadcastList.add(new cemara.labschool.id.rumahcemara.util.broadcast.model.Broadcast(articleLists.get(i).getId(), articleLists.get(i).getTitle(), articleLists.get(i).getUserCreator().getProfile().getFullname(), DateHelper.dateFormat(DateHelper.stringToDate(articleLists.get(i).getCreatedAt())), articleLists.get(i).getBanner()));
                    }
                    //broadcastList.add(new News("1", "testing", "test", "June 20 2019", R.drawable.img_article));

                    broadcastAdapter = new BroadcastAdapter(getContext(), broadcastList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(broadcastAdapter);
                    broadcastAdapter.notifyDataSetChanged();

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
            ivMark.setVisibility(View.GONE);
            ivShare.setVisibility(View.GONE);

            Glide.with(getContext())
                    .load(articlePager.get(position).getBanner())
                    .into(ivCarousel);
            tvLabelCarousel.setText(articlePager.get(position).getTitle());
            ivShare.setOnClickListener(v -> {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, articlePager.get(position).getTitle()+
                        "\n"+ articlePager.get(position).getBanner());
                startActivity(Intent.createChooser(sharingIntent, "Share using:"));
            });
            ivMark.setOnClickListener(new MarkBroadcastClickListener(context, articlePager.get(position)));

            ivCarousel.setOnClickListener(new BroadcastClickListener(articlePager.get(position)));
            return customView;
        }
    };
}

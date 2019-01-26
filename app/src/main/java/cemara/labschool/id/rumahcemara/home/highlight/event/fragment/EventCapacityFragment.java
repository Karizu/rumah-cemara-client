package cemara.labschool.id.rumahcemara.home.highlight.event.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.util.news.adapter.NewsAdapter;
import cemara.labschool.id.rumahcemara.util.news.model.News;


public class EventCapacityFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    List<News> newsList = new ArrayList<>();
    //carousel
    @BindView(R.id.carousel)
    CarouselView customCarouselView;
    String[] sampleNetworkImageURLs = {
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image1&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image4&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image5&txt=350%C3%97150&w=350&h=150"
    };
    String[] sampleTitles = {"Artikel 1", "Artikel 2", "Artikel 3"};

    public EventCapacityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.event_capacity_fragment, container, false);
        ButterKnife.bind(this, rootView);
        getListNews();
        initSlider(rootView);
        return rootView;
    }

    private void getListNews() {
        newsList.clear();
        newsList.add(new News("1", "testing", "test", "June 20 2019", R.drawable.img_news));
        newsList.add(new News("1", "testing", "test", "June 20 2019", R.drawable.img_news));
        newsAdapter = new NewsAdapter(getActivity(), newsList, "highlight_event");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();
    }

    private void initSlider(View v) {
        customCarouselView.setPageCount(sampleTitles.length);
        customCarouselView.setSlideInterval(4000);
        customCarouselView.setViewListener(viewListener);
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
            View customView = getLayoutInflater().inflate(R.layout.carousel_custom_event, null);
            TextView tvLabelCarousel = customView.findViewById(R.id.tv_label_carousel);
            ImageView ivCarousel = customView.findViewById(R.id.iv_carousel);
            ImageView ivReminder = customView.findViewById(R.id.iv_carousel_reminder);
            ImageView ivShare = customView.findViewById(R.id.iv_carousel_share);

            Glide.with(getContext())
                    .load(sampleNetworkImageURLs[position])
                    .into(ivCarousel);
            tvLabelCarousel.setText(sampleTitles[position]);
            ivReminder.setOnClickListener(reminderOnClickListener);
            ivShare.setOnClickListener(shereOnClickListerner);
            ivCarousel.setOnClickListener(imageOnClickListener);
            return customView;
        }
    };

    //To share
    View.OnClickListener reminderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Reminder", Toast.LENGTH_SHORT).show();
        }
    };

    //To Mark
    View.OnClickListener shereOnClickListerner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Shared", Toast.LENGTH_SHORT).show();
        }
    };

    //To News
    View.OnClickListener imageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Image Clicked", Toast.LENGTH_SHORT).show();
        }
    };

}
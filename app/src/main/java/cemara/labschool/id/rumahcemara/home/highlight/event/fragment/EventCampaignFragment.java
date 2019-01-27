package cemara.labschool.id.rumahcemara.home.highlight.event.fragment;


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
import cemara.labschool.id.rumahcemara.api.EventHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.util.EventClickListener;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.event.adapter.EventAdapter;
import cemara.labschool.id.rumahcemara.model.Event;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import okhttp3.Headers;

public class EventCampaignFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    EventAdapter eventAdapter;
    List<cemara.labschool.id.rumahcemara.util.event.model.Event> eventList = new ArrayList<>();
    //carousel
    @BindView(R.id.carousel)
    CarouselView customCarouselView;
    String[] sampleNetworkImageURLs = {
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image1&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image4&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image5&txt=350%C3%97150&w=350&h=150"
    };
    String[] sampleTitles = {"Artikel 1", "Artikel 2", "Artikel 3"};
    List<cemara.labschool.id.rumahcemara.model.Event> eventPager=new ArrayList<>();

    View rootView;

    public EventCampaignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.event_campaign_fragment, container, false);
        ButterKnife.bind(this, rootView);
        getListEvent();
        return rootView;
    }

    private void getListEvent() {
        Loading.show(getContext());
        EventHelper.getEventCampaign(new RestCallback<ApiResponse<List<Event>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<cemara.labschool.id.rumahcemara.model.Event>> body) {
                Loading.hide(getContext());
                if (body != null && body.isStatus()) {
                    eventList.clear();
                    List<cemara.labschool.id.rumahcemara.model.Event> eventLists=body.getData();
                    Log.d("Training","Lists");

                    // Insert Pager
                    int maxPager=eventLists.size()> Constants.MAX_NEWS_PAGER?Constants.MAX_NEWS_PAGER: eventLists.size();
                    for(int i=0;i<maxPager;i++){
                        eventPager.add(eventLists.get(0));//Always get position 0 , because always delete item already get below
                        eventLists.remove(0);
                    }
                    for(int i=0;i<eventLists.size();i++){
                        eventList.add(new cemara.labschool.id.rumahcemara.util.event.model.Event(eventLists.get(i).getId(), eventLists.get(i).getTitle(), eventLists.get(i).getUserCreator().getProfile().getFullname(), DateHelper.dateFormat(DateHelper.stringToDate(eventLists.get(i).getCreatedAt())), eventLists.get(i).getBanner()));
                    }
                    //articleList.add(new News("1", "testing", "test", "June 20 2019", R.drawable.img_article));

                    eventAdapter = new EventAdapter(getContext(), eventList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(eventAdapter);
                    eventAdapter.notifyDataSetChanged();

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
//        customCarouselView.setPageCount(sampleTitles.length);
        customCarouselView.setPageCount(eventPager.size());
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
            View customView = getLayoutInflater().inflate(R.layout.carousel_custom_event, null);
            TextView tvLabelCarousel = customView.findViewById(R.id.tv_label_carousel);
            ImageView ivCarousel = customView.findViewById(R.id.iv_carousel);
            ImageView ivReminder = customView.findViewById(R.id.iv_carousel_reminder);
            ImageView ivShare = customView.findViewById(R.id.iv_carousel_share);

            Glide.with(getContext())
                    .load(eventPager.get(position).getBanner())
                    .into(ivCarousel);
            tvLabelCarousel.setText(eventPager.get(position).getTitle());
            ivReminder.setOnClickListener(reminderOnClickListener);
            ivShare.setOnClickListener(shereOnClickListerner);
            //ivCarousel.setOnClickListener(imageOnClickListener);
            ivCarousel.setOnClickListener(new EventClickListener(eventPager.get(position)));

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

    //To Event
    View.OnClickListener imageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Image Clicked", Toast.LENGTH_SHORT).show();
        }
    };

}
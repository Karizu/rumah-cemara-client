package cemara.labschool.id.rumahcemara.home.fragment;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.ArticleHelper;
import cemara.labschool.id.rumahcemara.api.EventHelper;
import cemara.labschool.id.rumahcemara.api.NewsHelper;
import cemara.labschool.id.rumahcemara.home.highlight.article.ArticleActivity;
import cemara.labschool.id.rumahcemara.home.highlight.event.EventActivity;
import cemara.labschool.id.rumahcemara.home.highlight.news.NewsActivity;
import cemara.labschool.id.rumahcemara.home.service.asktheexpert.AskTheExpertActivity;
import cemara.labschool.id.rumahcemara.home.service.behavioral.CounselingAppointmentActivity;
import cemara.labschool.id.rumahcemara.home.service.biomedical.BiomedicalAppointmentActivity;
import cemara.labschool.id.rumahcemara.home.service.structural.LegalCounselingAppointmentActivity;
import cemara.labschool.id.rumahcemara.home.service.structural.StructuralLegalAidActivity;
import cemara.labschool.id.rumahcemara.home.service.structural.StructuralViolationActivity;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.util.article.model.adapter.ArticleAdapter;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.event.model.Event;
import cemara.labschool.id.rumahcemara.util.events.adapter.EventsAdapter;
import cemara.labschool.id.rumahcemara.util.events.model.Events;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import cemara.labschool.id.rumahcemara.util.news.adapter.NewsAdapter;
import cemara.labschool.id.rumahcemara.util.news.model.News;
import okhttp3.Headers;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements LocationListener {


    @BindView(R.id.btn_highlight_menu)
    ImageView btnHighlight;
    @BindView(R.id.highlight_menu_item)
    LinearLayout highlightItem;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    ArticleAdapter articleAdapter;
    EventsAdapter eventsAdapter;
    List<News> newsList = new ArrayList<>();
    List<cemara.labschool.id.rumahcemara.util.article.model.Article> articlesList = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();
    Dialog dialog;
    int TAG_CODE_PERMISSION_LOCATION;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, rootView);

        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i("uuh!", "need permissions....");
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,},
                    TAG_CODE_PERMISSION_LOCATION);
        }

        getListNews();
        init();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
//        getListNews();
    }

    @OnClick(R.id.btn_biomedical)
    public void dialogBiomedical() {
        showDialog(R.layout.dialog_biomedical);
        final CardView biomedical_appointment = dialog.findViewById(R.id.btn_biomedical_appointment);
        biomedical_appointment.setOnClickListener(view -> {
//                toast();
            startActivity(new Intent(getContext(), BiomedicalAppointmentActivity.class));
        });

        ImageView closeDialog = dialog.findViewById(R.id.btn_close);
        closeDialog.setOnClickListener(view -> dialog.dismiss());
    }

    @OnClick(R.id.btn_behavioral)
    public void dialogBehavioral() {
        showDialog(R.layout.dialog_behavioral);
        CardView behavioral_appointment = dialog.findViewById(R.id.btn_behavioral_appointment);
        ImageView closeDialog = dialog.findViewById(R.id.btn_close);
        closeDialog.setOnClickListener(view -> dialog.dismiss());

        behavioral_appointment.setOnClickListener(view -> startActivity(new Intent(getContext(), CounselingAppointmentActivity.class)));
    }

    @OnClick(R.id.btn_structural)
    public void dialogStructural() {
        showDialog(R.layout.dialog_structural);
        CardView structural_appointment = dialog.findViewById(R.id.btn_structural_appointment);
        CardView legal_aid = dialog.findViewById(R.id.btn_legal_aid);
        CardView violation_report = dialog.findViewById(R.id.btn_violation_report);
        ImageView closeDialog = dialog.findViewById(R.id.btn_close);
        closeDialog.setOnClickListener(view -> dialog.dismiss());

        structural_appointment.setOnClickListener(view -> {
//                toast();
            Intent intent = new Intent(getContext(), LegalCounselingAppointmentActivity.class);
            startActivity(intent);
        });

        legal_aid.setOnClickListener(view -> {
//                toast();
            Intent intent = new Intent(getContext(), StructuralLegalAidActivity.class);
            startActivity(intent);
        });

        violation_report.setOnClickListener(view -> {
//                toast();
            Intent intent = new Intent(getContext(), StructuralViolationActivity.class);
            startActivity(intent);
        });
    }

    @OnClick(R.id.btn_ask_the_expert)
    public void toAsktheExpert() {
        startActivity(new Intent(getContext(), AskTheExpertActivity.class));
    }

    @OnClick(R.id.layout_news)
    public void toNews() {
        Intent intent = new Intent(getContext(), NewsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_article)
    public void toArticle() {
        Intent intent = new Intent(getContext(), ArticleActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_event)
    public void toEvent() {
        Intent intent = new Intent(getContext(), EventActivity.class);
        startActivity(intent);
    }


    private void getListNews() {
        Loading.show(getContext());
        NewsHelper.getNews( new RestCallback<ApiResponse<List<cemara.labschool.id.rumahcemara.model.News>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<cemara.labschool.id.rumahcemara.model.News>> body) {
                if (body != null && body.isStatus()) {
                    List<cemara.labschool.id.rumahcemara.model.News> newsLists=body.getData();
                    Log.d("aa","sss");
                    Log.d("Size", String.valueOf(newsLists.size()));
                    if (newsLists.size() >= 3){
                        Log.d("News","Masuk First If");
                        for(int i=0;i<3;i++){
                            newsList.add(new News(newsLists.get(i).getId(),
                                    newsLists.get(i).getNews_category_id(),
                                    newsLists.get(i).getTitle(),
                                    newsLists.get(i).getUserCreator().getProfile().getFullname(),
                                    DateHelper.dateFormat(DateHelper.stringToDate(newsLists.get(i).getCreatedAt())),
                                    newsLists.get(i).getBanner(),
                                    newsLists.get(i).getContent(),
                                    newsLists.get(i).getUserCreator()));
                        }
                    } else {
                        for(int i=0;i<newsLists.size();i++){
                            newsList.add(new News(newsLists.get(i).getId(),
                                    newsLists.get(i).getNews_category_id(),
                                    newsLists.get(i).getTitle(),
                                    newsLists.get(i).getUserCreator().getProfile().getFullname(),
                                    DateHelper.dateFormat(DateHelper.stringToDate(newsLists.get(i).getCreatedAt())),
                                    newsLists.get(i).getBanner(),
                                    newsLists.get(i).getContent(),
                                    newsLists.get(i).getUserCreator()));
                        }
                    }


                    ArticleHelper.getArticle(new RestCallback<ApiResponse<List<Article>>>() {
                        @Override
                        public void onSuccess(Headers headers, ApiResponse<List<Article>> body) {
                            Loading.hide(getContext());
                            if (body != null && body.isStatus()) {
                                List<cemara.labschool.id.rumahcemara.model.Article> newsArticles=body.getData();
                                if (newsArticles.size() >= 3){
                                    for (int i=0; i<3; i++){
                                        newsList.add(new News(newsArticles.get(i).getId(),
                                                newsArticles.get(i).getArticleCategoryId(),
                                                newsArticles.get(i).getTitle(),
                                                newsArticles.get(i).getUserCreator().getProfile().getFullname(),
                                                DateHelper.dateFormat(DateHelper.stringToDate(newsArticles.get(i).getCreatedAt())),
                                                newsArticles.get(i).getBanner(),
                                                newsArticles.get(i).getContent(),
                                                newsArticles.get(i).getUserCreator()));
                                    }
                                } else {
                                    for (int i=0; i<newsArticles.size(); i++){
                                        newsList.add(new News(newsArticles.get(i).getId(),
                                                newsArticles.get(i).getArticleCategoryId(),
                                                newsArticles.get(i).getTitle(),
                                                newsArticles.get(i).getUserCreator().getProfile().getFullname(),
                                                DateHelper.dateFormat(DateHelper.stringToDate(newsArticles.get(i).getCreatedAt())),
                                                newsArticles.get(i).getBanner(),
                                                newsArticles.get(i).getContent(),
                                                newsArticles.get(i).getUserCreator()));
                                    }
                                }
                            }

                            newsAdapter = new NewsAdapter(getActivity(), articlesList, "home_news", "article", "art");
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
//                            recyclerView.setAdapter(articleAdapter);
//                            articleAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailed(ErrorResponse error) {
                            Loading.hide(getContext());
                            Log.d("Error", error.getMessage());
                            Toast.makeText(getContext(),"Gagal Ambil Data", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCanceled() {
                            Loading.hide(getContext());
                        }
                    });

                        EventHelper.getEvent(new RestCallback<ApiResponse<List<cemara.labschool.id.rumahcemara.model.Event>>>() {
                        @Override
                        public void onSuccess(Headers headers, ApiResponse<List<cemara.labschool.id.rumahcemara.model.Event>> body) {
                            Loading.hide(getContext());
                            if (body != null && body.isStatus()) {
                                List<cemara.labschool.id.rumahcemara.model.Event> newsEvents=body.getData();
                                if (newsEvents.size() >= 3){
                                    for (int i=0; i<3; i++){
                                        newsList.add(new News(newsEvents.get(i).getId(),
                                                newsEvents.get(i).getEventCategoryId(),
                                                newsEvents.get(i).getTitle(),
                                                newsEvents.get(i).getUserCreator().getProfile().getFullname(),
                                                DateHelper.dateFormat(DateHelper.stringToDate(newsEvents.get(i).getCreatedAt())),
                                                newsEvents.get(i).getBanner(),
                                                newsEvents.get(i).getContent(),
                                                newsEvents.get(i).getUserCreator()));
                                    }
                                } else {
                                    for (int i=0; i<newsEvents.size(); i++){
                                        newsList.add(new News(newsEvents.get(i).getId(),
                                                newsEvents.get(i).getEventCategoryId(),
                                                newsEvents.get(i).getTitle(),
                                                newsEvents.get(i).getUserCreator().getProfile().getFullname(),
                                                DateHelper.dateFormat(DateHelper.stringToDate(newsEvents.get(i).getCreatedAt())),
                                                newsEvents.get(i).getBanner(),
                                                newsEvents.get(i).getContent(),
                                                newsEvents.get(i).getUserCreator()));
                                    }
                                }
                            }

                            newsAdapter = new NewsAdapter(getActivity(), eventsList, "home_news", "event");
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
//                            recyclerView.setAdapter(newsAdapter);
//                            newsAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailed(ErrorResponse error) {
                            Toast.makeText(getContext(),"Gagal Ambil Data", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCanceled() {

                        }
                    });

                    //newsList.add(new News("1", "testing", "test", "June 20 2019", R.drawable.img_news));

                    newsAdapter = new NewsAdapter(getActivity(), newsList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(newsAdapter);
//                    newsAdapter.notifyDataSetChanged();

                } else {
//                        loadingDialog.dismiss();
                    Toast.makeText(getContext(), Objects.requireNonNull(body).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(getContext());
                showDialogAlert(R.layout.dialog_bad_connection);
                TextView ok = dialog.findViewById(R.id.appointment_ok);
                ok.setOnClickListener(view -> dialog.dismiss());
            }

            @Override
            public void onCanceled() {
                Loading.hide(getContext());
            }
        });
    }

    private void showDialogAlert(int layout) {
        dialog = new Dialog(Objects.requireNonNull(getActivity()));
        //SET TITLE
        dialog.setTitle("");

        //set content
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void init() {

        btnHighlight.setOnClickListener(view -> {
            if (highlightItem.getVisibility() == View.VISIBLE) {
                highlightItem.setVisibility(View.GONE);
                btnHighlight.setImageResource(R.drawable.list_menu_icon_unselected);
            } else {
                highlightItem.setVisibility(View.VISIBLE);
                btnHighlight.setImageResource(R.drawable.list_menu_icon_selected);
            }
        });
    }

    private void showDialog(int layout) {
        dialog = new Dialog(Objects.requireNonNull(getContext()));
        //SET TITLE
        dialog.setTitle("");

        //set content
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

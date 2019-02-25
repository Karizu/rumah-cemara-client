package cemara.labschool.id.rumahcemara.home.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.rezkyatinnov.kyandroid.session.Session;
import com.rezkyatinnov.kyandroid.session.SessionObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.MainActivity;
import cemara.labschool.id.rumahcemara.api.AuthHelper;
import cemara.labschool.id.rumahcemara.api.NewsHelper;
import cemara.labschool.id.rumahcemara.auth.activity.LoginActivity;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.home.service.asktheexpert.AskTheExpertActivity;
import cemara.labschool.id.rumahcemara.home.highlight.article.ArticleActivity;
import cemara.labschool.id.rumahcemara.home.highlight.event.EventActivity;
import cemara.labschool.id.rumahcemara.home.highlight.news.NewsActivity;
import cemara.labschool.id.rumahcemara.home.service.behavioral.CounselingAppointmentActivity;
import cemara.labschool.id.rumahcemara.home.service.biomedical.BiomedicalAppointmentActivity;
import cemara.labschool.id.rumahcemara.home.service.structural.LegalCounselingAppointmentActivity;
import cemara.labschool.id.rumahcemara.home.service.structural.StructuralLegalAidActivity;
import cemara.labschool.id.rumahcemara.home.service.structural.StructuralViolationActivity;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.firebase.MyFirebaseMessagingService;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import cemara.labschool.id.rumahcemara.util.news.adapter.NewsAdapter;
import cemara.labschool.id.rumahcemara.util.news.model.News;
import cemara.labschool.id.rumahcemara.R;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.btn_highlight_menu)
    ImageView btnHighlight;
    @BindView(R.id.highlight_menu_item)
    LinearLayout highlightItem;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    List<News> newsList = new ArrayList<>();
    Dialog dialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getListNews();
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
                Loading.hide(getContext());
                if (body != null && body.isStatus()) {
                    List<cemara.labschool.id.rumahcemara.model.News> newsLists=body.getData();
                    Log.d("aa","sss");
                    for(int i=0;i<newsLists.size();i++){
                        newsList.add(new News(newsLists.get(i).getId(),
                                newsLists.get(i).getTitle(),
                                newsLists.get(i).getUserCreator().getProfile().getFullname(),
                                DateHelper.dateFormat(DateHelper.stringToDate(newsLists.get(i).getCreatedAt())),
                                newsLists.get(i).getBanner()));
                    }
                    //newsList.add(new News("1", "testing", "test", "June 20 2019", R.drawable.img_news));

                    newsAdapter = new NewsAdapter(getActivity(), newsList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();

                } else {
//                        loadingDialog.dismiss();
                    Toast.makeText(getContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Toast.makeText(getContext(),"Gagal Ambil Data", Toast.LENGTH_SHORT).show();
                Loading.hide(getContext());
            }

            @Override
            public void onCanceled() {
                Loading.hide(getContext());
            }
        });
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

    private void toast() {
        Toast toast = Toast.makeText(getContext(), "On Progress", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showDialog(int layout) {
        dialog = new Dialog(Objects.requireNonNull(getContext()));
        //SET TITLE
        dialog.setTitle("Biomedical");

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

}

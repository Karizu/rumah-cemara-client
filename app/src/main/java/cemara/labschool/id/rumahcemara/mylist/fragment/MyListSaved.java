package cemara.labschool.id.rumahcemara.mylist.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.list.reminder.adapter.ListReminderAdapter;
import cemara.labschool.id.rumahcemara.util.list.reminder.model.ListReminder;
import cemara.labschool.id.rumahcemara.util.list.saved.adapter.ListSavedAdapter;
import cemara.labschool.id.rumahcemara.util.list.saved.model.ListSaved;
import cemara.labschool.id.rumahcemara.util.news.adapter.NewsAdapter;
import cemara.labschool.id.rumahcemara.util.news.model.News;
import okhttp3.Headers;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyListSaved extends Fragment {

    @BindView(R.id.saved_list_recycler_view)
    RecyclerView recyclerView;
    ListSavedAdapter eventAdapter;
//    List<News> newsList = new ArrayList<>();

    private List<ListSaved> savedList;
    private Context activity;
    private LinearLayoutManager layoutManager;

    public MyListSaved() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.my_list_saved_fragment, container, false);
        ButterKnife.bind(this, rootView);
        activity=getActivity();
        layoutManager = new LinearLayoutManager(activity,
                LinearLayout.VERTICAL,
                false);

        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSavedNews();
    }

    private void getSavedNews() {
        Log.d("Saved","start");

        Loading.show(getContext());
        ListHelper.getListSaved(new RestCallback<ApiResponse<List<cemara.labschool.id.rumahcemara.model.ListSaved>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<cemara.labschool.id.rumahcemara.model.ListSaved>> body) {
                Loading.hide(getContext());
                if (body != null && body.isStatus()) {
                    List<cemara.labschool.id.rumahcemara.model.ListSaved> res = body.getData();
                    Log.d("Saved Size",String.valueOf(res.size()));
                    savedList = new ArrayList<>();

                    for (int i = 0; i < res.size(); i++) {
                        //savedList.add(new cemara.labschool.id.rumahcemara.util.event.model.Event());
                        if(res.get(i).getArticle()!=null){
                            savedList.add(
                                    new ListSaved(
                                            res.get(i).getArticle().getId()
                                            ,res.get(i).getArticle().getTitle()
                                            ,res.get(i).getArticle().getCreator()
                                            ,res.get(i).getArticle().getCreatedAt()
                                            ,res.get(i).getArticle().getIsBanner()
                                            ,res.get(i).getArticle().getBanner()
                                            ,true
                                    )
                            );
                        }else {
                            if (res.get(i).getNews()!=null){
                                savedList.add(
                                        new ListSaved(
                                                res.get(i).getNews().getId()
                                                ,res.get(i).getNews().getTitle()
                                                ,res.get(i).getNews().getCreator()
                                                ,res.get(i).getNews().getCreatedAt()
                                                ,res.get(i).getNews().getIsBanner()
                                                ,res.get(i).getNews().getBanner()
                                                ,false
                                        )
                                );
                            }
                        }
                    }
                    eventAdapter = new ListSavedAdapter(getContext(), savedList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(eventAdapter);
                    eventAdapter.notifyDataSetChanged();

                } else {
//                        loadingDialog.dismiss();
                    Toast.makeText(getContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(getContext());

                Toast.makeText(getContext(),"Gagal Ambil Data:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(),"Gagal Ambil Data1:" + error.getDescription(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled() {
                Loading.hide(getContext());
            }
        });
    }

}

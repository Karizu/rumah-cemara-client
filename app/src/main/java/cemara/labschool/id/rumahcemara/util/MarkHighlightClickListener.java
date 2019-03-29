package cemara.labschool.id.rumahcemara.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.ArrayList;
import java.util.List;

import cemara.labschool.id.rumahcemara.api.ArticleHelper;
import cemara.labschool.id.rumahcemara.api.EventHelper;
import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.api.NewsHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.model.Event;
import cemara.labschool.id.rumahcemara.model.ListReminder;
import cemara.labschool.id.rumahcemara.model.News;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.events.model.Events;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import io.realm.Realm;
import okhttp3.Headers;

public class MarkHighlightClickListener implements View.OnClickListener {
    private String typeId, type;
    private Context context;
    private int flag1 = 1;
    private String[] flag = new String[1];

    public MarkHighlightClickListener(Context context, String typeId) {
        this.context = context;
        this.typeId = typeId;
    }

    @Override
    public void onClick(View v) {
        // Use mView here if needed
        Loading.show(v.getContext());
        String type_id = typeId;
        Log.d("IDDD", type_id);

        checkNews();
        checkArticle();
        checkEvent();

    }

    private void checkBookmark(){
        ListHelper.getListSaved(new RestCallback<ApiResponse<List<cemara.labschool.id.rumahcemara.model.ListSaved>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<cemara.labschool.id.rumahcemara.model.ListSaved>> body) {
                Loading.hide(context);
                if (body != null && body.isStatus()) {
                    List<cemara.labschool.id.rumahcemara.model.ListSaved> res = body.getData();
                    Log.d("Saved Size",String.valueOf(res.size()));
                    if (res.size() != 0){
                        Log.d("aaa", "1");
                        for (int i = 0; i < res.size(); i++) {
                            Log.d("aaa", "2");
                            if (res.get(i).getNews()!=null){
                                Log.d("aaa", "3");
                                if(res.get(i).getNews().getId().equals(typeId)){
                                    flag1 = 0;
                                    Log.d("aaa", "11");
                                }
                            } else if (res.get(i).getArticle() != null){
                                Log.d("aaa", "4");
                                if(res.get(i).getArticle().getId().equals(typeId)){
                                    flag1 = 0;
                                    Log.d("aaa", "111");
                                }
                            } else {
                                ListHelper.getListReminder(new RestCallback<ApiResponse<List<ListReminder>>>() {
                                    @Override
                                    public void onSuccess(Headers headers, ApiResponse<List<ListReminder>> body) {
                                        Loading.hide(context);
                                        if (body != null && body.isStatus()) {
                                            List<cemara.labschool.id.rumahcemara.model.ListReminder> res = body.getData();
                                            Log.d("Saved Size",String.valueOf(res.size()));

                                            for (int i = 0; i < res.size(); i++) {
                                                if (res.get(i).getEvent()!=null){
                                                    if(res.get(i).getEvent().getId().equals(typeId)){
                                                        flag1 = 0;
                                                        Log.d("aaa", "33");
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailed(ErrorResponse error) {
                                        Loading.hide(context);
                                    }

                                    @Override
                                    public void onCanceled() {

                                    }
                                });
                            }
                        }
                    } else {
                        flag1 = 1;
                        Log.d("aaa", "55");
                    }

                    if (flag1 != 0){
                        createBookmark(type, typeId);
                    } else {
                        Toast.makeText(context, type +" sudah ada dalam list anda", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(context);
                Log.d("Masuk onFailed", error.getMessage());
//                Toast.makeText(mContext,"Gagal Ambil Data:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(),"Gagal Ambil Data1:" + error.getDescription(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled() {
                Loading.hide(context);
            }
        });
    }

    private void checkNews(){
        NewsHelper.getNews(new RestCallback<ApiResponse<List<News>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<News>> body) {
                Loading.hide(context);
                if (body != null && body.isStatus()) {
                    List<cemara.labschool.id.rumahcemara.model.News> newsLists = body.getData();
                    Log.d("aa", "sss");
                    Log.d("Size", String.valueOf(newsLists.size()));
                    for (int i = 0; i < newsLists.size(); i++) {
                        if (newsLists.get(i).getId().equals(typeId)) {
                            Log.d("News", "sss");
                            type = "News";
                            checkBookmark();
                        }
                    }
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(context);
            }

            @Override
            public void onCanceled() {

            }
        });
    }

    private void checkArticle(){
        ArticleHelper.getArticle(new RestCallback<ApiResponse<List<Article>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<Article>> body) {
                Loading.hide(context);
                if (body != null && body.isStatus()) {
                    List<cemara.labschool.id.rumahcemara.model.Article> newsArticles = body.getData();
                    Log.d("aa", "sss");
                    Log.d("Size", String.valueOf(newsArticles.size()));
                    for (int i = 0; i < newsArticles.size(); i++) {
                        if (newsArticles.get(i).getId().equals(typeId)) {
                            Log.d("Article", "sss");
                            type = "Article";
                            checkBookmark();
                        }
                    }
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(context);
            }

            @Override
            public void onCanceled() {

            }
        });
    }

    private void checkEvent(){
        EventHelper.getEvent(new RestCallback<ApiResponse<List<Event>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<Event>> body) {
                Loading.hide(context);
                if (body != null && body.isStatus()) {
                    List<cemara.labschool.id.rumahcemara.model.Event> newsEvents=body.getData();
                    Log.d("aa", "sss");
                    Log.d("Size", String.valueOf(newsEvents.size()));
                    for (int i = 0; i < newsEvents.size(); i++) {
                        if (newsEvents.get(i).getId().equals(typeId)) {
                            Log.d("Event", "sss");
                            type = "Event";
                            checkBookmark();
                        }
                    }
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(context);
            }

            @Override
            public void onCanceled() {

            }
        });
    }

    private void createBookmark(String type, String typeId){
        Realm realm = LocalData.getRealm();
        User user = realm.where(User.class).findFirst();
        assert user != null;
        ListHelper.postCreateUserList(
                user.getId(),
                type,
                typeId,
                null,
                new RestCallback<ApiResponse>() {
                    @Override
                    public void onSuccess(Headers headers, ApiResponse body) {
                        Loading.hide(context);
                        if (body != null && body.isStatus()) {
                            Toast.makeText(context, type+" Marked", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, body.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(ErrorResponse error) {
                        Loading.hide(context);
                        Toast.makeText(context, type+" Marked Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCanceled() {
                        Loading.hide(context);
                    }
                });
    }
}

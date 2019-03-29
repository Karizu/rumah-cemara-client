package cemara.labschool.id.rumahcemara.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.List;

import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.home.highlight.NewsDetailActivity;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.ListSaved;
import cemara.labschool.id.rumahcemara.model.News;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;

public class MarkNewsClickListener implements View.OnClickListener {
    private  News news;
    private Context context;
    private String newsId;
    private int flag1 = 1;
    private  cemara.labschool.id.rumahcemara.util.news.model.News newsUtil;

    public MarkNewsClickListener(Context context, News news)
    {
        this.context=context;
        this.newsUtil=null;
        this.news=news;
    }
    public MarkNewsClickListener(cemara.labschool.id.rumahcemara.util.news.model.News news)
    {
        this.newsUtil=news;
        this.news=null;
    }

    @Override
    public void onClick(View v) {
        // Use mView here if needed
        Loading.show(v.getContext());
        Realm realm = LocalData.getRealm();
        User user = realm.where(User.class).findFirst();
        if(newsUtil!=null){
            newsId=newsUtil.getNewsId();
        } else {
            newsId=news.getId();
        }

        checkBookmark(newsId);

    }

    private void checkBookmark(String newsId){
        ListHelper.getListSaved(new RestCallback<ApiResponse<List<ListSaved>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<cemara.labschool.id.rumahcemara.model.ListSaved>> body) {
                Loading.hide(context);
                if (body != null && body.isStatus()) {
                    List<cemara.labschool.id.rumahcemara.model.ListSaved> res = body.getData();
                    Log.d("Saved Size", String.valueOf(res.size()));
                    if (res.size() != 0) {
                        Log.d("aaa", "1");
                        for (int i = 0; i < res.size(); i++) {
                            Log.d("aaa", "2");
                            if (res.get(i).getNews() != null) {
                                Log.d("aaa", "3");
                                if (res.get(i).getNews().getId().equals(newsId)) {
                                    flag1 = 0;
                                    Log.d("aaa", "11");
                                }
                            }
                        }
                    } else {
                        flag1 = 1;
                        Log.d("aaa", "55");
                    }

                    if (flag1 != 0) {
                        createBookmark();
                    } else {
                        Toast.makeText(context, "News sudah ada dalam list anda", Toast.LENGTH_SHORT).show();
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

    private void createBookmark(){
        Realm realm = LocalData.getRealm();
        User user = realm.where(User.class).findFirst();
        if(newsUtil!=null){
            newsId=newsUtil.getNewsId();
        } else {
            newsId=news.getId();
        }

        ListHelper.postCreateUserList(
                user.getId(),
                "News",
                newsId,
                null,
                new RestCallback<ApiResponse>() {
                    @Override
                    public void onSuccess(Headers headers, ApiResponse body) {
                        Loading.hide(context);
                        if (body != null && body.isStatus()) {
                            Toast.makeText(context, "News Marked", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, body.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(ErrorResponse error) {
                        Loading.hide(context);
                        Toast.makeText(context,"News Marked Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCanceled() {
                        Loading.hide(context);
                    }
                });
    }
}




package cemara.labschool.id.rumahcemara.util;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.home.highlight.NewsDetailActivity;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.News;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;

public class MarkNewsClickListener implements View.OnClickListener {
    private  News news;
    private  cemara.labschool.id.rumahcemara.util.news.model.News newsUtil;

    public MarkNewsClickListener(News news)
    {
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

        String newsId;
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
                        Loading.hide(v.getContext());
                        if (body != null && body.isStatus()) {
                            Toast.makeText(v.getContext(), "News Marked", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(ErrorResponse error) {
                        Loading.hide(v.getContext());
                        Toast.makeText(v.getContext(),"News Marked Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCanceled() {
                        Loading.hide(v.getContext());
                    }
                });

    }
}




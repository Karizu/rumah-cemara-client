package cemara.labschool.id.rumahcemara.util;

import android.view.View;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;

public class MarkArticleClickListener implements View.OnClickListener {
    private  Article article;
    private  cemara.labschool.id.rumahcemara.util.article.model.Article articleUtil;
    public MarkArticleClickListener(Article article)
    {
        this.article=article;
        this.articleUtil=null;
    }
    public MarkArticleClickListener(cemara.labschool.id.rumahcemara.util.article.model.Article article)
    {
        this.articleUtil=article;
        this.article=null;
    }
    @Override
    public void onClick(View v) {
        // Use mView here if needed
        Loading.show(v.getContext());
        Realm realm = LocalData.getRealm();
        User user = realm.where(User.class).findFirst();

        String articleId;

        if(articleUtil!=null){
            articleId=articleUtil.getArticleId();
        } else {
            articleId=article.getId();
        }
        ListHelper.postCreateUserList(
                user.getId(),
                "Article",
                articleId,
                null,
                new RestCallback<ApiResponse>() {
                    @Override
                    public void onSuccess(Headers headers, ApiResponse body) {
                        Loading.hide(v.getContext());
                        if (body != null && body.isStatus()) {

                            Toast.makeText(v.getContext(), "Article Marked", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(ErrorResponse error) {
                        Loading.hide(v.getContext());
                        Toast.makeText(v.getContext(),"Article Marked Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCanceled() {
                        Loading.hide(v.getContext());
                    }
                });

    }
}




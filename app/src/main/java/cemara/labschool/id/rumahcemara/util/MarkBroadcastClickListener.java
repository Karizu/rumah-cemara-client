package cemara.labschool.id.rumahcemara.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.List;

import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.model.Broadcast;
import cemara.labschool.id.rumahcemara.model.ListSaved;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;

public class MarkBroadcastClickListener implements View.OnClickListener {
    private Broadcast article;
    private Context context;
    String broadcastId;
    private int flag1 = 1;
    private cemara.labschool.id.rumahcemara.util.broadcast.model.Broadcast articleUtil;

    public MarkBroadcastClickListener(Context context, Broadcast article) {
        this.context = context;
        this.article = article;
        this.articleUtil = null;
    }

    public MarkBroadcastClickListener(Context context, cemara.labschool.id.rumahcemara.util.broadcast.model.Broadcast article) {
        this.context = context;
        this.articleUtil = article;
        this.article = null;
    }

    @Override
    public void onClick(View v) {
        // Use mView here if needed
        Loading.show(v.getContext());
        Realm realm = LocalData.getRealm();
        User user = realm.where(User.class).findFirst();

        if (articleUtil != null) {
            broadcastId = articleUtil.getBroadcastId();
        } else {
            broadcastId = article.getId();
        }

        checkBookmark(broadcastId);

    }

    private void checkBookmark(String articleId) {
        ListHelper.getListSaved(new RestCallback<ApiResponse<List<ListSaved>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<ListSaved>> body) {
                Loading.hide(context);
                try {
                    if (body != null && body.isStatus()) {
                        List<ListSaved> res = body.getData();
                        Log.d("Saved Size", String.valueOf(res.size()));
                        if (res.size() != 0) {
                            Log.d("aaa", "1");
                            for (int i = 0; i < res.size(); i++) {
                                Log.d("aaa", "2");
                                if (res.get(i).getArticle() != null) {
                                    Log.d("aaa", "3");
                                    if (res.get(i).getArticle().getId().equals(articleId)) {
                                        flag1 = 0;
                                        Log.d("aaa", "11");
                                    }
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
                        Toast.makeText(context, " Sayembara sudah ada dalam list anda", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
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

        if (articleUtil != null) {
            broadcastId = articleUtil.getBroadcastId();
        } else {
            broadcastId = article.getId();
        }
        assert user != null;
        ListHelper.postCreateUserList(
                user.getId(),
                "Broadcast",
                broadcastId,
                null,
                new RestCallback<ApiResponse>() {
                    @Override
                    public void onSuccess(Headers headers, ApiResponse body) {
                        Loading.hide(context);
                        try {
                            if (body.isStatus()) {
                                Toast.makeText(context, "Sayembara Marked", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, body.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailed(ErrorResponse error) {
                        Loading.hide(context);
                        Toast.makeText(context, "Sayembara Marked Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCanceled() {
                        Loading.hide(context);
                    }
                });
    }
}




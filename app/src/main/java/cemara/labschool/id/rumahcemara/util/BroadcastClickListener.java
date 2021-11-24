package cemara.labschool.id.rumahcemara.util;

import android.content.Intent;
import android.view.View;

import cemara.labschool.id.rumahcemara.home.highlight.ArticleDetailActivity;
import cemara.labschool.id.rumahcemara.home.highlight.BroadcastDetailActivity;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.model.Broadcast;

public class BroadcastClickListener implements View.OnClickListener {
    private Broadcast article;
    public BroadcastClickListener(Broadcast article)
    {
        this.article=article;
    }
    @Override
    public void onClick(View v) {
        // Use mView here if needed
        Intent intent = new Intent(v.getContext(), BroadcastDetailActivity.class);
        intent.putExtra("id",article.getId());
        v.getContext().startActivity(intent);

    }
}




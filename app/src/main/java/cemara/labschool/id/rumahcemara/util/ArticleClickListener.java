package cemara.labschool.id.rumahcemara.util;

import android.content.Intent;
import android.view.View;

import cemara.labschool.id.rumahcemara.home.highlight.ArticleDetailActivity;
import cemara.labschool.id.rumahcemara.home.highlight.NewsDetailActivity;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.model.News;

public class ArticleClickListener implements View.OnClickListener {
    private Article article;
    public ArticleClickListener(Article article)
    {
        this.article=article;
    }
    @Override
    public void onClick(View v) {
        // Use mView here if needed
        Intent intent = new Intent(v.getContext(), ArticleDetailActivity.class);
        intent.putExtra("id",article.getId());
        v.getContext().startActivity(intent);

    }
}




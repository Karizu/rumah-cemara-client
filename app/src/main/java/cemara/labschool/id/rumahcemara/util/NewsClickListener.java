package cemara.labschool.id.rumahcemara.util;

import android.content.Intent;
import android.view.View;

import cemara.labschool.id.rumahcemara.MainActivity;
import cemara.labschool.id.rumahcemara.home.highlight.NewsDetailActivity;
import cemara.labschool.id.rumahcemara.model.News;

public class NewsClickListener  implements View.OnClickListener {
    private  News news;
    public NewsClickListener(News news)
    {
        this.news=news;
    }
    @Override
    public void onClick(View v) {
        // Use mView here if needed
        Intent intent = new Intent(v.getContext(), NewsDetailActivity.class);
        intent.putExtra("id",news.getId());
        v.getContext().startActivity(intent);

    }
}




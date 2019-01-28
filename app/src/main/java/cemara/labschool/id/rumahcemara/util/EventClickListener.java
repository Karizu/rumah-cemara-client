package cemara.labschool.id.rumahcemara.util;

import android.content.Intent;
import android.view.View;

import cemara.labschool.id.rumahcemara.home.highlight.EventDetailActivity;
import cemara.labschool.id.rumahcemara.model.Event;
import cemara.labschool.id.rumahcemara.model.Event;

public class EventClickListener implements View.OnClickListener {
    private Event event;
    public EventClickListener(Event event)
    {
        this.event=event;
    }
    @Override
    public void onClick(View v) {
        // Use mView here if needed
        Intent intent = new Intent(v.getContext(), EventDetailActivity.class);
        intent.putExtra("id",event.getId());
        v.getContext().startActivity(intent);

    }
}




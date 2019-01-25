package cemara.labschool.id.rumahcemara.model.response;

import cemara.labschool.id.rumahcemara.model.OutreachLocationData;

public class OutreachNearMeResponse extends OutreachLocationDataResponse {

    private OutreachLocationData outreachLocationData;

    public OutreachLocationData getOutreachLocationData() {
        return outreachLocationData;
    }

    public void setOutreachLocationData(OutreachLocationData outreachLocationData) {
        this.outreachLocationData = outreachLocationData;
    }
}

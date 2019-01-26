package cemara.labschool.id.rumahcemara.model.response;

import cemara.labschool.id.rumahcemara.model.OutreachLocationData;
import cemara.labschool.id.rumahcemara.model.ProviderLocationData;

public class ProviderNearMeResponse extends OutreachLocationDataResponse {

    private ProviderLocationData providerLocationData;

    public ProviderLocationData getProviderLocationData() {
        return providerLocationData;
    }

    public void setProviderLocationData(ProviderLocationData providerLocationData) {
        this.providerLocationData = providerLocationData;
    }
}
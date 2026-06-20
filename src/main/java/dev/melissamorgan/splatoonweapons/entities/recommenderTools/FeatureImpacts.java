package dev.melissamorgan.splatoonweapons.entities.recommenderTools;

import java.util.List;

public class FeatureImpacts {
    private List<String> advantages;
    private List<String> deficits;

    public FeatureImpacts() {
    }

    public List<String> getAdvantages() {
        return advantages;
    }

    public void setAdvantages(List<String> advantages) {
        this.advantages = advantages;
    }

    public List<String> getDeficits() {
        return deficits;
    }

    public void setDeficits(List<String> deficits) {
        this.deficits = deficits;
    }
}

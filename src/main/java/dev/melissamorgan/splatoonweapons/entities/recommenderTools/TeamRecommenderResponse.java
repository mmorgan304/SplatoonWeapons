package dev.melissamorgan.splatoonweapons.entities.recommenderTools;

import java.util.List;
import java.util.Map;

public class TeamRecommenderResponse {
    private Map<String, List<String>> recommendedTeam;
    private FeatureImpacts features;
    private double projectedWinRate;
    private String explanation;
    private Map<String, String> weaponImages;

    public Map<String, List<String>> getRecommendedTeam() {
        return recommendedTeam;
    }

    public void setRecommendedTeam(Map<String, List<String>> recommendedTeam) {
        this.recommendedTeam = recommendedTeam;
    }

    public FeatureImpacts getFeatures() {
        return features;
    }

    public void setFeatures(FeatureImpacts features) {
        this.features = features;
    }

    public double getProjectedWinRate() {
        return projectedWinRate;
    }

    public void setProjectedWinRate(double projectedWinRate) {
        this.projectedWinRate = projectedWinRate;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Map<String, String> getWeaponImages() {
        return weaponImages;
    }

    public void setWeaponImages(Map<String, String> weaponImages) {
        this.weaponImages = weaponImages;
    }
}
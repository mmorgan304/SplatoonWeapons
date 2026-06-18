package dev.melissamorgan.splatoonweapons.entities.recommenderTools;

import java.util.ArrayList;
import java.util.List;

public class TeamRecommenderResponse {
    private TeamWeaponPool recommendedTeam;
    private double predictedWinRate;
    private List<String> features;
    private String explanation;

    public TeamRecommenderResponse() {

    }

    public TeamRecommenderResponse(TeamWeaponPool recommendedTeam, List<String> features, String explanation) {
        this.recommendedTeam = recommendedTeam;
        this.features = features;
        this.explanation = explanation;
    }

    public TeamWeaponPool getRecommendedTeam() {
        return recommendedTeam;
    }

    public void setRecommendedTeam(TeamWeaponPool recommendedTeam) {
        this.recommendedTeam = recommendedTeam;
    }

    public double getPredictedWinRate() {
        return predictedWinRate;
    }

    public void setPredictedWinRate(double predictedWinRate) {
        this.predictedWinRate = predictedWinRate;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}

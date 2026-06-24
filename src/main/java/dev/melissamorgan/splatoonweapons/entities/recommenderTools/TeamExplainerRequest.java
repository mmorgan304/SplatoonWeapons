package dev.melissamorgan.splatoonweapons.entities.recommenderTools;

import java.util.List;
import java.util.Map;

public class TeamExplainerRequest {
    private Map<String, List<String>> recommendedTeam;
    private List<String> bravoTeam;
    private List<String> advantages;
    private List<String> deficits;
    private double projectedWinRate;

    public TeamExplainerRequest() {
    }

    // Getters and Setters
    public Map<String, List<String>> getRecommendedTeam() { return recommendedTeam; }
    public void setRecommendedTeam(Map<String, List<String>> recommendedTeam) { this.recommendedTeam = recommendedTeam; }

    public List<String> getBravoTeam() { return bravoTeam; }
    public void setBravoTeam(List<String> bravoTeam) { this.bravoTeam = bravoTeam; }

    public List<String> getAdvantages() { return advantages; }
    public void setAdvantages(List<String> advantages) { this.advantages = advantages; }

    public List<String> getDeficits() { return deficits; }
    public void setDeficits(List<String> deficits) { this.deficits = deficits; }

    public double getProjectedWinRate() { return projectedWinRate; }
    public void setProjectedWinRate(double projectedWinRate) { this.projectedWinRate = projectedWinRate; }
}

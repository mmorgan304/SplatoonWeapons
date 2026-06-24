package dev.melissamorgan.splatoonweapons.entities.recommenderTools;

import java.util.List;
import java.util.Map;

public class TeamExplainerResponse {
    private String justificationParagraph;
    private List<String> rephrasedAdvantages;
    private List<String> rephrasedDeficits;
    private Map<String, String> hoverRoles;
    private double generationTimeSeconds;

    // Getters and Setters
    public String getJustificationParagraph() { return justificationParagraph; }
    public void setJustificationParagraph(String justificationParagraph) { this.justificationParagraph = justificationParagraph; }

    public List<String> getRephrasedAdvantages() { return rephrasedAdvantages; }
    public void setRephrasedAdvantages(List<String> rephrasedAdvantages) { this.rephrasedAdvantages = rephrasedAdvantages; }

    public List<String> getRephrasedDeficits() { return rephrasedDeficits; }
    public void setRephrasedDeficits(List<String> rephrasedDeficits) { this.rephrasedDeficits = rephrasedDeficits; }

    public Map<String, String> getHoverRoles() { return hoverRoles; }
    public void setHoverRoles(Map<String, String> hoverRoles) { this.hoverRoles = hoverRoles; }

    public double getGenerationTimeSeconds() { return generationTimeSeconds; }
    public void setGenerationTimeSeconds(double generationTimeSeconds) { this.generationTimeSeconds = generationTimeSeconds; }
}
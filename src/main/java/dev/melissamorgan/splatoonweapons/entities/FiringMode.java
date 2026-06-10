package dev.melissamorgan.splatoonweapons.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiringMode implements Serializable {

    @JsonProperty("firingModeName")
    private String firingModeName;

    @JsonProperty("maxDamage")
    private Double maxDamage;

    @JsonProperty("minDamage")
    private Double minDamage;

    @JsonProperty("windupFrames")
    private Integer windupFrames;

    @JsonProperty("actionIntervalFrames")
    private Integer actionIntervalFrames;

    @JsonProperty("inkEfficiency")
    private Double inkEfficiency;

    @JsonProperty("effectiveRange")
    private Double effectiveRange;

    @JsonProperty("hasOneshot")
    private Boolean hasOneshot;

    public FiringMode() {
    }

    // Getters and Setters
    public String getFiringModeName() {
        return firingModeName;
    }

    public void setFiringModeName(String firingModeName) {
        this.firingModeName = firingModeName;
    }

    public Double getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(Double maxDamage) {
        this.maxDamage = maxDamage;
    }

    public Double getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(Double minDamage) {
        this.minDamage = minDamage;
    }

    public Integer getWindupFrames() {
        return windupFrames;
    }

    public void setWindupFrames(Integer windupFrames) {
        this.windupFrames = windupFrames;
    }

    public Integer getActionIntervalFrames() {
        return actionIntervalFrames;
    }

    public void setActionIntervalFrames(Integer actionIntervalFrames) {
        this.actionIntervalFrames = actionIntervalFrames;
    }

    public Double getEffectiveRange() {
        return effectiveRange;
    }

    public void setEffectiveRange(Double effectiveRange) {
        this.effectiveRange = effectiveRange;
    }

    public Double getInkEfficiency() {
        return inkEfficiency;
    }

    public void setInkEfficiency(Double inkEfficiency) {
        this.inkEfficiency = inkEfficiency;
    }

    public Boolean getHasOneshot() {
        return hasOneshot;
    }

    public void setHasOneshot(Boolean hasOneshot) {
        this.hasOneshot = hasOneshot;
    }
}
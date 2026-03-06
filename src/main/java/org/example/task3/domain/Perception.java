package org.example.task3.domain;
// Восприятие и осознание персонажа
public class Perception {
    private String sensation;
    private String realization;
    private boolean isIllusion;

    public Perception() {
        this.sensation = "";
        this.realization = "";
        this.isIllusion = false;
    }

    public void setSensation(String sensation) {
        this.sensation = sensation;
    }

    public void setRealization(String realization) {
        this.realization = realization;
    }

    public void setIllusion(boolean illusion) {
        this.isIllusion = illusion;
    }

    public String getSensation() {
        return sensation;
    }

    public String getRealization() {
        return realization;
    }

    public boolean isIllusion() {
        return isIllusion;
    }

    public boolean hasRealized() {
        return !realization.isEmpty();
    }

    @Override
    public String toString() {
        return String.format(
                "Perception{sensation='%s', realization='%s', illusion=%s}", sensation, realization, isIllusion);
    }
}

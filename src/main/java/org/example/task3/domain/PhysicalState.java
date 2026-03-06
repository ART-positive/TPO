package org.example.task3.domain;

import org.jetbrains.annotations.NotNull;
// Физическое состояние персонажа

public record PhysicalState(boolean isBodiless, boolean hasHair, boolean hairMoving, String sensationType) {

    public boolean canFeelHairMovement() {
        return hasHair && hairMoving;
    }

    @NotNull
    @Override
    public String toString() {
        return String.format("PhysicalState{bodiless=%s, hair=%s, moving=%s}", isBodiless, hasHair, hairMoving);
    }
}

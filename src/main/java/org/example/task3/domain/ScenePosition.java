package org.example.task3.domain;

import org.jetbrains.annotations.NotNull;
// Позиция персонажа в сцене

public record ScenePosition(String target, Speed speed, boolean isMoving) {

    @NotNull
    @Override
    public String toString() {
        return String.format("ScenePosition{target=%s, speed=%s, moving=%s}", target, speed, isMoving);
    }
}

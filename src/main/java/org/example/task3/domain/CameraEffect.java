package org.example.task3.domain;
// Эффект камеры
public record CameraEffect(String type, boolean isRealMovement) implements SceneEvent {

    public boolean isIllusion() {
        return !isRealMovement;
    }

    @Override
    public void execute() {
        System.out.println("Эффект камеры: " + type);
    }

    @Override
    public String getDescription() {
        return String.format("CameraEffect{type=%s, real=%s}", type, isRealMovement);
    }
}

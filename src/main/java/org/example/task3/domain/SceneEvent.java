package org.example.task3.domain;
// Базовый интерфейс для событий сцены
public interface SceneEvent {
    void execute();

    String getDescription();
}

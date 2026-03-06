package org.example.task3.domain;

import java.util.ArrayList;
import java.util.List;
// Сцена - последовательность событий

public class Scene {
    private final List<SceneEvent> events;
    private final List<String> executionLog;
    private final Person mainPerson;

    public Scene(Person mainPerson) {
        this.mainPerson = mainPerson;
        this.events = new ArrayList<>();
        this.executionLog = new ArrayList<>();
    }

    public void addEvent(SceneEvent event) {
        events.add(event);
    }

    public void execute() {
        executionLog.clear();
        for (SceneEvent event : events) {
            event.execute();
            executionLog.add(event.getDescription());
        }
    }

    public List<String> getExecutionLog() {
        return new ArrayList<>(executionLog);
    }

    public Person getMainPerson() {
        return mainPerson;
    }
}

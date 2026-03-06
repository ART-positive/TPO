package org.example.task3.events;

import org.example.task3.domain.Person;
import org.example.task3.domain.SceneEvent;
// Событие: осознание иллюзии

public class RealizationEvent implements SceneEvent {
    private final Person person;
    private final String realization;

    public RealizationEvent(Person person, String realization) {
        this.person = person;
        this.realization = realization;
    }

    @Override
    public void execute() {
        person.realize(realization);
        person.getPerception().setIllusion(true);
    }

    @Override
    public String getDescription() {
        return String.format("Realization: %s осознал: %s", person.getName(), realization);
    }
}

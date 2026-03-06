package org.example.task3.events;

import org.example.task3.domain.Person;
import org.example.task3.domain.SceneEvent;
import org.example.task3.domain.ScenePosition;
// Событие: движение персонажа к цели

public class MovementEvent implements SceneEvent {
    private final Person person;
    private final ScenePosition newPosition;

    public MovementEvent(Person person, ScenePosition newPosition) {
        this.person = person;
        this.newPosition = newPosition;
    }

    @Override
    public void execute() {
        person.setPosition(newPosition);
    }

    @Override
    public String getDescription() {
        return String.format("Movement: %s → %s (%s)", person.getName(), newPosition.target(), newPosition.speed());
    }
}

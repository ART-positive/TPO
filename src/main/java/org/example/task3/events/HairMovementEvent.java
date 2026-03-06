package org.example.task3.events;

import org.example.task3.domain.Person;
import org.example.task3.domain.PhysicalState;
import org.example.task3.domain.SceneEvent;
// Событие: ощущение движения волос

public class HairMovementEvent implements SceneEvent {
    private final Person person;
    private final PhysicalState newState;

    public HairMovementEvent(Person person, PhysicalState newState) {
        this.person = person;
        this.newState = newState;
    }

    @Override
    public void execute() {
        person.setPhysicalState(newState);
        person.getPerception().setSensation("зашевелились волосы на бесплотной голове");
    }

    @Override
    public String getDescription() {
        return "HairMovement: " + person.getName();
    }
}

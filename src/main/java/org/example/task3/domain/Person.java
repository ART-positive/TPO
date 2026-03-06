package org.example.task3.domain;
// Персонаж сцены с физическим состоянием и восприятием
public class Person {
    private final String name;
    private PhysicalState physicalState;
    private ScenePosition position;
    private Perception perception;

    public Person(String name) {
        this.name = name;
        this.physicalState = new PhysicalState(false, false, false, "NONE");
        this.position = new ScenePosition(null, Speed.STILL, false);
        this.perception = new Perception();
    }

    public String getName() {
        return name;
    }

    public PhysicalState getPhysicalState() {
        return physicalState;
    }

    public void setPhysicalState(PhysicalState state) {
        this.physicalState = state;
    }

    public ScenePosition getPosition() {
        return position;
    }

    public void setPosition(ScenePosition position) {
        this.position = position;
    }

    public Perception getPerception() {
        return perception;
    }

    public void setPerception(Perception perception) {
        this.perception = perception;
    }

    public void realize(String realization) {
        perception.setRealization(realization);
    }

    @Override
    public String toString() {
        return String.format("Person{name='%s', state=%s}", name, physicalState);
    }
}

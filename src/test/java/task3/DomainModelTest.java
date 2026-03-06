package task3;

import static org.junit.jupiter.api.Assertions.*;

import org.example.task3.domain.*;
import org.example.task3.events.HairMovementEvent;
import org.example.task3.events.MovementEvent;
import org.example.task3.events.RealizationEvent;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DomainModelTest {

    @Nested
    class CharacterTests {

        @Test
        void testCharacterCreation() {
            Person arthur = new Person("Артур");
            assertEquals("Артур", arthur.getName());
            assertNotNull(arthur.getPhysicalState());
            assertFalse(arthur.getPhysicalState().isBodiless());
        }

        @Test
        void testSetPhysicalState() {
            Person arthur = new Person("Артур");
            PhysicalState state = new PhysicalState(true, true, false, "HAIR");
            arthur.setPhysicalState(state);

            assertTrue(arthur.getPhysicalState().isBodiless());
            assertTrue(arthur.getPhysicalState().hasHair());
        }

        @Test
        void testSetBodiless() {
            Person arthur = new Person("Артур");
            PhysicalState state = new PhysicalState(true, true, true, "HAIR_MOVEMENT");
            arthur.setPhysicalState(state);

            assertTrue(arthur.getPhysicalState().isBodiless());
            assertTrue(arthur.getPhysicalState().canFeelHairMovement());
        }
    }

    @Nested
    class PhysicalStateTests {

        @Test
        void testPhysicalStateCreation() {
            PhysicalState state = new PhysicalState(true, true, true, "HAIR_MOVEMENT");

            assertTrue(state.isBodiless());
            assertTrue(state.hasHair());
            assertTrue(state.hairMoving());
        }

        @Test
        void testHairMovement() {
            PhysicalState state = new PhysicalState(false, true, true, "HAIR");
            assertTrue(state.canFeelHairMovement());
        }

        @Test
        void testBodilessWithHair() {
            PhysicalState state = new PhysicalState(true, true, true, "PARADOX");

            assertTrue(state.isBodiless());
            assertTrue(state.hasHair());
            assertTrue(state.canFeelHairMovement());
        }
    }

    @Nested
    class ScenePositionTests {

        @Test
        void testScenePositionCreation() {
            ScenePosition pos = new ScenePosition("пульт", Speed.SLOWLY, true);

            assertEquals("пульт", pos.target());
            assertEquals(Speed.SLOWLY, pos.speed());
            assertTrue(pos.isMoving());
        }

        @Test
        void testMovementSpeed() {
            ScenePosition slow = new ScenePosition("пульт", Speed.SLOWLY, true);
            ScenePosition steady = new ScenePosition("пульт", Speed.STEADILY, true);

            assertEquals(Speed.SLOWLY, slow.speed());
            assertEquals(Speed.STEADILY, steady.speed());
        }
    }

    @Nested
    class CameraEffectTests {

        @Test
        void testCameraEffectCreation() {
            CameraEffect effect = new CameraEffect("наезд", false);

            assertEquals("наезд", effect.type());
            assertFalse(effect.isRealMovement());
            assertTrue(effect.isIllusion());
        }

        @Test
        void testZoomInEffect() {
            CameraEffect effect = new CameraEffect("наезд", false);
            assertEquals("наезд", effect.type());
        }

        @Test
        void testIsIllusion() {
            CameraEffect illusion = new CameraEffect("наезд", false);
            CameraEffect real = new CameraEffect("движение", true);

            assertTrue(illusion.isIllusion());
            assertFalse(real.isIllusion());
        }
    }

    @Nested
    class PerceptionTests {

        @Test
        void testPerceptionCreation() {
            Perception perception = new Perception();

            assertEquals("", perception.getSensation());
            assertEquals("", perception.getRealization());
            assertFalse(perception.isIllusion());
        }

        @Test
        void testSensation() {
            Perception perception = new Perception();
            perception.setSensation("зашевелились волосы");

            assertEquals("зашевелились волосы", perception.getSensation());
        }

        @Test
        void testRealization() {
            Perception perception = new Perception();
            perception.setRealization("наезд камеры при съёмке");

            assertEquals("наезд камеры при съёмке", perception.getRealization());
            assertTrue(perception.hasRealized());
        }
    }

    @Nested
    class SceneTests {

        @Test
        void testSceneExecution() {
            Person arthur = new Person("Артур");
            Scene scene = new Scene(arthur);

            PhysicalState state = new PhysicalState(true, true, true, "HAIR_MOVEMENT");
            scene.addEvent(new HairMovementEvent(arthur, state));

            ScenePosition pos = new ScenePosition("пульт", Speed.SLOWLY, true);
            scene.addEvent(new MovementEvent(arthur, pos));

            scene.execute();

            assertTrue(arthur.getPhysicalState().hairMoving());
            assertTrue(arthur.getPosition().isMoving());
            assertEquals(2, scene.getExecutionLog().size());
        }

        @Test
        void testIllusionRevelation() {
            Person arthur = new Person("Артур");
            Scene scene = new Scene(arthur);

            scene.addEvent(new RealizationEvent(arthur, "наезд камеры при съёмке"));
            scene.execute();

            assertTrue(arthur.getPerception().isIllusion());
            assertTrue(arthur.getPerception().hasRealized());
            assertEquals("наезд камеры при съёмке", arthur.getPerception().getRealization());
        }
    }

    @Test
    void testFullTextScenario() {
        Person arthur = new Person("Артур");
        Scene scene = new Scene(arthur);

        // Артур почувствовал движение волос на бесплотной голове
        PhysicalState state = new PhysicalState(true, true, true, "HAIR_MOVEMENT");
        scene.addEvent(new HairMovementEvent(arthur, state));

        // Начал медленно двигаться к пульту
        ScenePosition pos = new ScenePosition("пульт", Speed.SLOWLY, true);
        scene.addEvent(new MovementEvent(arthur, pos));

        // Осознал, что это наезд камеры
        scene.addEvent(new RealizationEvent(arthur, "наезд камеры при съёмке"));

        scene.execute();

        // Проверка состояний
        assertTrue(arthur.getPhysicalState().isBodiless());
        assertTrue(arthur.getPhysicalState().hasHair());
        assertTrue(arthur.getPhysicalState().hairMoving());
        assertTrue(arthur.getPosition().isMoving());
        assertEquals("пульт", arthur.getPosition().target());
        assertTrue(arthur.getPerception().isIllusion());
        assertTrue(arthur.getPerception().hasRealized());
        assertEquals(3, scene.getExecutionLog().size());
    }
}

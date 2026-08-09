package ca.ciccc.silverBullet.playerElements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ciccc.silverBullet.enums.gameplay.Directions;
import ca.ciccc.silverBullet.testsupport.JavaFxToolkit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Tests for a player's ammo counter ({@code addShot} / {@code isHasShot}). */
class PlayerAmmoTest {

    @BeforeAll
    static void startJavaFx() throws InterruptedException {
        JavaFxToolkit.init();
    }

    private static Player playerWithShots(int shots) {
        return new Player(shots, 1, 0, 0, Directions.NORTH, null);
    }

    @Test
    void aPlayerWithZeroShotsHasNoAmmo() {
        Player player = playerWithShots(0);
        assertEquals(0, player.getNumberOfShots());
        assertFalse(player.isHasShot());
    }

    @Test
    void aPlayerWithShotsHasAmmo() {
        Player player = playerWithShots(2);
        assertEquals(2, player.getNumberOfShots());
        assertTrue(player.isHasShot());
    }

    @Test
    void addShotIncrementsAmmo() {
        Player player = playerWithShots(0);

        player.addShot();

        assertEquals(1, player.getNumberOfShots());
        assertTrue(player.isHasShot());
    }

    @Test
    void addShotIsCappedAtThree() {
        Player player = playerWithShots(2);

        player.addShot(); // 3
        player.addShot(); // capped
        player.addShot(); // capped

        assertEquals(3, player.getNumberOfShots());
    }
}

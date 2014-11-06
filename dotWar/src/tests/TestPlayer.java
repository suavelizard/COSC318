package tests;

import junit.framework.TestCase;
import client.Player;
import org.junit.Test;

/**
 * Created by Zane on 2014-11-06.
 */
public class TestPlayer extends TestCase{
    Player player;
    protected void setUp() {
       player = new Player();
    }
    @Test
    public void testPlayerHealth(){
        assertTrue(player.getHealth() == 100);
    }
    @Test
    public void testPlayerMoveSpeed(){
        assertTrue(player.getMoveSpeed() == 10);
    }
}

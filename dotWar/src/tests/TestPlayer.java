package tests;

import junit.framework.TestCase;
import client.Player;
/**
 * Created by Zane on 2014-11-06.
 */
public class TestPlayer extends TestCase{
    protected void setUp() { player = new Player();
    }

    public void testNewCellIsDead() {
        assertTrue(!cell.isAlive());
    }
}

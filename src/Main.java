/**
 * Created by ekalawen on 01/09/17.
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {
    public static void main (String[] args) throws SlickException {
        System.out.println("Hello Clicker !!!");
        System.out.println("Hello SLICK2D !!!");
        new AppGameContainer(new WindowGame(), 640, 480, false).start();
        System.out.println("Bye SLICK2D !!!");
    }
}

/**
 * Created by ekalawen on 01/09/17.
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {
    public static void main (String[] args) throws SlickException {

        WindowGame windows = new WindowGame();
        new AppGameContainer(windows,
                windows.getWindowsHeight(),
                windows.getWindowsWidth(),
                false).start();

    }
}

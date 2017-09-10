/**
 * Created by ekalawen on 01/09/17.
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

    private final static int FRAME_RATE = 120;

    public static void main (String[] args) throws SlickException {


        WindowGame windows = new WindowGame();
        AppGameContainer app = new AppGameContainer(windows,
                windows.getWindowsWidth(),
                windows.getWindowsHeight(),
                false);
        app.setTargetFrameRate(FRAME_RATE);
        app.start();

    }
}

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class CounterText {
    /* Gère et affiche le texte au dessus de l'animation du counter */

    /*Attributs*/
    private int nbPixels; //Nombre de pixels possédés
    private int pps; //Nombre de pixels gagnés chaque seconde
    private int lastPps, freqPps; // la dernière fois qu'on a gagné des pixels, la fréquenc à laquelle on les gagnes
    private int ppc; //Nombre de pixels gagnés par clic
    private int y; //Hauteur du placement du texte
    private float size; //facteur d'agrandissement de l'animation

    /*Constructeur*/
    public CounterText(int nbPixelsDepart, int pps, int ppc, int y, float size){
        this.nbPixels = nbPixelsDepart;
        this.pps = pps ;
        this.lastPps = 0;
        this.freqPps = 1000;
        this.ppc = ppc ;
        this.y = y;
        this.size = size;
    }
    public void renderText(Graphics g, WindowGame windows) throws SlickException {
        /*
        String s = "Pixels : " + nbPixels;
        Font font = g.getFont();
        int xString = windows.getWindowsWidth() / 2 - font.getWidth(s) / 2;
        int yString = windows.getWindowsHeight() / 2 - font.getHeight(s) * 2 - (int) (animation.getHeight() * size + size / 20) / 2;
        g.setColor(new Color(255, 255, 255));
        g.setFont(font);
        g.drawString(s, xString, yString);
        */
        /* TESTS SUR LES FONDS */
        /* GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {

            ge.registerFont(java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("resources/fonts/pixelhole/pixelhole.ttf")));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] fonts = ge.getAvailableFontFamilyNames();
        for (String font : fonts) {
            System.out.println(font);
            if (font.equals("Pixelhole")){
                String fontNbPixels = font;
            }

        } */

        String s = "Pixels : " + this.nbPixels;
        Font pixelFont = g.getFont();
        /*
        UnicodeFont pixelFont = new UnicodeFont("resources/fonts/pixelhole/pixelhole.ttf", 20, false, false);

        pixelFont.addAsciiGlyphs();
        pixelFont.addGlyphs(400, 600);
        pixelFont.getEffects().add(new ColorEffect());
        pixelFont.loadGlyphs();
        */

        int xString = windows.getWindowsWidth() / 2 - pixelFont.getWidth(s) / 2;
        int yString = windows.getWindowsHeight() / 2 - pixelFont.getHeight(s) * 2 - (int) (y * size + size / 20) / 2;
        //g.setColor(new Color(175, 175, 175));
        g.setFont(pixelFont);
        g.drawString(s, xString, yString);

       /* FIN DES TESTS */
    }


    public void updatePixels(int delta) {
        lastPps += delta;
        if(lastPps > freqPps) {
            nbPixels += pps;
            lastPps = 0;
        }
    }

    public int getNbPixels() {
        return nbPixels;
    }

    public void setNbPixels(int nbPixels) {
        this.nbPixels = nbPixels;
    }

    public int getPps() {
        return pps;
    }

    public void setPps(int pps) {
        this.pps = pps;
    }

    public int getPpc() {
        return ppc;
    }

    public void setPpc(int ppc) {
        this.ppc = ppc;
    }

    /* Met à jour le nombre total de pixels gagnés grace au clic */
    public void activer(){
        setNbPixels(this.getNbPixels() + this.getPpc());
    }

    /* Met à jour le nombre total de pixels gagnés grace au pps */
    public void incrementer(){
        setNbPixels(this.getNbPixels() + this.getPps());
    }

}

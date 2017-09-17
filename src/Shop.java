import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.min;
import static java.lang.Math.nextAfter;

public class Shop {

    /* Attributs */

    // Model
    private List<ProduitBot> produitsBots; // Liste des produits de Bots que contient la boutique;
    private List<ProduitUpgrade> produitsUpgrades; // Liste des produits d'Upgrades que contient la boutique;

    // Affichage
    // Background
    private Rectangle pos; // la position du shop
    private int sizeRect; // taille en pixel des carrés de subdivision du background du shop
    private List<Rectangle> quadrillage; // la quadrillage du background
    private List<Color> quadrillageColor; // les couleurs des carrés du quadrillage du background
    private float backgroundSaturation; // la saturation du background
    private float backgroundLuminosite; // la luminosité du background
    private int lastBackground; // la dernière fois qu'on a mis à jour le background
    private int backgroundFreq; // la fréquence à laquelle on met à jour le background

    // Entete
    private Rectangle enteteFond; // le fond de l'entête
    private List<String> catchPhrases; // les phrases d'accroche du shop !
    private int currentPhrase; // la catchphrase courante

    /* Initialisation du shop */
    public Shop(WindowGame windows) throws SlickException {
        // Calcul de la position
        this.pos = initPos(windows);

        // Création du Background
        this.initBackground();

        // Création de l'Entête
        this.initEntete();

        // Création des Produits
        this.produitsBots = initProduitsBots();
        this.produitsUpgrades = initProduitsUpgrades();

        // Créer tous les produits
        this.produitsBots = initProduitsBots();
        this.produitsUpgrades = initProduitsUpgrades();
    }

    private Rectangle initPos(WindowGame windows) {
        float x = windows.getWindowsWidth() * 0.72f;
        float y = 0;
        float w = windows.getWindowsWidth() * 0.28f;
        float h = windows.getWindowsHeight();
        return new Rectangle(x, y, w, h);
    }

    private void initBackground() {
        int x, y;
        // On crée autant de Rectangles que possible pour remplir la zone du shop
        this.sizeRect = 30; // en pixels
        this.backgroundSaturation = 0.40f;
        this.backgroundLuminosite = 0.40f;
        int nbLargeur = (int) pos.getWidth() / sizeRect + 1;
        int nbHauteur = (int) pos.getHeight() / sizeRect + 1;
        this.quadrillage = new ArrayList<Rectangle>();
        this.quadrillageColor = new ArrayList<Color>();
        for(int i = 0; i < nbLargeur; i++) {
            for(int j = 0; j < nbHauteur; j++) {
                x = (int) pos.getX() + i * sizeRect;
                y = (int) pos.getY() + j * sizeRect;
                quadrillage.add(new Rectangle(x, y, sizeRect, sizeRect));
                quadrillageColor.add(getRandomSaturatedColor(backgroundSaturation, backgroundLuminosite));
            }
        }
        this.lastBackground = 0;
        this.backgroundFreq = 100;
    }

    private void initEntete() {
        float x = pos.getX();
        float y = pos.getY();
        float w = pos.getWidth();
        float h = pos.getHeight() / 6;
        this.enteteFond = new Rectangle(x, y, w, h);

        this.catchPhrases = new ArrayList<String>();
        this.catchPhrases.add("Bienvenue dans le shop !");
        this.catchPhrases.add("Vous pouvez gaspiller vos pixels ici !");
        this.catchPhrases.add("La meilleure stratégie, c'est celle que tu n'auras pas choisie !");
        this.catchPhrases.add("Toujours acheter des Upgrades, jamais les bots, sinon c'est pas synergique !");
        this.catchPhrases.add("En vrai, tu paries combien qu'il y a un succes pour avoir acheté 1 000 Points ?");
        this.catchPhrases.add("Je parie que tu vas acheter un truc.");
        this.catchPhrases.add("Pile je gagne, Face tu perds. OK ?");

        this.currentPhrase = 0;
    }

    private List<ProduitBot> initProduitsBots() throws SlickException {
        List<ProduitBot> p = new ArrayList<ProduitBot>();

        // Créer tous les produits que l'on pourra acheter
        p.add(new ProduitBot(new EnsembleBot("item1", 10), 10, "resources/shop/prod_shop_1.png"));

        return p;
    }

    private List<ProduitUpgrade> initProduitsUpgrades() {
        List<ProduitUpgrade> p = new ArrayList<ProduitUpgrade>();

        // Créer tous les produits que l'on pourra acheter
        //p.add(new ProduitUpgrade());

        return p;
    }

    /* Affichage du shop */
    public void render(Graphics g) throws SlickException {
        // afficher le background du shop
        this.renderBackground(g);
        // afficher l'entête du shop
        this.renderEntete(g);
        // afficher les produitsUpgrades disponibles en magasin
        // afficher les produitsBots disponibles en magasin
        produitsBots.get(0).getIllustration().draw();
    }

    /* Le Background du shop est un quadrillage de carrés de couleurs qui changent au fur et à mesure du temps */
    private void renderBackground(Graphics g) {
        for(int i = 0; i < quadrillage.size(); i++) {
            g.setColor(quadrillageColor.get(i));
            g.fill(quadrillage.get(i));
        }
    }

    private void renderEntete(Graphics g) throws SlickException {
        // affichage du fond
        g.setColor(new Color(0, 0, 0, 175));
        g.fill(enteteFond);

        // affichage du mot shop
        String s = "SHOP";
        UnicodeFont font = new UnicodeFont("resources/fonts/pixelmix/pixelmix.ttf", 20, false, false);

        font.addAsciiGlyphs();
        font.addGlyphs(400, 600);
        font.getEffects().add(new ColorEffect());
        font.loadGlyphs();

        //Font font = g.getFont();
        float xString = enteteFond.getX() + enteteFond.getWidth() / 2 - font.getWidth(s) / 2;
        float yString = enteteFond.getY() + enteteFond.getHeight() * 0.3f - font.getHeight(s) / 2;
        g.setColor(new Color(255, 255, 255));
        g.setFont(font);
        g.drawString(s, xString, yString);

        // affichage d'une punch line
        String cp = catchPhrases.get(currentPhrase);
        boolean onADecoupe = false;
        while(font.getWidth(cp) > enteteFond.getWidth()) {
            onADecoupe = true;
            int i = 0;
            String cpFirst = "";
            while(i < cp.length() && font.getWidth(cp.substring(0, i)) < enteteFond.getWidth() - 20) {
                cpFirst = cp.substring(0, i);
                i++;
            }
            if(i < cp.length() - 1) {
                i--;
                cp = cp.substring(i, cp.length());
            }

            // On affiche la première partie de la chaine
            xString = enteteFond.getX() + enteteFond.getWidth() / 2 - font.getWidth(cpFirst) / 2;
            yString = yString + font.getHeight(cpFirst) * 1.2f;
            g.drawString(cpFirst, xString, yString);
        }

        // On affiche la fin de la chaine
        xString = enteteFond.getX() + enteteFond.getWidth() / 2 - font.getWidth(cp) / 2;
        if(onADecoupe) {
            yString = yString + font.getHeight(cp) * 1.2f;
        } else {
            yString = enteteFond.getY() + enteteFond.getHeight() * 0.65f - font.getHeight(cp) / 2;
        }
        g.drawString(cp, xString, yString);
    }

    /* Renvoie une couleur aléatoire d'une certaine saturation */
    private Color getRandomSaturatedColor(float s, float l) {
        Random rdn = new Random();
        float hue, saturation, brightness;
        hue = rdn.nextFloat(); // compris dans [0, 1]
        saturation = s;
        brightness = l;
        java.awt.Color cHSB = java.awt.Color.getHSBColor(hue, saturation, brightness);
        Color c = new Color(cHSB.getRed(), cHSB.getGreen(), cHSB.getBlue());
        return c;
    }


    /* Mise à jour du shop */
    public void update(int delta) {
        // mise à jour du background
        lastBackground += delta;
        if(lastBackground > backgroundFreq) {
            this.updateBackground();
            lastBackground = 0;
        }
        // mise à jour des produitsBots
        // mise à jour des produitsUpgrades
    }

    private void updateBackground() {
        /* METHODE 1 */
        // Si un voisin d'un carré du quadrillage a une couleur trop proche de la sienne, alors on change de couleur
        // Si aucune couleur n'a changé, alors on reset toutes les couleurs !
        /*
        float seuilChangementCouleur = 10f;
        boolean aChange = false;
        List<Color> voisins;
        for(int i = 0; i < quadrillageColor.size(); i++) {
            voisins = getCouleursVoisines(i);
            boolean couleurTropProche = false;
            for(Color c : voisins) {
                System.out.println("dist = " +colorHueDistance(c, quadrillageColor.get(i)) );
                if(colorHueDistance(c, quadrillageColor.get(i)) < seuilChangementCouleur) {
                    couleurTropProche = true;
                    aChange = true;
                }
            }
            if(couleurTropProche) {
                quadrillageColor.set(i, getRandomSaturatedColor(backgroundSaturation, backgroundLuminosite));
            }
        }
        if(!aChange) {
            for(int i = 0; i < quadrillageColor.size(); i++) {
                quadrillageColor.set(i, getRandomSaturatedColor(backgroundSaturation, backgroundLuminosite));
            }
        }
        */

        /* METHODE 2 */
        // Avec une certaine probabilité, un carré prend la couleur d'un de ses voisins
        // Lorsque tous les carrés sont de la même couleur, on reset toutes les couleurs !
        Random rdn = new Random();
        float probaVolerCouleur = 0.1f;
        float seuilDeDistance = 10f;
        boolean tousProchent = true;
        for(int i = 0; i < quadrillageColor.size(); i++) {
            List<Color> voisins = getCouleursVoisines(i);
            if(rdn.nextFloat() < probaVolerCouleur) {
                Color cSource = voisins.get(rdn.nextInt(voisins.size()));
                quadrillageColor.set(i, randomSimilarColor(cSource,seuilDeDistance));
            }
            for(Color c : voisins) {
                if(colorHueDistance(c, quadrillageColor.get(i)) > seuilDeDistance) {
                    tousProchent = false;
                }
            }
        }
        if(tousProchent) {
            for(int i = 0; i < quadrillageColor.size(); i++) {
                quadrillageColor.set(i, getRandomSaturatedColor(backgroundSaturation, backgroundLuminosite));
            }
        }
    }

    /* Renvoie les couleurs des carrés voisins d'un carré d'indice indice du quadrillage */
    private List<Color> getCouleursVoisines(int indice) {
        List<Color> list = new ArrayList<Color>();

        // Taille du quadrillage
        int nbLargeur = (int) pos.getWidth() / sizeRect + 1;
        int nbHauteur = (int) pos.getHeight() / sizeRect + 1;

        // position de la couleur
        int x = indice % nbLargeur;
        int y = indice / nbLargeur;
        int i;

        // couleur du voisin du haut
        if(y > 0) {
            i = (y-1) * nbLargeur + x;
            list.add(quadrillageColor.get(i));
        }
        // couleur du voisin du bas
        if(y < nbHauteur - 1) {
            i = (y + 1) * nbLargeur + x;
            list.add(quadrillageColor.get(i));
        }
        // couleur du voisin de gauche
        if(x > 0) {
            i = y * nbLargeur + x - 1;
            list.add(quadrillageColor.get(i));
        }
        // couleur du voisin de droite
        if(x < nbLargeur - 1) {
            i = y * nbLargeur + x + 1;
            list.add(quadrillageColor.get(i));
        }

        return list;
    }

    /* Renvoie la distance de teinte de deux couleurs */
    private float colorHueDistance(Color c1, Color c2) {
        float[] hsb1 = java.awt.Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), null);
        float[] hsb2 = java.awt.Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), null);

        float h1 = hsb1[0] * 360;
        float h2 = hsb2[0] * 360;

        float d = Math.min(Math.abs(h1 - h2),
                         Math.abs(Math.abs(h1 - h2) - 360));
        return d;
    }

    /* Renvoie une couleur aléatoire de teinte proche de la couleur cible et de même saturation et luminosité */
    private Color randomSimilarColor(Color source, float dist) {
        Random rdn = new Random();
        float[] hsb = java.awt.Color.RGBtoHSB(source.getRed(), source.getGreen(), source.getBlue(), null);
        float hue = hsb[0];

        hue = hue * 360;
        hue = hue + (rdn.nextFloat() * 2 - 1) * dist;
        hue = hue / 360;

        java.awt.Color c = java.awt.Color.getHSBColor(hue, backgroundSaturation, backgroundLuminosite);
        return new Color(c.getRed(), c.getGreen(), c.getBlue());
    }

    public void mouseClicked(int x, int y) {
        Random rdn = new Random();
        // Si on est dans l'entête
        if(x > enteteFond.getX() && x < enteteFond.getX() + enteteFond.getWidth()
                && y > enteteFond.getY() && y < enteteFond.getY() + enteteFond.getHeight()) {
            // on change de catchPhrase !
            int old = currentPhrase;
            while(currentPhrase == old) {
                currentPhrase = rdn.nextInt(catchPhrases.size());
            }
        }
    }
}















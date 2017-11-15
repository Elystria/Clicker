import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;

/* Le but de cette classe est de pouvoir afficher du text simplement à l'écran */
public class Text {
	
	/***************************************************
	ATTRIBUTS
	***************************************************/

	private String texte; // le texte à afficher
	private String fontName; // le nom de la font !
	private java.awt.Font fontJava; // la police à utiliser de Java
	private org.newdawn.slick.Font fontSlick; // la police de Slick
	private int fontSize; // la taille de la police
	private Color color; // la couleur du texte
	private int x, y, w, h; // la position du texte
	
	/***************************************************
	CONSTRUCTEUR
	***************************************************/
	
	public Text(String texte) {
		this(texte, java.awt.Font.SANS_SERIF, 20, Color.black);
	}
	
	public Text(String texte, String font, int fontSize, Color color) {
        this.texte = texte;
        this.fontName = font;
        this.fontSize = fontSize;
        this.color = color;
        
        // On charge la font
        if(font == null) {
        	// Si on a pas définit de font, on prend celle par défaut
        	this.fontJava = new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, fontSize);
        } else {
        	try {
        		// on lit la nouvelle font dans son fichier
        	    this.fontJava = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(font));
        	    // puis on lui "donne forme"
        	    this.fontJava = this.fontJava.deriveFont(java.awt.Font.PLAIN, fontSize);
			} catch (Exception e) {
				System.out.println("Echec du chargement de la font !");
				e.printStackTrace();
			}        	
        }
        // on convertie notre fontJava en fontSlick !
        this.fontSlick = new TrueTypeFont(fontJava, true);

        // on set les attributs de position
        this.x = 0;
        this.y = 0;
	}

	/***************************************************
	METHODES
	***************************************************/

	public void draw(Graphics g) {
        g.setFont(this.fontSlick);
        g.setColor(this.color);
        g.drawString(this.texte, x, y);
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setFont(String fontName) {
		this.fontName = fontName;
        // On charge la font
        if(fontName == null) {
        	// Si on a pas définit de font, on prend celle par défaut
        	this.fontJava = new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, fontSize);
        } else {
        	try {
        		// on lit la nouvelle font dans son fichier
        	    this.fontJava = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(fontName));
        	    // puis on lui "donne forme"
        	    this.fontJava = this.fontJava.deriveFont(java.awt.Font.PLAIN, fontSize);
				System.out.println(fontJava.getFontName());
			} catch (Exception e) {
				System.out.println("Echec du chargement de la font !");
				e.printStackTrace();
			}        	
        }
        // on convertie notre fontJava en fontSlick !
        this.fontSlick = new TrueTypeFont(fontJava, true);
	}
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
        // On charge la font
        if(fontName == null) {
        	// Si on a pas définit de font, on prend celle par défaut
        	this.fontJava = new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, fontSize);
        } else {
        	try {
        		// on lit la nouvelle font dans son fichier
        	    this.fontJava = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(fontName));
        	    // puis on lui "donne forme"
        	    this.fontJava = this.fontJava.deriveFont(java.awt.Font.PLAIN, fontSize);
			} catch (Exception e) {
				e.printStackTrace();
			}        	
        }
        // on convertie notre fontJava en fontSlick !
        this.fontSlick = new TrueTypeFont(fontJava, true);
	}

	public int getWidthText() {
		return fontSlick.getWidth(texte);
	}
	
	public int getHeightText() {
		return fontSlick.getHeight(texte);
	}

	
	public void centerArround(int x, int y) {
        this.setPos(x - this.getWidthText() / 2, y - this.getHeightText() / 2);
	}
	
	/***************************************************
	GETTEURS & SETTEURS
	***************************************************/
	
	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public String getFont() {
		return fontName;
	}
	public int getFontSize() {
		return fontSize;
	}
	public org.newdawn.slick.Font getFontSlick() {
		return fontSlick;
	}
}

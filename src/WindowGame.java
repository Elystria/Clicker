import org.newdawn.slick.*;

public class WindowGame extends BasicGame {

    private GameContainer container;
    //Position départ perso
    private float x = 300, y = 300;
    //Direction départ perso
    private int direction = 2;
    //animation marche
    private boolean moving = false;
    // Tableau de srpites
    private Animation[] animations = new Animation[8];

    public WindowGame() {
        super("Lesson 1 :: WindowGame");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        this.container = container;

        //Chargement et création de l'animation du perso
        SpriteSheet spriteSheet = new SpriteSheet("resources/sprites/character.png", 64, 64);
        //Animation fixe 1 sprite
        this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
        this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
        this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
        this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
        //Animations avec plusieurs sprite
        this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
        this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
        this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
        this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);

    }

    private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 100);
        }
        return animation;
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            //Mouvement du personnage
            case Input.KEY_UP:    this.direction = 0; this.moving = true; break;
            case Input.KEY_LEFT:  this.direction = 1; this.moving = true; break;
            case Input.KEY_DOWN:  this.direction = 2; this.moving = true; break;
            case Input.KEY_RIGHT: this.direction = 3; this.moving = true; break;
        }
    }

    @Override
    public void keyReleased(int key, char c){
        if (Input.KEY_ESCAPE == key){
            container.exit();
        }
        //Arret du perso quand on ne presse plus la touche
        if (Input.KEY_UP == key || Input.KEY_DOWN == key ||Input.KEY_LEFT == key || Input.KEY_RIGHT == key ){
            this.moving = false;
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawAnimation(animations[direction + (moving ? 4 : 0)], x, y);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (this.moving) {
            switch (this.direction) {
                case 0: this.y -= .1f * delta; break;
                case 1: this.x -= .1f * delta; break;
                case 2: this.y += .1f * delta; break;
                case 3: this.x += .1f * delta; break;
            }
        }

    }
}

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public abstract class AnimatedThing {
    Random rand = new Random();

    private double x_hero;
    private double y_hero;
    public ImageView spriteSheet_hero;

    private double x_end;
    private double y_end;
    public ImageView spriteSheet_end;



    private int runIndex=0;
    private int runIndexMax = 7;


    private int jumpIndex=0;
    public static int jumpOk=0;


    private double x_foe;
    private double y_foe;
    public ImageView spriteSheet_foe;
    int foe_count=0; //compteur d'apparition d'un ennemi
    int foe_countMax=100; // Fréquence d'apparation d'un ennemi
    boolean ennemi= false; //Présence d'un ennemi
    int ennemi_index=0; //pointe le rang de l'ennemi
    int speed=6;//Vitesse de déplacement de l'ennemi

    public boolean ingame= true; // le jeu continue si ingame == true
    public double y=250;//Position
    public double a=-9.81 ; //acceleration
    public double v=25; //vitesse


    //constructeur
    public AnimatedThing(double x, double y) {
        Image thisSpringSheet_hero = new Image("/Images/luffy.png"); // Attribue à Image l'image animée (notre Hero)
        this.spriteSheet_hero=new ImageView(thisSpringSheet_hero); // création de l'image à voir
        this.spriteSheet_hero.setX(x);
        this.spriteSheet_hero.setY(y);
        spriteSheet_hero.setViewport( new Rectangle2D(0,0,120,120)); // Rectangle2D crée un cadre 2D sur spritesheet, partant de (v,v1) et longueur v3 et de hauteur v4
        setXHero(x);
        setYHero(y);

        Image thisSpringSheet_foe = new Image("/Images/foes.png");
        this.spriteSheet_foe=new ImageView(thisSpringSheet_foe); // création de l'image à voir
        this.spriteSheet_foe.setX(x);
        this.spriteSheet_foe.setY(y);
        spriteSheet_foe.setViewport( new Rectangle2D(0,0,85,100)); // Rectangle2D crée un cadre 2D sur spritesheet, partant de (v,v1) et longueur v3 et de hauteur v4
        setXFoe(x);
        setYFoe(y);

        Image thisSpringSheet_end = new Image("/Images/end.png"); // Attribue à Image l'image animée (notre Hero)
        this.spriteSheet_end=new ImageView(thisSpringSheet_end); // création de l'image à voir
        this.spriteSheet_end.setX(x);
        this.spriteSheet_end.setY(y);
        spriteSheet_end.setViewport( new Rectangle2D(-1,-1,1,1)); // Rectangle2D crée un cadre 2D sur spritesheet, partant de (v,v1) et longueur v3 et de hauteur v4
        setXEnd(x);
        setYEnd(y);



    }
    //Méthode
    public void update(long time,double deltatime){ // permet d'actualiser l'image animée

        int newindex;
        int newindex2;
        if(jumpOk==0) { // Etat où on ne saute pas => Il court
            newindex = getRunIndex(); //Index permet de pointer les 6 positions différentes de notre Hero

            if (newindex == runIndexMax) {
                setRunIndex(0);
            } else {
                setRunIndex(newindex + 1);
            }
            spriteSheet_hero.setX(30);
            spriteSheet_hero.setViewport(new Rectangle2D(getRunIndex() * 126+5, 0, 120, 120)); // On affiche la nouvelle position du Hero à chaque appel de update


        }
        else { //Etat où on saute
            if (jumpIndex ==1 & y>200) {
                setJumpIndex(0);
                jumpOk = 0;
                jumpIndex=0;
                v=25;
                y=250;
                spriteSheet_hero.setY(y);

            }
            else {
                if (jumpIndex == 0) {
                    if (v<0) {
                        jumpIndex = 1;
                        spriteSheet_hero.setX(30);
                    }
                }
                v+=a*deltatime;
                y+=v*a*deltatime;
                spriteSheet_hero.setX(94);
                spriteSheet_hero.setY(y);
                spriteSheet_hero.setViewport(new Rectangle2D(jumpIndex * 128+64, 120, 64, 120)); // On affiche la nouvelle position du Hero à chaque appel de update

            }

        }

    }

    public void foeSummoning(long time){
        if(ennemi) {
            double position = getXFoe();
            if (position>10){
                this.spriteSheet_foe.setX(position-speed); //On modifie le x associé à l'image dans le background de gauche
                setXFoe( (position- speed));
                spriteSheet_foe.setViewport(new Rectangle2D( 120*ennemi_index+1, 0, 110, 170)); // On affiche la nouvelle position du Hero à chaque appel de update
            }
            else{ennemi=false;
            foe_count=0;
            }
        }
        else{
            if (foe_count<foe_countMax) {
                foe_count = foe_count+1;
                spriteSheet_foe.setViewport(new Rectangle2D( 0, 300, 110, 170)); // On affiche la nouvelle position du Hero à chaque appel de update
                setXFoe(600);
                this.spriteSheet_foe.setX(600);
            }
            else{
                ennemi= true;
                ennemi_index = rand.nextInt(9);
            }

        }
    }
    public void end(){
        this.spriteSheet_end.setX(100); //On modifie le x associé à l'image dans le background de gauche
        setXEnd(100);
        this.spriteSheet_end.setY(0); //On modifie le x associé à l'image dans le background de gauche
        setYEnd(0);
        spriteSheet_end.setViewport( new Rectangle2D(0,0,0,0)); // Rectangle2D crée un cadre 2D sur spritesheet, partant de (v,v1) et longueur v3 et de hauteur v4

    }


    //Setter
    public void setXHero(double x) {this.x_hero = x;}
    public void setYHero(double y) {this.y_hero = y;}
    public void setXFoe(double x) {this.x_foe = x;}
    public void setYFoe(double y) {this.y_foe = y;}
    public void setXEnd(double x) {this.x_end = x;}
    public void setYEnd(double y) {this.y_end = y;}

    public void setRunIndex(int runIndex) {this.runIndex = runIndex;}
    public void setJumpIndex(int jumpIndex) {this.jumpIndex = jumpIndex;}


    //Getter
    public double getXHero() {return x_hero;}

    public double getYHero() {return y_hero;}
    public double getXFoe() {return x_foe;}
    public int getRunIndex() {return runIndex;}
    public int getJumpIndex() {return jumpIndex;}
    public ImageView getEnd(){return this.spriteSheet_end;}


    public abstract ImageView getSpriteSheet();
}

package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class Bone {
    public static final int FLUCTUATION = 130;
    public static final int BONE_CAP = 100; //расстояние между top и bot bones
    public static final int LOWEST_OPENING = 120; //bottom bone
    public static final int BONE_WIDTH = 14;

    private Texture bottomBone, topBone, rowBone;
    private Vector2 posBottomBone, posTopBone;
    private Random rand;
    private int mode;
    private int broken;

    private Rectangle boundsTop, boundsBot, boundsRow;

    public Texture getBottomBone() {
        return bottomBone;
    }

    public Texture getTopBone() {
        return topBone;
    }

    public Texture getRowBone() {
        return rowBone;
    }

    public Vector2 getPosBottomBone() {
        return posBottomBone;
    }

    public Vector2 getPosTopBone() {
        return posTopBone;
    }

    public Bone(float x, int modes) {
        bottomBone = new Texture("bone.png");
        topBone = new Texture("bone.png");
        rowBone = new Texture("bones.png");
        rand = new Random();
        mode = modes;
        broken = 0;

        //posTopBone = new Vector2(x, rand.nextInt(FLUCTUATION) + BONE_CAP + LOWEST_OPENING);
        //posBottomBone = new Vector2(x, posTopBone.y - BONE_CAP - bottomBone.getHeight());
        //if (mode == 1)
        //    posBottomBone = new Vector2(x, 10);
        //else if (mode == 2) {
        posBottomBone = new Vector2(x, 10);
        if (mode == 3) {
            posTopBone = new Vector2(x, posBottomBone.y + BONE_CAP + BONE_CAP + BONE_CAP);
        }
        else {
            posTopBone = new Vector2(x, posBottomBone.y + BONE_CAP);
        }
        boundsTop = new Rectangle(posTopBone.x, posTopBone.y, topBone.getWidth(), topBone.getHeight());
        boundsBot = new Rectangle(posBottomBone.x, posBottomBone.y, bottomBone.getWidth(), bottomBone.getHeight());
        boundsRow = new Rectangle(posBottomBone.x, posBottomBone.y, rowBone.getWidth(), rowBone.getHeight());

        boundsRow.setPosition(-100, -100);
        boundsBot.setPosition(-100, -100);
        boundsTop.setPosition(-100, -100);

        // }
        //else if (mode == 3)
         //   posBottomBone = new Vector2(x, 10);
    }

    public void reposition(float x) {
        //posTopBone.set(x, rand.nextInt(FLUCTUATION) + BONE_CAP + LOWEST_OPENING);
        //posTopBone.set(x, posTopBone.y - BONE_CAP - bottomBone.getHeight());

        posBottomBone.add(x, 0);
        posTopBone.add(x, 0);
        if (posBottomBone.x < 0) {
            posBottomBone.x = 500;
            posTopBone.x = 500;
            mode = rand.nextInt(3) + 1;
            broken = 0;
        }

        if (broken == 1) {
            if (mode == 1) {
                boundsBot.setPosition(-100, -100);
            }
            else if (mode == 2) {
                boundsBot.setPosition(-100, -100);
                boundsTop.setPosition(-100, -100);
            }
            else if (mode == 3) {
                boundsRow.setPosition(-100, -100);
            }
        }
        else {
            if (mode == 1) {
                boundsBot.setPosition(posBottomBone.x, posBottomBone.y);
            }
            else if (mode == 2) {
                boundsBot.setPosition(posBottomBone.x, posBottomBone.y);
                boundsTop.setPosition(posTopBone.x, posTopBone.y);
            }
            else if (mode == 3) {
                boundsRow.setPosition(posBottomBone.x, posBottomBone.y);
            }
        }

        /*if (mode == 1) {
            posBottomBone.add(x, 0);
            if (posBottomBone.x < 0) {
                posBottomBone.x = 500;
                mode = rand.nextInt(3) + 1;
            }
        }
        else if (mode == 2) {
            posBottomBone.add(x, 0);
            posTopBone.add(x, 0);
            if (posBottomBone.x < 0) {
                posBottomBone.x = 500;
                posTopBone.x = 500;
                mode = rand.nextInt(3) + 1;
            }
        }
        if (mode == 3) {
            posBottomBone.add(x, 0);
            if (posBottomBone.x < 0) {
                posBottomBone.x = 500;
                mode = rand.nextInt(3) + 1;
            }
        }*/
        //posBottomBone.add(MOVEMENT * dt, velocity.y, 0);
    }

    public boolean collides (Rectangle hero) {
        return hero.overlaps(boundsBot) || hero.overlaps(boundsTop) ||hero.overlaps(boundsRow);
    }

    public void broke() {
        broken = 1;
    }

    public int getBroken() {return broken;}

    public int getMode() {return mode;}
}

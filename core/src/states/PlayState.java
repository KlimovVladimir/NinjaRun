package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.kvg.KVGames;
import com.mygdx.kvg.Swipes;

import java.awt.Button;
import java.util.Random;

import sprites.Bone;
import sprites.Hero;

public class PlayState extends State {

    public static final int BONE_SPACING = 70; //расстояние между костями по ширине
    public static final int BONE_COUNT = 4;

    private Hero hero;
    private Texture background;
    public Swipes swipe;
    Vector3 touchPos;
    int mode;

    Random rand;

    private Array<Bone> bones1;
    private Array<Bone> bones2;

    public PlayState(GameStateManager gsm) {
        super(gsm);
       // hero = new Hero(20,30);
        swipe = new Swipes();
        touchPos = new Vector3();

        background = new Texture("background.png");
        hero = new Hero(200,10);
        camera.setToOrtho(false, KVGames.WIDTH/2, KVGames.HEIGHT/ 2);

        bones1 = new Array<Bone>();
        bones2 = new Array<Bone>();

        rand = new Random();

        int i = 0;
        int tmp;
        for (i = 1; i <= BONE_COUNT; i++) {
            tmp = rand.nextInt(2) + 1;
            if (tmp == 3)
                bones1.add(new Bone( i * (BONE_SPACING + Bone.BONE_WIDTH + 60) + 300,tmp));
            else
                bones1.add(new Bone( i * (BONE_SPACING + Bone.BONE_WIDTH) + 300,tmp));
        }

        /*for (; i < (BONE_COUNT / 3) * 2; i++) {
            bones1.add(new Bone( i * (BONE_SPACING + Bone.BONE_WIDTH),2 ));
        }

        for (; i < BONE_COUNT; i++) {
            bones1.add(new Bone( i * (BONE_SPACING + Bone.BONE_WIDTH),3 ));
        }*/
    }

    @Override
    protected void handleInput() {
        //swipe.is_swipe_up() ||
        if (Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            hero.jump();
            //hero.setXPosition(10);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            hero.setXPosition(-1.5f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            hero.setXPosition(1.5f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            hero.attack();
        }

    }

    private float boneSpeed = 1.5f;
    //private float boneSpeed = 2f;
    @Override
    public void update(float dt) {
        handleInput();
        hero.update(dt);
        //camera.position.x = hero.getPosition().x + 80;
        boneSpeed += 0.0005f;
        for (Bone bone : bones1) {
            bone.reposition( (/*rand.nextInt(3) +*/ boneSpeed) * -1);
            /*if (camera.position.x - (camera.viewportHeight / 2) > bone.getPosTopBone().x
            + bone.getTopBone().getWidth()) {
                bone.reposition(bone.getPosTopBone().x + ((Bone.BONE_WIDTH + BONE_SPACING) * BONE_COUNT));
            }*/
            if (bone.collides(hero.getBounds())) {
                if (hero.getTimeSinceAttack() > hero.getBaseAttackTime())
                    gsm.set(new PlayState(gsm));
                else {
                    bone.broke();
                }
            }
        }
        camera.update();
    }

    //ShapeRenderer shapeRenderer = new ShapeRenderer();
   // ShapeRenderer shapeRenderer1 = new ShapeRenderer();
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, KVGames.WIDTH, KVGames.HEIGHT);
        sb.draw(hero.getNinja(),hero.getPosition().x, hero.getPosition().y);
        for (Bone bone : bones1) {
            if(bone.getBroken() == 0) {
                mode = bone.getMode();
                if (mode == 1)
                    sb.draw(bone.getBottomBone(), bone.getPosBottomBone().x, bone.getPosBottomBone().y);
                else if (mode == 2) {
                    sb.draw(bone.getTopBone(), bone.getPosTopBone().x, bone.getPosTopBone().y);
                    sb.draw(bone.getBottomBone(), bone.getPosBottomBone().x, bone.getPosBottomBone().y);
                /*shapeRenderer1.setProjectionMatrix(camera.combined);
                shapeRenderer1.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer1.setColor(Color.GREEN);
                shapeRenderer1.rect(bone.getPosTopBone().x, bone.getPosTopBone().y, bone.getTopBone().getWidth(), bone.getTopBone().getHeight());
                shapeRenderer1.end();*/
                } else if (mode == 3)
                    sb.draw(bone.getRowBone(), bone.getPosBottomBone().x, bone.getPosBottomBone().y);
            }
        }

        /*shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(hero.getPosition().x, hero.getPosition().y, hero.getNinja().getRegionWidth(), hero.getNinja().getRegionHeight());
        shapeRenderer.end();*/

        sb.end();
    }

    @Override
    public void dispose() {
        hero.dispose();
    }
}

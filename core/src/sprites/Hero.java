package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Hero {
    public static  final int MOVEMENT = 100;
    public static  final int GRAVITY = -11;
    public static final float BASE_ATTACK_TIME = 0.5f;
    private float timeSinceAttack;

    private Vector3 position;
    private Vector3 velocity;

    //private Texture heroStand;
    private Texture heroJump;
    private Texture heroSlide;
    private Texture heroAttack;
    private Texture heroJumpAttack;

    private Texture heroWalk;
    private Animation ninjaWalk;
    private Animation ninjaAttack;
    private Animation ninjaJumpAttack;
    private Animation ninjaJump;

    private Rectangle bounds;
    private Rectangle meleeAttack;

    private int attackFlag = 0;

    public Hero(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);
        //heroStand = new Texture("hero_stand.png");
        heroJump = new Texture("ninja_jump.png");
        heroSlide = new Texture("hero_slide.png");
        heroAttack = new Texture("ninja_attack.png");
        heroJumpAttack = new Texture("ninja_attack_jump.png");
        timeSinceAttack = 10;

        heroWalk = new Texture("ninja_Run.png");
        ninjaWalk = new Animation(new TextureRegion(heroWalk), 9, 0.5f);
        ninjaAttack = new Animation(new TextureRegion(heroAttack), 9, 0.5f);
        ninjaJump = new Animation(new TextureRegion(heroJump), 9, 0.5f);
        ninjaJumpAttack = new Animation(new TextureRegion(heroJumpAttack), 9, 0.5f);
        bounds = new Rectangle(x, y, heroWalk.getWidth() / 18, heroWalk.getHeight());
        meleeAttack = new Rectangle(x, y, heroAttack.getWidth() / 18, heroAttack.getHeight());

    }

    public Vector3 getPosition() {
        return position;
    }

    public void setXPosition(float x) {
        if((position.x + x) > 0 && (position.x + x) < 370)
        position.x += x;
    }

    public Texture getHero() {
        if (position.y > 10 && timeSinceAttack >= BASE_ATTACK_TIME)
            return heroJump;
        else if (position.y == 5)
            return heroSlide;
        else if(timeSinceAttack < BASE_ATTACK_TIME && position.y == 10)
            return heroAttack;
        //else if(timeSinceAttack < BASE_ATTACK_TIME && position.y > 10)
            return heroJumpAttack;
        //else
           // return heroStand;
    }

    public TextureRegion getNinja() {
        if (position.y > 10 && timeSinceAttack >= BASE_ATTACK_TIME)
            return ninjaJump.getFrame();
        else if(timeSinceAttack < BASE_ATTACK_TIME && position.y == 10)
            return ninjaAttack.getFrame();
        else if(timeSinceAttack < BASE_ATTACK_TIME && position.y > 10)
            return ninjaJumpAttack.getFrame();
        else
            return ninjaWalk.getFrame();
    }

    public void update(float dt) {
        timeSinceAttack += dt;
        if (position.y > 10)
            velocity.add(MOVEMENT, GRAVITY, 0);
        velocity.scl(dt);
        //position.add(MOVEMENT * dt, velocity.y, 0);
        position.add(0, velocity.y, 0);
        velocity.scl(1 / dt);

        if (position.y < 10) {
            position.y = 10;
        }
        bounds.setPosition(position.x, position.y);

        if(timeSinceAttack < BASE_ATTACK_TIME)
        {
            meleeAttack.setPosition(position.x, position.y);
            attackFlag = 1;
        }
        else
            attackFlag = 0;

        ninjaWalk.update(dt);
        ninjaJump.update(dt);
        if (attackFlag == 1) {
            ninjaAttack.update(dt);
            ninjaJumpAttack.update(dt);
        }
        else
        {
            ninjaAttack.update(0);
            ninjaJumpAttack.update(0);
        }
        //meleeAttack
    }

    public Rectangle getBounds() {
        if(timeSinceAttack < BASE_ATTACK_TIME)
            return meleeAttack;
        else
            return bounds;
    }

    public float getTimeSinceAttack()
    {
        return timeSinceAttack;
    }

    public float getBaseAttackTime()
    {
        return BASE_ATTACK_TIME;
    }

    public void jump() {
        if (position.y <= 10) {
            position.y = 11;
            velocity.y = 300;
        }
    }

    public void slide() {
        if (position.y == 10)
            position.y = 5;
    }

    public void attack()
    {
        if (timeSinceAttack >= 0.8f) {
            timeSinceAttack = 0;
        }
    }

    public void dispose() {
        heroWalk.dispose();
    }
}

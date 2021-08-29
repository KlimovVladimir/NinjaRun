package com.mygdx.kvg;

import com.badlogic.gdx.Gdx;

public class Swipes {

    public boolean is_swipe_right(){
        if(Gdx.input.isTouched()&&Gdx.input.getDeltaX()>0)
            return true;
        return false;
    }

    public boolean is_swipe_left(){
        if(Gdx.input.isTouched()&&Gdx.input.getDeltaX()<0)
            return true;
        return false;
    }

    public boolean is_swipe_up(){
        if(Gdx.input.isTouched()&&Gdx.input.getDeltaY()>0)
            return true;
        return false;
    }

    public boolean is_swipe_down(){
        if(Gdx.input.isTouched()&& Gdx.input.getDeltaX()<0)
            return true;
        return false;
    }
}

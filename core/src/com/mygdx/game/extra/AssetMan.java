package com.mygdx.game.extra;

import static com.mygdx.game.extra.Utils.ATLAS_MAP;
import static com.mygdx.game.extra.Utils.BACKGROUND_IMAGE;
import static com.mygdx.game.extra.Utils.CUBE01;
import static com.mygdx.game.extra.Utils.CUBE02;
import static com.mygdx.game.extra.Utils.CUBE03;
import static com.mygdx.game.extra.Utils.CUBE_OBSTACLE;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetMan {

    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    public AssetMan(){
        this.assetManager = new AssetManager();

        assetManager.load(ATLAS_MAP, TextureAtlas.class);
        assetManager.finishLoading();

        this.textureAtlas = assetManager.get(ATLAS_MAP);
    }
    //IMAGEN DE FONDO
    public TextureRegion getBackground(){
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE);
    }

    //
    public TextureRegion getObstacleCube(){
        return this.textureAtlas.findRegion(CUBE_OBSTACLE);
    }

    //ANIMACIÓN PÁJARO
    public Animation<TextureRegion> getCubeAnimation(){
        return new Animation<TextureRegion>(0.20f,
                textureAtlas.findRegion(CUBE01),
                textureAtlas.findRegion(CUBE02),
                textureAtlas.findRegion(CUBE03));

    }



}















package com.mygdx.game.actors;

import static com.mygdx.game.extra.Utils.USER_CUBE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Cube extends Actor {

    private static float CUBE_WIDTH = 0.5f;
    private static float CUBE_HEIGHT = 0.5f;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_DEAD = 1;

    private static final float JUMP_SPEED = 10f;
    private static final float SPEED = 0.01f;

    private int state;

    private Animation<TextureRegion> birdAnimation;
    private Vector2 position;

    private float stateTime;

    private World world;

    private Body body;
    private Fixture fixture;

    public Cube(World world, Animation<TextureRegion> animation, Vector2 position){
        this.birdAnimation = animation;
        this.position = position;
        this.world = world;

        this.stateTime = 0f;
        createBody();
        createFixture();
    }

    private void createBody(){
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(this.position);

        bodyDef.type = BodyDef.BodyType.DynamicBody;

        this.body = this.world.createBody(bodyDef);
    }

    private void createFixture(){
        PolygonShape cube = new PolygonShape();
        cube.setAsBox(CUBE_WIDTH/2, CUBE_HEIGHT/2);

        this.fixture = this.body.createFixture(cube, 8);
        this.fixture.setFriction(0);
        this.fixture.setUserData(USER_CUBE);

        cube.dispose();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        Vector2 impulse = new Vector2(SPEED, 0); // Magnitud y dirección de la fuerza
        Vector2 point = body.getWorldCenter(); // Punto de aplicación de la fuerza (centro del cuerpo)
        body.applyLinearImpulse(impulse, point, true);

        boolean jump  = Gdx.input.justTouched();

        if(jump && this.state == STATE_NORMAL){
            //this.jumpSound.play();
            this.body.applyLinearImpulse(new Vector2(-0.5f, JUMP_SPEED), point, true);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x - (CUBE_WIDTH/2), body.getPosition().y - (CUBE_HEIGHT/2));
        batch.draw(this.birdAnimation.getKeyFrame(stateTime, true), getX(), getY(), CUBE_WIDTH,CUBE_HEIGHT);

        stateTime += Gdx.graphics.getDeltaTime();
    }

    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

}

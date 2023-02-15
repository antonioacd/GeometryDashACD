package com.mygdx.game.actors;

import static com.mygdx.game.extra.Utils.USER_CUBE_OBSTACLE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Obstacle extends Actor {

    private static float OBSTACLE_WIDTH = 0.3f;
    private static float OBSTACLE_HEIGHT = 0.3f;

    private TextureRegion texture;
    private Vector2 position;

    private float stateTime;

    private World world;

    private Body body;
    private Fixture fixture;

    public Obstacle(World world, TextureRegion texture, Vector2 position){
        this.texture = texture;
        this.position = position;
        this.world = world;

        this.stateTime = 0f;
        createBody();
        createFixture();
    }

    private void createBody(){
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(this.position);

        bodyDef.type = BodyDef.BodyType.StaticBody;

        this.body = this.world.createBody(bodyDef);
    }

    private void createFixture(){
        PolygonShape obstacle = new PolygonShape();
        obstacle.setAsBox(OBSTACLE_WIDTH, OBSTACLE_HEIGHT);

        this.fixture = this.body.createFixture(obstacle, 8);
        this.fixture.setUserData(USER_CUBE_OBSTACLE);

        obstacle.dispose();
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(body.getPosition().x - (OBSTACLE_WIDTH/2), body.getPosition().y - (OBSTACLE_HEIGHT/2));
        batch.draw(this.texture, getX(), getY(), OBSTACLE_WIDTH,OBSTACLE_HEIGHT);

        stateTime += Gdx.graphics.getDeltaTime();
    }

    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }

}

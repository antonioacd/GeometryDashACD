package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.CAMERA_HEIGHT;
import static com.mygdx.game.extra.Utils.CAMERA_WIDTH;
import static com.mygdx.game.extra.Utils.USER_FLOOR;
import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Cube;
import com.mygdx.game.actors.Obstacle;

public class GameScreen  extends BaseScreen{




    private Stage stage;
    private Cube cube;

    private Obstacle obs01;

    private Image background;

    private World world;

    //Depuración
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera ortCamera;

    public GameScreen(MainGame mainGame) {
        super(mainGame);

        this.world = new World(new Vector2(0,-10), true);
        FitViewport fitViewport = new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT);
        this.stage = new Stage(fitViewport);

        this.ortCamera = (OrthographicCamera) this.stage.getCamera();
        this.debugRenderer = new Box2DDebugRenderer();
    }

    public void addBackground(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }

    public void addCube(){
        Animation<TextureRegion> cubeSprite = mainGame.assetManager.getCubeAnimation();
        this.cube = new Cube(this.world,cubeSprite, new Vector2(1f,0.2f));
        this.stage.addActor(this.cube);
    }

    public void addObstacles(){
        TextureRegion obstacleCube = mainGame.assetManager.getObstacleCube();
        this.obs01 = new Obstacle(this.world,obstacleCube, new Vector2(5f,0.2f));
        this.stage.addActor(this.obs01);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act();
        this.world.step(delta,6,2);
        this.stage.draw();

        this.debugRenderer.render(this.world, this.ortCamera.combined);

        ortCamera.position.set((ortCamera.position.x + (cube.getX() - ortCamera.position.x)+1.5f), WORLD_HEIGHT/2f, 0);
    }

    @Override
    public void show() {
        addBackground();
        addCube();
        addFloor();
        addRoof();
        addObstacles();
    }

    @Override
    public void hide() {
        this.cube.detach();
        this.cube.remove();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.world.dispose();
    }

    private void addFloor() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(3/2,0f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);
        body.setUserData(USER_FLOOR);

        PolygonShape edge = new PolygonShape();
        edge.setAsBox(100f, 0.2f);
        body.createFixture(edge, 3);
        edge.dispose();
    }

    public void addRoof(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        EdgeShape edge = new EdgeShape();
        edge.set(0,WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT);
        body.createFixture(edge, 1);
        edge.dispose();
    }


}

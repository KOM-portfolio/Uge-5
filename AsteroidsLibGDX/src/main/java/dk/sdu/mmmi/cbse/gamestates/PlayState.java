package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.entities.Enemy;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.entities.Projectile;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayState extends GameState {

    private ShapeRenderer sr;

    private Player player;
    private Enemy enemy;
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {

        sr = new ShapeRenderer();

        player = new Player();

        enemy = new Enemy();
    }

    public void update(float dt) {

        handleInput();

        player.update(dt);

        enemy.update(dt);
        
        Random rnd = new Random();
        if(rnd.nextInt(20) == 2){
            projectiles.add(new Projectile(enemy.getX(), enemy.getY(), enemy.getRadians()));
        }
        
        List<Projectile> deadProjectiles = Collections.synchronizedList(new ArrayList<>());
        
        projectiles.stream().forEach((bullet) -> {
            if(bullet.isDurationOut()){
                deadProjectiles.add(bullet);
            }
            bullet.update(dt);
        });
        projectiles.removeAll(deadProjectiles);
    }

    public void draw() {
        player.draw(sr);
        enemy.draw(sr);
        projectiles.stream().forEach((bullet) -> {
            bullet.draw(sr);
        });
    }

    public void handleInput() {
        player.setLeft(GameKeys.isDown(GameKeys.LEFT));
        player.setRight(GameKeys.isDown(GameKeys.RIGHT));
        player.setUp(GameKeys.isDown(GameKeys.UP));
        if (GameKeys.isPressed(GameKeys.SPACE)) {
            projectiles.add(new Projectile(player.getX(), player.getY(), player.getRadians()));
        }
    }

    public void dispose() {
    }

}

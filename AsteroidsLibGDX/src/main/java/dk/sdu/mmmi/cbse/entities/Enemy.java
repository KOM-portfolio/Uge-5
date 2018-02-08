/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;
import java.util.Random;

/**
 *
 * @author chris
 */
public class Enemy extends SpaceObject {

    private float maxSpeed;
    private float acceleration;
    private float deceleration;

    public Enemy() {

        x = Game.WIDTH / 4;
        y = Game.HEIGHT / 4;

        shapex = new float[4];
        shapey = new float[4];

        maxSpeed = 200;
        acceleration = 100;
        deceleration = 10;

        radians = 3.1415f / 2;
        rotationSpeed = 3;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadians() {
        return radians;
    }

    public void update(float dt) {
        Random rnd = new Random();
        boolean forward = rnd.nextBoolean();
        int leftOrRight = rnd.nextInt(128);
        // turning
        if (leftOrRight < 64) {
            radians += rotationSpeed * dt;
        } else if (leftOrRight >= 64) {
            radians -= rotationSpeed * dt;
        }

        // accelerating
        if (forward) {
            dx += MathUtils.cos(radians) * acceleration * dt;
            dy += MathUtils.sin(radians) * acceleration * dt;
        }

        // deceleration
        float vec = (float) Math.sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // set position
        x += dx * dt;
        y += dy * dt;

        // set shape
        setShape();

        // screen wrap
        wrap();

    }

    public void draw(ShapeRenderer sr) {

        sr.setColor(255, 0, 0, 1);

        sr.begin(ShapeRenderer.ShapeType.Line);

        for (int i = 0, j = shapex.length - 1;
                i < shapex.length;
                j = i++) {

            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);

        }

        sr.end();

    }

    private void setShape() {
        shapex[0] = x + MathUtils.cos(radians) * 8;
        shapey[0] = y + MathUtils.sin(radians) * 8;

        shapex[1] = x + MathUtils.cos(radians - 4 * 3.1415f / 5) * 8;
        shapey[1] = y + MathUtils.sin(radians - 4 * 3.1145f / 5) * 8;

        shapex[2] = x + MathUtils.cos(radians + 3.1415f) * 5;
        shapey[2] = y + MathUtils.sin(radians + 3.1415f) * 5;

        shapex[3] = x + MathUtils.cos(radians + 4 * 3.1415f / 5) * 8;
        shapey[3] = y + MathUtils.sin(radians + 4 * 3.1415f / 5) * 8;
    }
}

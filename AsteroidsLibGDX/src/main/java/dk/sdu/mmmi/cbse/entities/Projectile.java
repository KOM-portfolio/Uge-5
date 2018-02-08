/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import java.util.Random;

/**
 *
 * @author chris
 */
public class Projectile extends SpaceObject {

    private final int maxSpeed;
    private final int deceleration;
    private final int maxDuration;
    private int duration;
    private boolean durationOut;
    
    public Projectile(float _x, float _y, float _radians) {
        x = _x;
        y = _y;
        radians = _radians;
        speed = 200;
        deceleration = 25;
        maxSpeed = 300;
        maxDuration = 250;
        durationOut = false;
        shapex = new float[4];
        shapey = new float[4];
    }

    private void setShape() {
        shapex[0] = x + MathUtils.cos(radians) * 2;
        shapey[0] = y + MathUtils.sin(radians) * 2;

        shapex[1] = x + MathUtils.cos(radians - 4 * 3.1415f / 5) * 2;
        shapey[1] = y + MathUtils.sin(radians - 4 * 3.1145f / 5) * 2;

        shapex[2] = x + MathUtils.cos(radians + 3.1415f) * 2;
        shapey[2] = y + MathUtils.sin(radians + 3.1415f) * 2;

        shapex[3] = x + MathUtils.cos(radians + 4 * 3.1415f / 5) * 2;
        shapey[3] = y + MathUtils.sin(radians + 4 * 3.1415f / 5) * 2;
    }

    public void update(float dt) {
        // forward movement
        dx += MathUtils.cos(radians) * speed * dt;
        dy += MathUtils.sin(radians) * speed * dt;

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
        duration++;
        if(duration == maxDuration){
            durationOut = true;
        }
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

    public boolean isDurationOut() {
        return durationOut;
    }

}

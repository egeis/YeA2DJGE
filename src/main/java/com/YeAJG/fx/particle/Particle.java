/*
 * The MIT License
 *
 * Copyright 2015 Richard Coan.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * HTE SOFTWARE.
 */
package main.java.com.YeAJG.fx.particle;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public class Particle {
    private double age;
    
    private final double MAX_AGE;
    private final double AGE_INC;
    
    private Vector3f location;
    private Vector3f velocity;
    private Vector3f acceleration;
    
    private long lastUpdate = 0l; 
    
    public Particle(Vector3f location, Vector3f velocity, Vector3f acceleration, double MAX_AGE, double AGE_INC) {
        this.MAX_AGE = MAX_AGE;
        this.AGE_INC = AGE_INC;
        
        age = 0.0;
        
        this.location = location;
        this.acceleration = acceleration;
        this.velocity = velocity;
    }
    
    public void draw()
    {
        GL11.glColor3f(0.5f,0.5f,1.0f);
        
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2f(location.x,     location.y);
            GL11.glVertex2f(location.x + 10, location.y);
            GL11.glVertex2f(location.x + 10, location.y + 10);
            GL11.glVertex2f(location.x,     location.y + 10);
        GL11.glEnd();
    }
    
    public boolean update(long next_game_tick)
    {        
        if( next_game_tick > lastUpdate ) 
        {
            lastUpdate = next_game_tick;
            Vector3f.add(velocity, acceleration, velocity);
            Vector3f.add(location, velocity, location);
        
            age += AGE_INC;
            return true;
        }
        
        return false;
    }
    
    public boolean isDead()
    {
        if(age < MAX_AGE)
            return false;
        else
            return true;
    }
}

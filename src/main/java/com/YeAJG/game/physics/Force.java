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
 * THE SOFTWARE.
 */
package main.java.com.YeAJG.game.physics;

import main.java.com.YeAJG.game.GameLauncher;
import main.java.com.YeAJG.game.entity.Entity;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public class Force extends Entity {
    
    protected int fId;
    
    protected boolean isAttractor;
    protected float mass;
    
    private boolean randomize = false;

    public void setRandomize(boolean randomize) {
        this.randomize = randomize;
    }    
    
    public Force(int fId, boolean isAttractor, float mass, Vector3f pos)
    {
        this.fId = fId;
        this.mass = mass;
        this.isAttractor = isAttractor;
               
        this.Setup(
                pos, 
                new Vector3f(0,0,0), 
                new Vector3f(1,1,1),
                "assets/shaders/vertex.glsl", 
                "assets/shaders/fragment.glsl", 
                ((GameLauncher.DEBUG)?"assets/textures/force_symbol.png":""),
                new Vector3f[] { 
                    new Vector3f(-0.5f, 0.5f, 0),
                    new Vector3f(-0.5f, -0.5f, 0), 
                    new Vector3f(0.5f, -0.5f, 0),
                    new Vector3f(0.5f, 0.5f, 0) 
                }, 
                new Vector3f[] { 
                    new Vector3f(1, 1, 1), 
                    new Vector3f(1, 1, 1),
                    new Vector3f(1, 1, 1), 
                    new Vector3f(1, 1, 1)
                },
                new Vector2f[] {
                    new Vector2f(0, 0),       
                    new Vector2f(0, 1),
                    new Vector2f(1, 1),
                    new Vector2f(1, 0)
                }
        );
    }
        
    public Entity apply(Entity e)
    {
        if(randomize == true) {
            mass = (float) Math.sin(System.currentTimeMillis());
            //if(mass < 0.0f) mass = 0.0f;    //Floor
        } 
        
        //TODO: Calculate Force on Axis.
        
        return e;
    }
    
}

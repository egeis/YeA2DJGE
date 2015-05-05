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
import main.java.com.YeAJG.game.utils.MathUtil;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public class Force extends Entity {
    
    protected int fId;
    
    protected boolean isAttractor;
   
    protected final int type;
    protected int direction;
    
    private boolean randomize = false;
    
    public static final int TYPE_LINEAR = 1;
    
    public static final int DIR_X = 1;
    public static final int DIR_Y = 2;
    public static final int DIR_Z = 2;
    
    public void setRandomize(boolean randomize) {
        this.randomize = randomize;
    }    

    public void setDirection(int direction) {
        this.direction = direction;
    }
        
    public Force(int fId, int type, boolean isAttractor, float mass, Vector3f pos)
    {
        this.fId = fId;
        this.mass = mass;
        this.type = type;
        this.isAttractor = isAttractor;
               
        this.Setup(
                pos, 
                new Vector3f(0.0f, 10.0f, 0.5f), 
                new Vector3f(0.05f, 0.05f, 0.05f), 
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
            mass = Math.abs( (float) Math.sin(System.currentTimeMillis()) ) / 10.0f;
            //if(mass < 0.0f) mass = 0.0f;    //Floor
        } 
        
        float f, aX, aY, aZ;
        
        //TODO, use != Total Cords of Force Emitter.
        if( (e.getModelPos().x * modelPos.x) + (e.getModelPos().y * modelPos.y) + (e.getModelPos().z * modelPos.z) != 0)
        {
            f = e.getMass() * mass * 1.15f;
            logger.info(f);
            
            switch(type)
            {
                case TYPE_LINEAR:
                    Vector3f accel = new Vector3f();
                    if(direction == DIR_X) {
                        Vector3f.add(e.getModelAccel(), new Vector3f(f, 0 , 0), accel);
                    }
                        
                    if(direction == DIR_Y) {
                        Vector3f.add(e.getModelAccel(), new Vector3f(0, f , 0), accel);
                    }
                        
                    if(direction == DIR_Z) {
                        Vector3f.add(e.getModelAccel(), new Vector3f(0, 0 , f), accel);
                    }
                
                e.setModelAccel(accel);
            }
                        
         
        }
        //TODO: Calculate Force on Axis.
        
        return e;
    }
    
}

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

import java.util.ArrayList;
import main.java.com.YeAJG.api.physics.IForce;
import main.java.com.YeAJG.game.GameLauncher;
import main.java.com.YeAJG.game.entity.Entity;
import main.java.com.YeAJG.game.utils.MathUtil;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public class Force extends Entity implements IForce {
    
    protected int fId;
    
    protected boolean isAttractor;
   
    protected final int type;
    protected int direction;
    
    protected float mass;
    
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

    @Override
    public void apply(ArrayList<Integer> forces, Entity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

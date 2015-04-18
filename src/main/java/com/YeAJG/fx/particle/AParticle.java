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
package main.java.com.YeAJG.fx.particle;

import main.java.com.YeAJG.game.Entity.AEntity;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Richard Coan
 */
public abstract class AParticle extends AEntity {  
    public float age;
    public float ageStep;
    public float maxAge;
    
    public Vector3f prevLocation;    //x, y, z 
            
    public boolean keepAlive;
        
    public Texture texture;
    public Color color;
    
    protected void setDefaults(Particle p)
    {
        this.acceleration = p.acceleration;
        this.age = p.age;
        this.ageStep = p.ageStep;
        this.color = p.color;
        this.location = p.location;
        this.keepAlive = p.keepAlive;
        this.maxAge = p.maxAge;
        this.prevLocation = p.prevLocation;
        this.rotation = p.rotation;
        this.scale = p.scale;
        this.size = p.size;
        this.texture = p.texture;
        this.velocity = p.velocity;
        this.visible = p.visible;
    }
    
    public boolean isDead()
    {
        if(age < maxAge)
            return false;
        else
            return !keepAlive;
    }
}
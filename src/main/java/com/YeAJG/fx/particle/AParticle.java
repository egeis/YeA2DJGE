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

import java.util.ArrayList;
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
        
    protected ArrayList<Texture> textures;
    public Color color;
    
    protected void setDefaults(Particle p)
    {
        this.acceleration = new Vector3f(p.acceleration);
        this.location = new Vector3f(p.location);
        this.prevLocation = new Vector3f(p.prevLocation);
        this.rotation = new Vector3f(p.rotation);
        this.scale = new Vector3f(p.scale);
        this.size = new Vector3f(p.size);
        this.velocity = new Vector3f(p.velocity);
        this.textures = new ArrayList(p.textures);
        
        this.color = new Color(p.color);
        this.keepAlive = p.keepAlive;
        this.maxAge = p.maxAge;
        this.visible = p.visible;
        this.age = p.age;
        this.ageStep = p.ageStep;
    }
    
    public boolean isDead()
    {
        if(age < maxAge)
            return false;
        else
            return !keepAlive;
    }
    
    public Texture getTexture(int num)
    {
        return textures.get(num);
    }
    
    public void addTexture(int num, String path)
    {
        
    }
}
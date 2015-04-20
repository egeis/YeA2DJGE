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
        
    public Vector3f prevLocation;    //x, y, z 
            
    public boolean keepAlive = false;
        
    protected ArrayList<Texture> textures;
    public Color color;
        
    protected void setDefaults(Particle p)
    {
        this.acceleration = new Vector3f(p.acceleration);
        this.location = new Vector3f(p.location);
        this.prevLocation = new Vector3f(p.prevLocation);
        this.rotation = p.rotation;
        this.scale = new Vector3f(p.scale);
        this.size = new Vector3f(p.size);
        this.velocity = new Vector3f(p.velocity);
        this.spin = p.spin;
        
        this.textures = new ArrayList(p.textures);
        
        this.color = new Color(p.color);
        this.keepAlive = p.keepAlive;
        this.visible = p.visible;
    }
    
    public boolean isDead()
    {
        if(keepAlive) return false;
        
        if(parameters.containsKey("Age.Max") && 
                parameters.containsKey("Age.Count"))         
            if( ((float) parameters.get("Age.Count")) < 
                    ((float) parameters.get("Age.Max")))
                return false;
        
        if(parameters.containsKey("Death.FADE.Step") &&
                parameters.containsKey("Death.FADE.End"))
            if(this.color.getAlpha() > (Integer) this.parameters.get("Death.FADE.End"))
            {
                this.color.setAlpha( this.color.getAlpha() - (int) parameters.get("Death.FADE.Step") );
                return false;
            }
        
        return !keepAlive;
    }
    
    public Texture getTexture(int num)
    {
        return textures.get(num);
    }
    
    public void addTexture(int num, String path)
    {
        
    }

    public Vector3f getPrevLocation() {
        return prevLocation;
    }

    public void setPrevLocation(Vector3f prevLocation) {
        this.prevLocation = new Vector3f(prevLocation);
    }
}
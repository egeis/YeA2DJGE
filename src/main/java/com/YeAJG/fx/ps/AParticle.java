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
package main.java.com.YeAJG.fx.ps;

import main.java.com.YeAJG.game.Entity.AEntity;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public abstract class AParticle extends AEntity {      
                    
    public boolean keepAlive = false;
    public float age;
    public float mass;
                
    protected void setDefaults(Particle p)
    {
        this.acceleration = new Vector3f(p.acceleration);
        this.modelPos = new Vector3f(p.modelPos);
        this.modelAngle = new Vector3f(p.modelAngle);
        this.modelScale = new Vector3f(p.modelScale);
        this.velocity = new Vector3f(p.velocity);
        this.spin = p.spin;
                
        this.color = new Color(p.color);
        this.keepAlive = p.keepAlive;
        this.visible = p.visible;
        
        this.age = p.age;
        this.mass = p.mass;
    }
    
    public void tick() {
        Vector3f.add(this.modelAngle, this.spin, this.modelAngle);
        Vector3f.add(this.velocity, this.acceleration, this.velocity);
        Vector3f.add(this.modelPos, this.velocity, this.modelPos);
    }
    
    public boolean isDead()
    {
        /*if(keepAlive) return false;
        
        if(parameters.containsKey("Age.Max") && 
                parameters.containsKey("Age.Count"))         
            if( ((float) parameters.get("Age.Count")) < 
                    ((float) parameters.get("Age.Max")))
                return false;
        
        
        if(parameters.containsKey("Age.Death.FADE.Step") &&
                parameters.containsKey("Age.Death.FADE.End"))
            if(this.color.getAlpha() > (Integer) this.parameters.get("Death.FADE.End"))
            {
                this.color.setAlpha( this.color.getAlpha() - (int) parameters.get("Death.FADE.Step") );
                return false;
            }
        */
        return !keepAlive;
    }
}
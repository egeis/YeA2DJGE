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

import main.java.com.YeAJG.api.IEntity;
import main.java.com.YeAJG.api.IParticle;
import main.java.com.YeAJG.game.Entity.Entity;


/**
 * Abstract Particle Class.
 * @author Richard Coan
 */
public abstract class Particle extends Entity implements IEntity, IParticle {      
    protected boolean keepAlive = false;
    protected float age = 1.0f;
    protected float decay = 0.01f;
    protected float mass;
  
    /*public void Tick() {        
        Vector3f.add(this.modelAngle, this.spin, this.modelAngle);
        Vector3f.add(this.velocity, this.acceleration, this.velocity);
        Vector3f.add(this.modelPos, this.velocity, this.modelPos);
    }*/
    
    public boolean isAlive()
    {       
        if(keepAlive) return keepAlive;
        else if(age < 0.0f) return false;
        return true;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public float getDecay() {
        return decay;
    }

    /**
     * Sets the decay rate, must be a positive non-zero number
     * @param decay rate.
     * @return 0 if not set or decay value if set.
     */
    public float setDecay(float decay) {
        if(decay <= 0.0f) return 0.0f;
        this.decay = decay;
        return decay;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }    
}
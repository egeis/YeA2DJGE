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
package main.java.com.YeAJG.game.entity;
import main.java.com.YeAJG.api.entity.IEntity;
import main.java.com.YeAJG.api.entity.IParticle;
import main.java.com.YeAJG.game.utils.MathUtil;

/**
 * Abstract Particle Class.
 * @author Richard Coan
 */
public abstract class Particle extends Entity implements IEntity, IParticle {      
    protected boolean keepAlive = false;
    protected float age = 1.0f;
    protected float decay = 0.01f;
    protected float mass = 0.0001f;
    protected float magnitude = 0.0001f;
    protected float angle = 0;

    @Override
    public void Render(float interpolation) {
        modelPos.x += magnitude * MathUtil.cos(this.angle) * interpolation * 20;
        modelPos.y += magnitude * MathUtil.sin(this.angle) * interpolation * 20;
        
        super.Render(interpolation); //To change body of generated methods, choose Tools | Templates.
    }
        
    @Override
    public boolean isAlive()
    {       
        if(keepAlive) return keepAlive;
        else if(age < 0.0f) return false;
        return true;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public float getAge() {
        return age;
    }

    public float getDecay() {
        return decay;
    }

    public float getMass() {
        return mass;
    }
    
    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }
    
    /**
     * Sets the age, must be a positive non-zero number;
     * @param age
     * @return age on success or 0.0
     */
    public float setAge(float age) {
        if(age <= 0.0f) return 0.0f;
        this.age = age;
        return age;
    }
    
    /**
     * Sets the magnitude, must be a positive non-zero number.
     * @param magnitude
     * @return magnitude on success or 0.0
     */
    public float setMagnitude(float magnitude) {
        if( magnitude <= 0.0f ) return 0.0f;
        this.magnitude = magnitude;
        return magnitude;
    }

    /**
     * Sets the mass, must be a positive non-zero number.
     * @param mass
     * @return mass on success or 0.0
     */
    public float setMass(float mass) {
        if(mass <= 0.0f) return 0.0f;
        this.mass = mass;
        return mass;
    }  
    
    /**
     * Sets the decay rate, must be a positive non-zero number
     * @param decay rate.
     * @return decay on success or 0.0
     */
    public float setDecay(float decay) {
        if(decay <= 0.0f) return 0.0f;
        this.decay = decay;
        return decay;
    }
}
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
package main.java.com.example.emitters;

import main.java.com.YeAJG.api.entity.IEntity;
import main.java.com.YeAJG.api.entity.IParticle;
import main.java.com.YeAJG.api.physics.IForce;
import main.java.com.YeAJG.game.entity.Particle;
import main.java.com.YeAJG.game.utils.MathUtil;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Richard Coan
 */
public class ExampleParticle extends Particle implements IParticle, IEntity, IForce {
    
    public ExampleParticle() {
        this.age = 1.2f;
        this.decay = 0.01f;
    }    
    
    @Override
    public boolean isAlive() {
        return super.isAlive();
    }

    @Override
    public void Tick() {
        applyForce(5.0f, 5.0f, 5.0f, 1.0f, false);
        super.Tick();
    }

    @Override
    public void Render(float interpolation) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        super.Render(interpolation);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    @Override
    public void applyForce(float x, float y, float z, float mass, boolean isAttractor) {
        float f, mX, mY, mZ, angle;

        if ((modelPos.x - x) * (modelPos.x - x) + 
                (modelPos.y - y) * (modelPos.y - y) +
                (modelPos.z - z) * (modelPos.z - z) != 0)
        {
                f = this.mass * mass * 0.15f;
                mX = (this.mass * modelPos.x + mass * x) / (this.mass + mass);
                mY = (this.mass * modelPos.y + mass * y) / (this.mass + mass);
                mZ = (this.mass * modelPos.z + mass * z) / (this.mass + mass);
                
                angle = isAttractor 
                        ? MathUtil.findAngle(x,y,z,
                            mX - modelPos.x, 
                            mY - modelPos.y, 
                            mZ - modelPos.z)
                        : MathUtil.findAngle(x,y,z,
                            modelPos.x - mX, 
                            modelPos.y - mY, 
                            modelPos.z - mZ);

                mX = f * MathUtil.cos(angle);
                mY = f * MathUtil.sin(angle);
                mZ = f * MathUtil.sin(angle);

                mX += magnitude * MathUtil.cos(this.angle);
                mY += magnitude * MathUtil.sin(this.angle);
                mZ += magnitude * MathUtil.sin(this.angle);
                
                magnitude = (float) Math.sqrt(mX * mX + mY * mY);
                logger.info(magnitude);
                
                this.angle = MathUtil.findAngle(x,y,z,mX, mY,mZ);
        }
    }
}

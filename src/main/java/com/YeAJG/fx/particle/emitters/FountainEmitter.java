/*
 * The MIT License
 *
 * Copyright 2015 Richard.
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
package main.java.com.YeAJG.fx.particle.emitters;

import main.java.com.YeAJG.fx.particle.AEmitUpdater;
import main.java.com.YeAJG.fx.particle.IEmitUpdater;
import main.java.com.YeAJG.fx.particle.Particle;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard
 */
public class FountainEmitter extends AEmitUpdater implements IEmitUpdater {

    public FountainEmitter()
    {
        //Does Nothing!
    }    
        
    @Override
    public void generate(int num) {
        int i = 0;
        if(state == null) return;
        
        while(i < num && list.size() < limit)
        {
            list.add(new Particle(
                state.location,
                state.size,
                state.scale,
                state.rotation,
                state.velocity,
                state.acceleration,
                state.age,
                state.ageStep,
                state.maxAge,
                state.keepAlive
            ));
            
            i++;
        }
    }

    @Override
    public void update(Particle p) {
        p.prevLocation.x = location.x;
        p.prevLocation.y = location.y;
        p.prevLocation.z = location.z;

        Vector3f.add(p.velocity, p.acceleration, p.velocity);
        Vector3f.add(p.location, p.velocity, p.location);

        p.age += p.ageStep;
    }

    @Override
    public void preDraw(Particle p) {
    }

    @Override
    public void draw(Particle p) {
        GL11.glColor4b((byte)(p.color.getRedByte()-128),
            (byte)(p.color.getGreenByte()-128),
            (byte)(p.color.getBlueByte()-128),
            (byte)(p.color.getAlphaByte()-128));
        
        GL11.glLineWidth(30.8f);
        GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex3f(p.prevLocation.x, p.prevLocation.y, p.prevLocation.z);
            GL11.glVertex3f(p.location.x, p.location.y, p.location.z);
        GL11.glEnd();
    }

    @Override
    public void postDraw(Particle p) {
    }
       
}

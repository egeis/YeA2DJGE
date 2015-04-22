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

import java.util.HashMap;
import main.java.com.YeAJG.fx.particle.AEmitUpdater;
import main.java.com.YeAJG.fx.particle.IEmitUpdater;
import main.java.com.YeAJG.fx.particle.Particle;
import main.java.com.YeAJG.fx.particle.graphics.filters.MotionBlurPostFilter;
import main.java.com.YeAJG.game.Game;
import main.java.com.YeAJG.game.utils.Randomizer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public class CodeEmitter extends AEmitUpdater implements IEmitUpdater {
    
    private MotionBlurPostFilter mb;
    
    public CodeEmitter()
    {
        mb = new MotionBlurPostFilter(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, 0.7f, 0);
    }    
        
    @Override
    public void generate(int num) {
        int i = 0;
        if(state == null) {
            logger.info("State not Initialized!");
            return;
        }
        Vector3f nsize;
        float modifier = 0.0f, rot = 0.0f;
        int n;
        
        while(i < num && list.size() < limit)
        {            
            if(state.parameters.containsKey("Distance.Min") || 
                    state.parameters.containsKey("Distance.Max"))
                modifier = Randomizer.getValue(
                        (float) state.parameters.get("Distance.Min"), 
                        (float) state.parameters.get("Distance.Max"));
            
            nsize = new Vector3f(state.size.x * modifier,
                    state.size.y * modifier, state.size.z * modifier);
            
            n = Randomizer.getValue(0, 255);
            rot = Randomizer.getValue(-2.0f, 2.f);
            logger.info(rot);
            
            Vector3f rotation = new Vector3f(state.rotation);
            rotation.z = rotation.z + rot;
            
            list.add(new Particle(
                new Vector3f(
                        Randomizer.getValue(location.x, size.x),
                        Randomizer.getValue(location.y, size.y), 
                        0f),
                new Vector3f(nsize),
                new Vector3f(state.scale),
                new Vector3f(state.velocity),
                new Vector3f(state.acceleration),
                rotation,
                new Vector3f(state.spin),               
                new HashMap(state.parameters),
                new Color(n,n,255,255),
                state.keepAlive
            ));
                        
            i++;
        }
    }

    @Override
    public void update(Particle p) {
        p.prevLocation.x = p.location.x;
        p.prevLocation.y = p.location.y;

        Vector3f.add(p.velocity, p.acceleration, p.velocity);
        Vector3f.add(p.location, p.velocity, p.location);
        
        p.parameters.put("Age.Count",(float) p.parameters.get("Age.Count") + (float) p.parameters.get("Age.Step"));
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
        
        GL11.glPushMatrix();
            GL11.glTranslatef(p.location.x,p.location.y,0);
            GL11.glRotated(p.rotation.z, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-p.location.x,-p.location.y,0);
            
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2f(p.location.x - p.size.x, p.location.y - p.size.y);
                GL11.glVertex2f(p.location.x + p.size.x, p.location.y - p.size.y);
                GL11.glVertex2f(p.location.x + p.size.x, p.location.y + p.size.y);
                GL11.glVertex2f(p.location.x - p.size.x, p.location.y + p.size.y);                
            GL11.glEnd();
        GL11.glPopMatrix();
        
        mb.apply();
    }

    @Override
    public void postDraw(Particle p) {
    }
       
}

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

import main.java.com.YeAJG.api.entity.IEmitter;
import main.java.com.YeAJG.api.entity.IEntity;
import main.java.com.YeAJG.game.entity.Emitter;
import main.java.com.YeAJG.game.entity.Particle;
import main.java.com.YeAJG.game.utils.Randomizer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public class ExampleEmitter extends Emitter implements IEmitter, IEntity
{    

    public ExampleEmitter(Particle p, int num_per_tick, int particle_limit)
    {
        this.particle = p;
        this.num_per_tick = num_per_tick;
        this.particle_limit = particle_limit;
    }
    
    public void Setup(Vector3f pos, Vector3f angle, Vector3f[] vertex)
    {
        //this.Setup(pos, angle, null, "", "", null, vertex, null, null);
    }
        
    @Override
    public void Tick()
    {
        Generate();
        
        particles.stream().forEach((p) -> {        
             //p.applyForce(15.0f, -15.0f, 1.0f, true);
             p.Tick();
        });
    }

    @Override
    public void Render(float interpolation)
    {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        particles.stream().forEach((p) -> {        
            p.Render(interpolation);
        });
                
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    @Override
    public void Generate()
    {
        int i = 0;
        
        while(i < num_per_tick && (particles.size() < particle_limit) )
        { 
            ExampleParticle p = new ExampleParticle();
            p.Setup(
                new Vector3f(Randomizer.getValue(-15.0f, 15.0f), 15.0f, 0), 
                particle.getModelAngle(), 
                particle.getModelScale(),
                "assets/shaders/vertex.glsl", 
                "assets/shaders/fragment.glsl", 
                "assets/textures/snowflake.png",
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
                    new Vector3f(1,1,1)
                },
                new Vector2f[] {
                    new Vector2f(0, 0),       
                    new Vector2f(0, 1),
                    new Vector2f(1, 1),
                    new Vector2f(1, 0)
                }
            );
            
            p.setModelPos(Vector3f.add(
                    p.getModelPos(), 
                    new Vector3f(
                            Randomizer.getValue(-1.0f, 1.0f),
                            Randomizer.getValue(-1.0f, 1.0f),
                            Randomizer.getValue(-1.0f, 1.0f)
                    ), 
                    p.getModelPos())
            );
            
            //p.setModelAccel(new Vector3f(0.0f, 0.01f, 0.0f));
            p.setModelVelcity(new Vector3f(0.0f, -0.1f, 0.0f));
                
            particles.add(p);
       
            i++;
        }
    }
}
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
 * HTE SOFTWARE.
 */
package main.java.com.YeAJG.fx.ps;

import java.util.ArrayList;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public class Particle extends AParticle {
          
    /**
     * @param location
     * @param size
     * @param scale
     * @param rotation
     * @param velocity
     * @param acceleration
     * @param spin
     * @param parameters
     * @param color
     * @param keepAlive 
     */
    public Particle(Vector3f location, Vector2f size, Vector3f scale, Vector3f velocity, 
            Vector3f acceleration, Vector3f rotation, Vector3f spin, Map<String, Object> parameters, Color color, boolean keepAlive) {
        this.location = location;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.rotation = rotation;
        this.scale = scale;
        this.size = size;
        this.spin = spin;
        this.keepAlive = keepAlive;
        this.visible = true;
        this.textures = new ArrayList();
        this.color = new Color(color);
        this.parameters = parameters;
    } 

    @Override
    public void tick() {
        Vector3f.add(this.rotation, this.spin, this.rotation);
        Vector3f.add(this.velocity, this.acceleration, this.velocity);
        Vector3f.add(this.location, this.velocity, this.location);
        
        if(this.parameters.containsKey("Age.Count") && this.parameters.containsKey("Age.Step"))
            this.parameters.put("Age.Count",
                    ((float) this.parameters.get("Age.Count") + 
                    (float) this.parameters.get("Age.Step")));
    }

    @Override
    public void render() {
        GL11.glColor4b((byte)(this.color.getRedByte()-128),
            (byte)(this.color.getGreenByte()-128),
            (byte)(this.color.getBlueByte()-128),
            (byte)(this.color.getAlphaByte()-128));
        
        GL11.glPushMatrix();
            GL11.glTranslatef(this.location.x,this.location.y,0);
            GL11.glRotated(this.rotation.z, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-this.location.x,-this.location.y,0);
            
            GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2f(this.location.x - this.size.x, this.location.y - this.size.y);
                GL11.glVertex2f(this.location.x + this.size.x, this.location.y - this.size.y);
                GL11.glVertex2f(this.location.x + this.size.x, this.location.y + this.size.y);
                GL11.glVertex2f(this.location.x - this.size.x, this.location.y + this.size.y);                
            GL11.glEnd();
        GL11.glPopMatrix();
    }
    
    
}
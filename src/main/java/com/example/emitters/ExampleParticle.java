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
package main.java.com.example.emitters;

import main.java.com.YeAJG.api.IEntity;
import main.java.com.YeAJG.api.IParticle;
import main.java.com.YeAJG.fx.ps.Particle;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL14.GL_COLOR_SUM;
import static org.lwjgl.opengl.GL14.glSecondaryColor3f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard
 */
public class ExampleParticle extends Particle implements IParticle, IEntity {

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
        
        super.Tick();
    }

    @Override
    public void Render(float interpolation) {
        super.Render(interpolation);
        
        GL11.glEnable(GL_COLOR_SUM);
        glSecondaryColor3f(1.0f, 0.0f, 0.0f);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
        // render texture
        GL11.glDisable(GL_COLOR_SUM);

        
    }
    
    
    
}

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
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard
 */
public class ExampleParticle extends Particle implements IParticle, IEntity {

    public ExampleParticle() {
    
    }    
    
    @Override
    public boolean isAlive() {
        return super.isAlive();
    }

    @Override
    public void Setup(Vector3f pos, Vector3f angle, Vector3f scale,
            String shaderPath, String fragmentPath, String[] texturePaths,
            Vector3f[] vertex, Vector3f[] color, Vector2f[] uv) {
        this.setModelPos(pos);
        this.setModelAngle(angle);
        this.setModelScale(scale);
    }

    @Override
    public void Tick() {
    }

    @Override
    public void Render(float interpolation) {
    }
    
    
    
}

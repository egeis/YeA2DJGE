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
package main.java.com.YeAJG.fx.particle;

import java.util.ArrayList;
import java.util.List;
import main.java.com.YeAJG.game.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Richard Coan
 */
public abstract class AEmitUpdater implements IEmitUpdater {
    protected static final Logger logger = LogManager.getLogger( Game.class.getName() );
    
    protected int limit;
    protected List<Particle> list = new ArrayList();
    protected Particle state = null;
    //protected Future<Particle> state;
    
    protected Vector3f size;
    protected Vector3f location;    
    
    @Override
    public abstract void generate(int num);
    
    @Override
    public abstract void update(Particle p);
    
    @Override
    public abstract void preDraw(Particle p);
    
    @Override
    public abstract void draw(Particle p);
    
    @Override
    public abstract void postDraw(Particle p);

    @Override
    public List<Particle> getList() {
        return list;
    } 

    @Override
    public void setState(Particle state) {
        this.state = state;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void setSize(Vector3f size) {
        this.size = size;
    }

    @Override
    public void setLocation(Vector3f location) {
        this.location = location;
    }
    
}
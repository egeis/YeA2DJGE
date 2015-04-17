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
package main.java.com.YeAJG.shapes.primatives;

import java.awt.Point;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author egeis
 */
public class Quad {
    protected Point p1;
    protected Point p2;
    protected Point p3;
    protected Point p4;
    
    protected float cr;
    protected float cg;
    protected float cb;
    protected float ca;
    
    public Quad(Point p1, Point p2, Point p3, Point p4, float cr, float cg, float cb, float ca)
    {
        this(p1, p2, p3, p4, cr, cg, cb);
        this.ca = ca;
    }
    
    public Quad(Point p1, Point p2, Point p3, Point p4, float cr, float cg, float cb)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        
        this.cr = cr;
        this.cb = cb;
        this.cg = cg;
        this.ca = 1.0f;
    }
    
    public void draw()
    {
        // set the color of the quad (R,G,B,A)
        glColor4f(cr,cg,cb,ca);

        // draw quad
        glBegin(GL_QUADS);
            glVertex2f(p1.x, p1.y);
            glVertex2f(p2.x, p2.y);
            glVertex2f(p3.x, p3.y);
            glVertex2f(p4.x, p4.y);
        glEnd();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.YeAJG.shapes.primatives;

import java.awt.Color;
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

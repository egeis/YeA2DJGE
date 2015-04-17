/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.YeAJG.fx.particle;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Richard Coan
 */
public abstract class ParticleObject {
    protected double age;
    protected double maxAge;
    protected double ageStep;
    
    protected int width;
    protected int height;
    
    protected Vector3f location;
    protected Vector3f velocity;
    protected Vector3f acceleration;
    
    protected float px;
    protected float py;
    protected float pz;
        
    protected boolean keepAlive;
    protected boolean visible;
    
    protected Texture texture;
    protected Color color;
    
    public abstract void draw();    
}

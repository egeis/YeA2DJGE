/**
 * Code from: http://www.java-gaming.org/index.php/topic,6250.
 * Modifications made by Richard Coan for use with LWJGL. 
 * Original based on JOGL.
 */

package main.java.com.YeAJG.fx.particle.graphics.filters;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTTextureRectangle;
import org.lwjgl.opengl.GL11;

public class MotionBlurPostFilter
{
   private int textureHandle;
   private int width;
   private int height;
   private float alpha;
   private float zoom;
   private int frameCount;

   public MotionBlurPostFilter(int width, int height, float alpha, float zoom)
   {
       this.width = width;
       this.height = height;
       this.alpha = alpha;
       this.zoom = zoom;
       setupTexture();
   }

   public void apply()
   {
       GL11.glEnable(EXTTextureRectangle.GL_TEXTURE_RECTANGLE_EXT);
       GL11.glBindTexture(EXTTextureRectangle.GL_TEXTURE_RECTANGLE_EXT, textureHandle);

       if (frameCount++ > 0)
       {
           GL11.glDisable(GL11.GL_LIGHTING);
           GL11.glDisable(GL11.GL_DEPTH_TEST);

           GL11.glEnable(GL11.GL_BLEND);
           GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
           GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_REPLACE);
           GL11.glColor4f(0, 0, 0, alpha);

           viewOrtho();
           GL11.glBegin(GL11.GL_QUADS);
           {
               GL11.glTexCoord2f(width, 0);
               GL11.glVertex2f(-zoom, -zoom);
               GL11.glTexCoord2f(width, height);
               GL11.glVertex2f(-zoom, 1.0f + zoom);
               GL11.glTexCoord2f(0, height);
               GL11.glVertex2f(1.0f + zoom, 1.0f + zoom);
               GL11.glTexCoord2f(0, 0);
               GL11.glVertex2f(1.0f + zoom, -zoom);
           }
           GL11.glEnd();
           viewPerspective();

           GL11.glEnable(GL11.GL_DEPTH_TEST);
       }

       GL11.glCopyTexSubImage2D(EXTTextureRectangle.GL_TEXTURE_RECTANGLE_EXT, 0, 0, 0, 0, 0, width, height);
       GL11.glDisable(EXTTextureRectangle.GL_TEXTURE_RECTANGLE_EXT);
   }

   private void setupTexture()
   {
       IntBuffer tmp = BufferUtils.createIntBuffer(1);
       int[] textureHandles = new int[1];
       GL11.glGenTextures(tmp);
       textureHandle = textureHandles[0];

       IntBuffer textureData = BufferUtils.createIntBuffer(width * height * 3);

       GL11.glEnable(EXTTextureRectangle.GL_TEXTURE_RECTANGLE_EXT);
       GL11.glBindTexture(EXTTextureRectangle.GL_TEXTURE_RECTANGLE_EXT, textureHandle);
       GL11.glTexImage2D(EXTTextureRectangle.GL_TEXTURE_RECTANGLE_EXT, 0, 3, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_INT, textureData);
       GL11.glTexParameteri(EXTTextureRectangle.GL_TEXTURE_RECTANGLE_EXT, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
       GL11.glTexParameteri(EXTTextureRectangle.GL_TEXTURE_RECTANGLE_EXT, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
       GL11.glDisable(EXTTextureRectangle.GL_TEXTURE_RECTANGLE_EXT);
   }

   void viewOrtho()
   {
       GL11.glMatrixMode(GL11.GL_PROJECTION);
       GL11.glPushMatrix();
       GL11.glLoadIdentity();
       GL11.glOrtho(1, 0, 0, 1, -1, 1000000);
       GL11.glMatrixMode(GL11.GL_MODELVIEW);
       GL11.glPushMatrix();
       GL11.glLoadIdentity();
   }

   void viewPerspective()
   {
       GL11.glMatrixMode(GL11.GL_PROJECTION);
       GL11.glPopMatrix();
       GL11.glMatrixMode(GL11.GL_MODELVIEW);
       GL11.glPopMatrix();
   }
}
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
package game;

import game.io.ConfigHandler;
import game.io.InputHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
 
import java.nio.ByteBuffer;
 
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 *
 * @author Richard Coan
 */
public class Game implements Runnable {
    private static Game instance = null;
    private static final Logger LOG = LogManager.getLogger( Game.class.getName() );
        
    protected ConfigHandler Config;
    protected InputHandler Input;
    
//    private GLFWErrorCallback errorCallback;
//    private GLFWKeyCallback   keyCallback;
    
    private long window;

    public static Game getInstance() {
        if(instance == null) instance = new Game();
        return instance;
    }
    
    private Game() {                
        Config = ConfigHandler.getInstance();
        Input = InputHandler.getInstance();
    }
    
    @Override
    public void run() {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
 
        try {
            init();
            loop(); 
            
            // Release window and window callbacks
//            glfwDestroyWindow(window);
//            keyCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
//            glfwTerminate();
//            errorCallback.release();
        }
    }
 
    private void init() {
//        // Setup an error callback. The default implementation
//        // will print the error message in System.err.
//        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
// 
//        // Initialize GLFW. Most GLFW functions will not work before doing this.
//        if ( glfwInit() != GL11.GL_TRUE )
//            throw new IllegalStateException("Unable to initialize GLFW");
// 
//        // Configure our window
//        glfwDefaultWindowHints(); // optional, the current window hints are already the default
//        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
//        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
 
        int WIDTH = 300;
        int HEIGHT = 300;
 
//        // Create the window
//        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
//        if ( window == NULL )
//            throw new RuntimeException("Failed to create the GLFW window");
// 
//        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
//        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
//            @Override
//            public void invoke(long window, int key, int scancode, int action, int mods) {
//                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
//                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
//            }
//        });
// 
//        // Get the resolution of the primary monitor
//        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//        // Center our window
//        glfwSetWindowPos(
//            window,
//            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
//            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
//        );
// 
//        // Make the OpenGL context current
//        glfwMakeContextCurrent(window);
//        // Enable v-sync
//        glfwSwapInterval(1);
// 
//        // Make the window visible
//        glfwShowWindow(window);
    }
 
    private void loop() {
//        // This line is critical for LWJGL's interoperation with GLFW's
//        // OpenGL context, or any context that is managed externally.
//        // LWJGL detects the context that is current in the current thread,
//        // creates the ContextCapabilities instance and makes the OpenGL
//        // bindings available for use.
//        GLContext.createFromCurrent();
//        
//        // Run the rendering loop until the user has attempted to close
//        // the window or has pressed the ESCAPE key.
//        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
//            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
//
//            // set the color of the quad (R,G,B,A)
//            GL11.glColor3f(0.5f,0.5f,1.0f);
//
//            // draw quad
//            GL11.glBegin(GL11.GL_QUADS);
//                GL11.glVertex2f(100,100);
//                GL11.glVertex2f(100+200,100);
//                GL11.glVertex2f(100+200,100+200);
//                GL11.glVertex2f(100,100+200);
//            GL11.glEnd();
//            
//            glfwSwapBuffers(window); // swap the color buffers           
//            
//            // Poll for window events. The key callback above will only be
//            // invoked during this call.
//            glfwPollEvents();
//        }
    }
    
}

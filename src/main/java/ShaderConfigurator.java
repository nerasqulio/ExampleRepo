/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

/**
 *
 * @author arda.eksi
 */
public class ShaderConfigurator {

    public static GLWindow window;

    public static void main(String[] args) {

        System.setProperty("sun.awt.noerasebackground", "true");
        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);
        window = GLWindow.create(capabilities);
       
        final FPSAnimator animator = new FPSAnimator(window, 165);

        window.addGLEventListener(new GLDisplay());
        window.swapBuffers();
        window.setSize(1024, 768);
        window.setFullscreen(false);
        window.setTitle("GLExperimental");
        window.setVisible(true);

        animator.start();

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyed(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });

        window.addKeyListener(new KeyAdapter() {

            public void keyPressed(final KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    animator.stop();
                    window.destroy();
                    //System.exit(0);

                }
            }
        });

    }
}

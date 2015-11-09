package org.aimos.gsd;

import org.aimos.abstractg.core.Launcher;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;


public class IOSLauncher extends IOSApplication.Delegate {
    /**
     * Se llama cuando se crea por primera vez la actividad.
     * @return la aplicacion
     */
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(new Launcher(), config);
    }

    /**
     * iniciará nuestra aplicación
     * Ejecuta el lanzador de la aplicacion
     * @param argv
     */
    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}
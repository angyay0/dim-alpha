//AndroidLauncher.java
package org.aimos.dim.android;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import org.aimos.abstractg.core.Launcher;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * Clase para iniciar el hilo principal y comenzar con la pantalla siguiente ddel juego
 *
 * @author libGDX
 * @version 1.0.3
 * @company AIMOS
 */

public class DimensionsLauncher extends  AndroidApplication {

	private Launcher launcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Se crea una configuracion para el manejo del juego con caracteristicas especiales
		//si las llegase a emplear
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//config.
		//Log.d("Cores","->"+getCores()+"<-");
		launcher = new Launcher();
		launcher.socialMedia = new AndroidSocialMedia(getContext());
		//Metodo para inicial0000000izar la siguiente vista
		//initialize(new MainScreenFX(), config);
		//initialize(new PhysicsBox2D(), config);
		//initialize(new ScreenWithSceneLoader(), config);
		config.useWakelock = true;
		initialize(launcher, config);


		//// Sondea y oculta desde KITKAT(4.4)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	//		hideVirtualButtons();
		}
		//Bloque para realizar ajustes y ocultar botones virtuales
	}

	/*@Override
	public void onBackPressed(){
		//Disabled for controlling from Inside libGDX
	//	Game g = (Game) launcher;
	//	g.pause();
	}
*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK ){
			//Toast.makeText(this, "Back Pressed", Toast.LENGTH_LONG ).show();
			return true;
		}
		if( keyCode == KeyEvent.KEYCODE_MENU){
			//Toast.makeText(this, "Back Pressed", Toast.LENGTH_LONG ).show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Metodo que configura y oculta botones virtuales
	 **/
	@TargetApi(19)
	private void hideVirtualButtons() {
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
	}

	/**
	 * Metodo complementario para asegurar ocultar los botones virtuales
	 **/
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			// Sondea y oculta desde KITKAT(4.4)
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				hideVirtualButtons();
			}
		}
	}

	/**
	 * Metodo para determinar el numero de cores del procesador
	 *
	 * Code Based on StackOverflow Post
	 * @url http://stackoverflow.com/questions/7592843/is-there-any-api-that-tells-whether-an-android-device-is-dual-core-or-not/7593003#7593003
	 *
	 * @return el numero de nucleos
	 */
	public int getCores(){
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				//Check if filename is "cpu", followed by a single digit number
				if(Pattern.matches("cpu[0-9]", pathname.getName())) {
					return true;
				}
				return false;
			}
		}

		try {
			//Get directory containing CPU info
			File dir = new File("/sys/devices/system/cpu/");
			//Filter to only list the devices we care about
			File[] files = dir.listFiles(new CpuFilter());
			//Return the number of cores (virtual CPU devices)
			return files.length;
		} catch(Exception e) {
			//Default to return 1 core
			Log.d("Error Getting CPU CORES",e.getMessage());
			return 1;
		}
	}


}

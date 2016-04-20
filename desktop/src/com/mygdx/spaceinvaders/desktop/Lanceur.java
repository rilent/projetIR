package com.mygdx.spaceinvaders.desktop;

import java.io.File;

public class Lanceur {
	public static void main (String[] arg) {
		
		launch(DesktopLauncher.class);		
		//launch(DesktopLauncher.class);
	}
	
	public static int launch(Class c) {
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = c.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
        try{
            Process process = builder.start();
            process.waitFor();	
            return process.exitValue();
        } catch(Exception e){
            e.printStackTrace();
            return 1;
        }
    }
	
	
}

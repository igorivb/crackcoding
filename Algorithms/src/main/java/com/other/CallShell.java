package com.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CallShell {

	public static void main(String[] args) throws Exception {
		
		String[] cmdarray = new String[2];
		cmdarray[0] = "/home/hduser/run.sh";
		cmdarray[1] = "a=b";
	    //params[1] = "C:\\Users\\user\\Desktop\\images.jpg";
	    //params[2] = "C:\\Users\\user\\Desktop\\images2.txt";
		
		File f = new File(cmdarray[0]);
		if (f.setExecutable(true)) {
			System.out.println("Wan't able to make file executable");
		}
	    
	    
	    String[] envp = null;
	    File dir = new File("/home/hduser");
	    
	    Process pr = Runtime.getRuntime().exec(cmdarray, envp, dir);
	    
	    InputStream in = pr.getInputStream();
	    BufferedReader bin = new BufferedReader(new InputStreamReader(in));
	    String line;
	    while ((line = bin.readLine()) != null) {
	    	System.out.println(line);
	    }	    	    
	    pr.waitFor();	    	   
	    
	    System.out.println("Exit value: " + pr.exitValue());

	}

}

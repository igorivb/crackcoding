package com.threads;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * Try fork and join framework.
 */
public class FolderProcessor extends RecursiveTask<List<String>> {

	private static final long serialVersionUID = -1875228398474746302L;
	
	final File path;
	final String extension;
			
	public FolderProcessor(File path, String extension) {
		super();
		this.path = path;
		this.extension = extension;
	}

	@Override
	protected List<String> compute() {			
		List<String> result = new ArrayList<>();
		List<FolderProcessor> tasks = new ArrayList<>();
		
		File[] children = path.listFiles();		
		if (children != null) {
			for (File child : children) {
				if (child.isDirectory()) { //create sub-task				
					FolderProcessor task = new FolderProcessor(child, extension);
					task.fork();
					tasks.add(task);												
				} else { //compute directly				
					if (child.getName().endsWith(this.extension)) {
						result.add(child.getAbsolutePath());
					}
				}
			}
			
			for (FolderProcessor task : tasks) {
				result.addAll(task.join());
			}
		}						
		
		return result;
	}

	public static void main(String[] args) throws Exception {
		String extension = ".gz";
		File path = new File("/home");
		ForkJoinPool pool = new ForkJoinPool();
		
		Date start = new Date();
		
		FolderProcessor task = new FolderProcessor(path, extension);		
		pool.execute(task);
		
		do {
			 System.out.printf("******************************************\n");
	         System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
	         System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
	         System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
	         System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
	         System.out.printf("******************************************\n");
	         
	         TimeUnit.SECONDS.sleep(1);	         
		} while (!task.isDone());
		
		pool.shutdown();
		
		Date end = new Date();
		
		List<String> result = task.get();
		int i = 0;
		for (String s : result) {
			System.out.printf("%3d. %s%n", ++i, s);
		}
		
		System.out.printf("Duration: %d secs%n", (end.getTime() - start.getTime()) / 1000);
	}
}

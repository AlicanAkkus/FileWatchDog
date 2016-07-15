package com.wora.watch.dog;

import java.io.File;

public abstract class FileWatchdog extends Thread {
	/**
	 * The default delay between every file modification check, set to 60 seconds.
	 */
	static final public long DEFAULT_DELAY = 60000;
	/**
	 * The name of the file to observe for changes.
	 */
	protected String filename;

	/**
	 * The delay to observe between every check. By default set {@link #DEFAULT_DELAY}.
	 */
	protected long delay = DEFAULT_DELAY;

	File file;
	long lastModif = 0;
	boolean warnedAlready = false;
	boolean interrupted = false;


	protected void initialize(String filename, int watchDelay) throws Exception {
		this.filename = filename;
		delay = watchDelay;
		file = new File(filename);
		setDaemon(true);
		checkAndConfigure();
	}

	/**
	 * Set the delay to observe between each check of the file changes.
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	abstract protected void doOnChange() throws Exception;

	protected void checkAndConfigure() {
		boolean fileExists;
		try {
			fileExists = file.exists();
		} catch (SecurityException e) {
			System.err.println("Was not allowed to read check file existance, file:[" + filename + "].");
			interrupted = true; // there is no point in continuing
			return;
		}

		if (fileExists) {
			long l = file.lastModified(); // this can also throw a
											// SecurityException
			if (l > lastModif) { // however, if we reached this point this
				lastModif = l; // is very unlikely.
				try {
					doOnChange();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
				warnedAlready = false;
			}
		} else {
			if (!warnedAlready) {
				System.err.println("[" + file.getAbsolutePath() + "] does not exist.");
				warnedAlready = true;
			}
		}
	}

	@Override
	@SuppressWarnings("static-access")
	public void run() {
		while (!interrupted) {
			try {
				Thread.currentThread();
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// no interruption expected
			}
			try {
				checkAndConfigure();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

}
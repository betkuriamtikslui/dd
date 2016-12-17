package read;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FileReader implements Runnable {
	private ConcurrentLinkedQueue<String> list;
	private String location;
	public static boolean empty = false;

	public FileReader(ConcurrentLinkedQueue<String> list, String location) {
		this.location = location;
		this.list = list;

	}

	public void read(String file) {
		boolean first = true;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				if (!first) {
					list.add(line);
				} else {
					first = false;
				}
				if (list.size() > 500) {
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						continue;
					}
				}
			}
			br.close();
			empty = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		this.read(location);
	}

}

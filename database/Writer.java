package database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Writer implements Runnable {
	BufferedWriter bw;
	File output;
	static boolean go = true;
	private ConcurrentLinkedQueue<String> fetchFrom;
	private String header;
	public int count = 0;

	public Writer(File output, ConcurrentLinkedQueue<String> subredditQueue, String header) throws IOException {
		this.output = output;
		bw = new BufferedWriter(new FileWriter(output, true));
		this.header = header;
		this.fetchFrom = subredditQueue;
	}

	public void write() throws IOException {
		String line;
		String full;

		bw.write(header);
		bw.flush();
		while (go) {
			full = "";
			try {
				line = fetchFrom.remove();
				if (line == null) {
					throw new Exception("yolo");
				}
				bw.write(line);
				count++;
			} catch (Exception e) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e1) {
					continue;
				}
				if (FileReader.empty && fetchFrom.size() == 0) {
					go = false;
					try {
						bw.write(full);
						bw.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
				continue;
			}
		}

		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		try {
			write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package database;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader implements Runnable {
	private CircularList<String> list;
	private String location;
	public static boolean empty = false;

	public FileReader(CircularList<String> list, String location) {
		this.location = location;
		this.list = list;
	
	}

	public void read(String file) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
//				if (list.size() < list2.size() && list.size() < list3.size() && list.size() < list4.size()) {
//					list.add(line);
//
//				} else if (list2.size() < list3.size() && list4.size() > list2.size()) {
//					list2.add(line);
//				} else if (list4.size() > list3.size()) {
//					list3.add(line);
//				} else {
					list.add(line);
					if(list.size() > 500){
						Thread.sleep(100);
					}
//				}
//	
			}
			br.close();
			empty = true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		this.read(location);
	}

}

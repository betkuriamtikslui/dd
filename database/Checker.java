package database;

import java.util.concurrent.ConcurrentLinkedQueue;
import javax.script.*;

public class Checker implements Runnable {
	CircularList<String> list;
	@SuppressWarnings("restriction")
	ScriptEngine engine;
	public static boolean go = true;
	public static long start;
	public ConcurrentLinkedQueue<String> destination;

	public Checker(CircularList<String> list) {
		this.list = list;
		start = System.currentTimeMillis();
	}

	public static void setGo() {
		go = false;
	}

	@SuppressWarnings("restriction")
	public void run() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		int count = 15;
		while (go) {
			String full = "";
			for (int i = 0; i < count; i++) {
				String line = list.fetch();
				if (line != null) {

					try {
						engine.eval("var x  = " + line);
						String id = (String) engine.eval("x.id");
						String parent_id = (String) engine.eval("x.parent_id");
						String link_id = (String) engine.eval("x.link_id");
						String name = (String) engine.eval("x.name");
						String author = (String) engine.eval("x.author");
						String body = (String) engine.eval("x.body");
						body =body.replaceAll("'", "||");
						body = body.replace("\\", "/");
						String subreddit_id = (String) engine.eval("x.subreddit_id");
						String subreddit = (String) engine.eval("x.subreddit");
						int score = (Integer) engine.eval("x.score");
						int created_utc = Integer.parseInt((String) engine.eval("x.created_utc"));

						String s = "('" + id + "', '" + parent_id + "', '" + link_id + "', '" + name + "', '" + author
								+ "', '" + body + "', '" + subreddit_id + "', '" + subreddit + "', '"
								+ Integer.toString(score) + "', '" + Integer.toString(created_utc) + "')";
						if (i != count-1) {
							s+=",\n";
						}
						full+=s;
					} catch (ScriptException e) {
						continue;
					}
					
					
					
				}else{
					i--;
				}

			}
//			System.out.println(full);
			destination.add(full);

		}

	}
}

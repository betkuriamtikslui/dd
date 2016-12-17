package read;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@SuppressWarnings("restriction")
public class Checker implements Runnable {
	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("JavaScript");

	private ConcurrentLinkedQueue<String> destinationList;
	private ConcurrentLinkedQueue<String> list;
	private ConcurrentLinkedQueue<String> postDestination;
	public static boolean go = true;

	public Checker(ConcurrentLinkedQueue<String> list, ConcurrentLinkedQueue<String> destinationList,
			ConcurrentLinkedQueue<String> postDestination) {
		this.list = list;
		this.destinationList = destinationList;
		this.postDestination = postDestination;
	}

	@SuppressWarnings("restriction")
	public void run() {
		String line = "";
		String reddit;
		String post;
		int count = 20;
		String longPost;
		String longReddit;

		while (go) {
			longPost = "";
			longReddit = "";
			for (int i = 0; i < count; i++) {
				if ((line = list.poll()) != null) {
					try {
						engine.eval("var x  = " + line);
						String id = (String) engine.eval("x.id");
						String parent_id = (String) engine.eval("x.parent_id");
						String link_id = (String) engine.eval("x.link_id");
						String name = (String) engine.eval("x.name");
						String author = (String) engine.eval("x.author");
						String body = (String) engine.eval("x.body");
						body = body.replaceAll("[^a-zA-Z0-9 -]", "");
						String subreddit_id = (String) engine.eval("x.subreddit_id");
						String subreddit = (String) engine.eval("x.subreddit");
						int score = (Integer) engine.eval("x.score");
						int created_utc = Integer.parseInt((String) engine.eval("x.created_utc"));
						post = "('" + id + "', '" + parent_id + "', '" + link_id + "', '" + name + "', '" + author
								+ "', '" + body + "', '" + subreddit_id + "', '" + Integer.toString(score) + "', '"
								+ Integer.toString(created_utc) + "')";
						reddit = "('" + subreddit_id + "', '" + subreddit + "')";
						if (i != count - 1) {
							post += ",\n";
							reddit += ",\n";
						}
						longPost += post;
						longReddit += reddit;
					} catch (ScriptException e) {
						continue;
					}
				} else {
					if(FileReader.empty){
						go =false;
						break;
					}
					i--;
				}
				if (postDestination.size() > 150 || destinationList.size() > 150) {
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						continue;
					}
				}
			}
			destinationList.add(longReddit);
			postDestination.add(longPost);

		}
	}

}

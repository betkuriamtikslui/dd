package read;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	public static void main(String[] args) throws SQLException{
		ConcurrentLinkedQueue<String> fetchFrom = new ConcurrentLinkedQueue<String> ();
		ConcurrentLinkedQueue<String> destination1 = new ConcurrentLinkedQueue<String> ();
		ConcurrentLinkedQueue<String> destination2 = new ConcurrentLinkedQueue<String> ();
		String subredditBase= ("INSERT IGNORE INTO subreddit ( subreddit_id,subreddit)VALUES");
		String postBase = ("INSERT  INTO posts (id, parent_id, link_id, name, author, body, subreddit_id, score, created_utc )VALUES");

		
		DB.url = args[1];
		DB.user = args[2];
		DB.password = args[3];
		System.out.println("sh");
		Checker ch = new Checker(fetchFrom, destination1,destination2);
		FileReader fr = new FileReader(fetchFrom, args[0]);
		DB db = new DB(subredditBase, destination1);
		DB postDB = new DB(postBase, destination2);

		DB.initialize();
		System.out.println("sh");

		ExecutorService pool = Executors.newFixedThreadPool(500);

		pool.execute(fr);
		pool.execute(ch);
		pool.execute(db);
		pool.execute(postDB);

		while(!fr.empty){
			try {
				System.out.println(System.currentTimeMillis());
				System.out.println(destination1.size() +" "+destination2.size() + " " + fetchFrom.size());
				//System.out.println(!FileReader.empty + " " + Checker.go  + " " + db.go);

				Thread.sleep(60000);
			} catch (InterruptedException e) {
				continue;			}
		}
		System.out.println("finished");
	}
}

package database;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main2 {

	static ExecutorService pool;

	public static void main(String[] args) throws SQLException {
		String subredditBase= ("INSERT IGNORE INTO subreddits ( subreddit_id,subreddit)VALUES");
		String postBase = ("INSERT  INTO posts (id, parent_id, link_id, name, author, body, subreddit_id, score, created_utc )VALUES");
		Database.url = "jdbc:mysql://localhost:3306/my_db?autoReconnect=true&useSSL=false&?useUnicode=true&amp;characterEncoding=UTF-8";
		Database.user = "root";
		Database.password = "123456789";
		Database.initialize();
		pool = Executors.newFixedThreadPool(500);
		CircularList<String> list = new CircularList<String>();
		FileReader fr = new FileReader(list,args[0]);
		ConcurrentLinkedQueue<String> postQueue = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> subredditQueue = new ConcurrentLinkedQueue<String>();
		pool.execute(fr);
		pool.execute(new DoubleChecker(list,postQueue, subredditQueue));			
		pool.execute(new Database(postBase, postQueue));
		pool.execute(new Database(subredditBase, subredditQueue));
		while(!fr.empty){
			try {
				System.out.println(System.currentTimeMillis());
				System.out.println(postQueue.size() + " " + subredditQueue.size()+ " " + list.size());
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				continue;			}
		}
		System.out.println("finished");
	}
}

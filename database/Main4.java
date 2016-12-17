package database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main4 {

	static ExecutorService pool;

	public static void main(String[] args) throws SQLException, IOException {
		String fullHeader="id,parent_id,link_id,name,author,body,subreddit_id,score,created_utc,subreddit\n";
		String postHeader = "id,parent_id,link_id,name,author,body,subreddit_id,score,created_utc,subreddit\n";
		
		pool = Executors.newFixedThreadPool(500);
		CircularList<String> list = new CircularList<String>();
		FileReader fr = new FileReader(list,args[0]);
		ConcurrentLinkedQueue<String> postQueue = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> fullQueue = new ConcurrentLinkedQueue<String>();

		ConcurrentLinkedQueue<String> subredditQueue = new ConcurrentLinkedQueue<String>();
		pool.execute(fr);
		pool.execute(new DoubleChecker(list,postQueue, subredditQueue, fullQueue));	
		File full = new File("E:\\full.csv");
		File reddit = new File("E:\\reddit.csv");
		File post = new File("E:\\post.csv");
		
		Files.deleteIfExists(full.toPath());
		Files.deleteIfExists(reddit.toPath());
		Files.deleteIfExists(post.toPath());
		
		Writer fullWriter = new Writer(full, fullQueue,fullHeader);
		Writer redditWriter = new Writer(reddit, subredditQueue,"subreddit_id,subreddit"+"\n");
		Writer postWriter = new Writer(post, postQueue, postHeader);

		pool.execute(fullWriter);
		pool.execute(redditWriter);
		pool.execute(postWriter);


		
		while(!FileReader.empty){
			try {
				System.out.println(System.currentTimeMillis());
				System.out.println(redditWriter.count+" " + subredditQueue.size()+ " " + list.size());
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				continue;			}
		}
		System.out.println("finished");
	}
}

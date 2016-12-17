package database;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	static ExecutorService pool;

	public static void main(String[] args) throws SQLException {
//		Database.url = args[1];
//		Database.user = args[2];
//		Database.password = args[3];
//
//		ConcurrentLinkedQueue<String> second1 = new ConcurrentLinkedQueue<String>();
//
//		pool = Executors.newFixedThreadPool(500);
//
//		CircularList<String> list = new CircularList<String>();
//
//		FileReader reader = new FileReader(list, args[0]);
//		new Thread(reader).start();
//		int smth = 1;
//		for (int i = 0; i < smth; i++) {
//			Checker c = new Checker(list);
//			if (i % 4 == 0) {
//				c.list = list;
//
//				int re = i % smth;
//				switch (re) {
//				case 0:
//					c.destination = second1;
//					break;
//
//			//		pool.execute(c);
//					Database db = new Database(
//							"INSERT INTO stuff (id, parent_id, link_id, name, author, body, subreddit_id,subreddit,score, created_utc )VALUES");
//
//					pool.execute(db);
//				}
//
//				// System.out.println("shit");
//				// while(Checker.go){
//				// try {
//				// Thread.sleep(60000);
//				// System.out.println(System.currentTimeMillis() -
//				// Checker.start);
//				// System.out.println(list.size() + " " + list2.size()+ "
//				// "+list3.size()+" "+ list4.size());
//				//
//				// System.out.println(second1.size());
//				// } catch (InterruptedException e) {
//				// // TODO Auto-generated catch block
//				// e.printStackTrace();
//				// }
//				// }
//			}
//		}
	}
}

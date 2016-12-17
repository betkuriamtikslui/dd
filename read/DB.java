package read;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DB implements Runnable {
	private ConcurrentLinkedQueue<String> fetchFrom;
	private String base;

	static String url = "";
	static String user = "";
	static String password = "";
	java.sql.Connection con = null;
	java.sql.Statement st = null;
	boolean go;

	public DB(String base, ConcurrentLinkedQueue<String> fetchFrom) throws SQLException {
		this.base = base;
		this.fetchFrom = fetchFrom;
		con = DriverManager.getConnection(url, user, password);
		st = con.createStatement();
	}

	public static void initialize() throws SQLException {

		java.sql.Connection con1 = null;
		java.sql.Statement st1 = null;
		con1 = DriverManager.getConnection(url, user, password);
		st1 = con1.createStatement();
		String dropSubreddits = "DELETE from subreddit;";
		String dropPosts = "DELETE from posts;";
		String my_db = "USE my_db;";
		st1.execute(my_db);
		// String createSubreddits = "CREATE TABLE subreddits (subreddit_id
		// varchar(255),subreddit varchar(255));";
		// String createPosts = "CREATE TABLE posts (id varchar(20),parent_id
		// varchar(15),link_id varchar(15),name varchar(255),author
		// varchar(255),body text(65535 )CHARACTER SET utf8mb4 COLLATE
		// utf8mb4_general_ci NOT NULL,subreddit_id varchar(255),score
		// int,created_utc int);";
		System.out.println(st1.executeUpdate(dropSubreddits));
		st1.executeUpdate(dropPosts);
		// st1.execute(createPosts);
		// st1.execute(createSubreddits);
		con1.close();
		System.out.println("finished initializing database");

	};

	public void run() {
		go = true;
		int end = 10;
		String query = "";
		String tmp;
		while (go) {
			query = base;

			for (int i = 0; i < end; i++) {
				tmp = fetchFrom.poll();

				if (tmp == null) {
					if (!Checker.go) {
						try {
							st.executeUpdate(query+","+tmp+";");
							go = false;
							break;
						} catch (SQLException e) {

						}
					}
					i--;
				} else {
					if (i != end - 1) {
						query += tmp + ",";
					} else {
						query += tmp + ";";
					}
				}
			}
			try {
				st.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(query);
				break;

			}
		}
	}

}

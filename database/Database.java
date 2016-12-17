package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Database implements Runnable {

	java.sql.Connection con = null;
	java.sql.Statement st = null;
	ResultSet rs = null;
	private String basic = "";

	static String url = "";
	static String user = "";
	static String password = "";
	public ConcurrentLinkedQueue<String> fetchFrom;

	public Database(String base, ConcurrentLinkedQueue<String> fetchFrom) throws SQLException {
		con = DriverManager.getConnection(url, user, password);
		st = con.createStatement();
		basic = base;
		rs = st.executeQuery("SELECT VERSION()");
		this.fetchFrom = fetchFrom;
	}

	public Database(String base) throws SQLException {
		con = DriverManager.getConnection(url, user, password);
		st = con.createStatement();
		basic = base;

	}

	public static void initialize() throws SQLException {

		java.sql.Connection con1 = null;
		java.sql.Statement st1 = null;
		con1 = DriverManager.getConnection(url, user, password);
		st1 = con1.createStatement();
		String dropSubreddits = "DROP TABLE subreddits;";
		String dropPosts = "DROP TABLE posts;";
		String my_db = "USE my_db;";
		st1.execute(my_db);
		String createSubreddits = "CREATE TABLE subreddits (subreddit_id varchar(255),subreddit varchar(255));";
		String createPosts = "CREATE TABLE posts (id varchar(20),parent_id varchar(15),link_id varchar(15),name varchar(255),author varchar(255),body text(65535 )CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,subreddit_id varchar(255),score int,created_utc int);";
		st1.executeUpdate(dropSubreddits);
		st1.executeUpdate(dropPosts);
		st1.execute(createPosts);
		System.out.println("asdasdas");
		st1.execute(createSubreddits);
		con1.close();
		System.out.println("finished initializing database");

	};

	public void run() {
		boolean go = true;
		int end = 5;
		String query = "";
		String tmp;
		while (go) {
			query = basic;

			for (int i = 0; i < end; i++) {
				tmp = fetchFrom.poll();

				if (tmp == null) {
					i--;
				} else {
					query += tmp;
					if (i != end - 1) {
						query += ",";
					} else {
						query += ";";
					}
				}
			}
			try {
				st.executeUpdate(query);
			} catch (SQLException e) {
				continue;
			}
		}
	}

}
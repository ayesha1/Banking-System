import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


/**
 * Manage connection to database and perform SQL statements.
 */
public class BankingSystem {
	// Connection properties
	private static String driver;
	private static String url;
	private static String username;
	private static String password;

	// JDBC Objects
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;

	// Current Customer
	private static int currrentCustomerId;

	/**
	 * Initialize database connection given properties file.
	 * 
	 * @param filename name of properties file
	 */
	public static void init(String filename) {
		try {
			Properties props = new Properties(); // Create a new Properties object
			FileInputStream input = new FileInputStream(filename); // Create a new FileInputStream object using our
																	// filename parameter
			props.load(input); // Load the file contents into the Properties object
			driver = props.getProperty("jdbc.driver"); // Load the driver
			url = props.getProperty("jdbc.url"); // Load the url
			username = props.getProperty("jdbc.username"); // Load the username
			password = props.getProperty("jdbc.password"); // Load the password
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test database connection.
	 */
	public static void testConnection() {
		System.out.println(":: TEST - CONNECTING TO DATABASE");
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			Statement stmt = con.createStatement();
			stmt.close();
			System.out.println(":: TEST - SUCCESSFULLY CONNECTED TO DATABASE");
		} catch (Exception e) {
			System.out.println(":: TEST - FAILED CONNECTED TO DATABASE");
			e.printStackTrace();
		}
	}

	/**
	 * Create a new customer.
	 * 
	 * @param name   customer name
	 * @param gender customer gender
	 * @param age    customer age
	 * @param pin    customer pin
	 */
	public static void newCustomer(String name, String gender, String age, String pin) {
		System.out.println(":: CREATE NEW CUSTOMER - RUNNING");
		String query1 = "INSERT INTO P1.CUSTOMER (name, gender, age, pin) " + "VALUES ('" + name + "', '" + gender
				+ "', '" + age + "', '" + pin + "')"; // The query to run

		try {
			Statement stmt = con.createStatement();
			stmt.execute(query1);
			System.out.println(":: CREATE NEW CUSTOMER - SUCCESS");
			stmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Open a new account.
	 * 
	 * @param id     customer id
	 * @param type   type of account
	 * @param amount initial deposit amount
	 */
	public static void openAccount(String id, String type, String amount) {
		System.out.println(":: OPEN ACCOUNT - RUNNING");
		try { //TODO: id needs to be inside the P1.customer
			Statement stmt = con.createStatement();
			String query1 = "INSERT INTO P1.Account (id, balance, type, status) " + "VALUES ('" + id + "', '0', " + "'"
					+ type + "'," + "'A')";
			stmt.execute(query1);
			stmt.close();
			System.out.println(":: OPEN ACCOUNT - SUCCESS");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Close an account.
	 * 
	 * @param accNum account number
	 */
	public static void closeAccount(String accNum) {
		System.out.println(":: CLOSE ACCOUNT - RUNNING");
		try { //TODO: id needs to be inside the P1.customer
			Statement stmt = con.createStatement();
			String query1 = "UPDATE P1.ACCOUNT SET status = 'I', balance = '0' " + "where number = '" + accNum + "'"; // Updated
			stmt.execute(query1);
			stmt.close();
			System.out.println(":: CLOSE ACCOUNT - SUCCESS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deposit into an account.
	 * 
	 * @param accNum account number
	 * @param amount deposit amount
	 */
	public static void deposit(String accNum, String amount) {
		System.out.println(":: DEPOSIT - RUNNING");

		try { //TODO: id needs to be inside the P1.customer
			int originalAmount = 0;
			Statement stmt = con.createStatement();
			String query1 = "SELECT balance from P1.ACCOUNT where number = '" + accNum + "'"; // Updated
			ResultSet rs = stmt.executeQuery(query1);

			while (rs.next()) {
				originalAmount = rs.getInt(1);
				break;
			}

			int sum = Integer.parseInt(amount) + originalAmount;
			String query2 = "UPDATE P1.ACCOUNT SET balance = '" + sum + "' " + "where number = '" + accNum
					+ "' AND STATUS = 'A'"; // Updated
			stmt.execute(query2);
			stmt.close();
			System.out.println(":: DEPOSIT - SUCCESS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Withdraw from an account.
	 * 
	 * @param accNum account number
	 * @param amount withdraw amount
	 */
	public static void withdraw(String accNum, String amount) {
		System.out.println(":: WITHDRAW - RUNNING");

		try { //TODO: id needs to be inside the P1.customer
			Statement stmt = con.createStatement();
			//---
			// 1. Get a connection to the Database
			int originalAmount = 0;
			String query1 = "SELECT balance from P1.ACCOUNT where number = '" + accNum + "'"; // Updated
			ResultSet rs = stmt.executeQuery(query1);

			while (rs.next()) {
				originalAmount = rs.getInt(1);
				break;
			}

			int sum = originalAmount - Integer.parseInt(amount);

			if (sum < 0) {
				String s = "CANNOT WITHDRAW. BALANCE IS ONLY " + originalAmount;
				System.out.println(s);

			} else {
				String query2 = "UPDATE P1.ACCOUNT SET balance = '" + sum + "' " + "where number = '" + accNum + "'"; // Updated
				stmt.execute(query2);
			}
			stmt.close();
			System.out.println(":: WITHDRAW - SUCCESS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Transfer amount from source account to destination account.
	 * 
	 * @param srcAccNum  source account number
	 * @param destAccNum destination account number
	 * @param amount     transfer amount
	 */
	public static void transfer(String srcAccNum, String destAccNum, String amount) {
		System.out.println(":: TRANSFER - RUNNING");

		try {
			int balanceOfsource = 0;
			Statement stmt = con.createStatement();
			String query1 = "SELECT balance from P1.ACCOUNT where number = '" + srcAccNum + "'";
	
			ResultSet rs = stmt.executeQuery(query1);
			while (rs.next()) {
				balanceOfsource = rs.getInt(1);
				break;
			}
	
			if (balanceOfsource < Integer.parseInt(amount)) {
				System.out.println("Balance of srcAcc is less than Amount.");
				return;
			}
	
			// 2. Subtract the amount from sourceAccNum
			int newBalance = balanceOfsource - Integer.parseInt(amount);
			String query2 = "UPDATE P1.ACCOUNT set balance = '" + newBalance + "' where number = '" + srcAccNum
					+ "'";
			stmt.execute(query2);
	
			// 3. Add the amount to destination AccNum and return true
			int originalBalanceOfDestinationAccount = 0;
			String query3 = "SELECT balance from P1.ACCOUNT where number = '" + destAccNum + "'"; // Updated
			ResultSet rs2 = stmt.executeQuery(query3);
	
			while (rs2.next()) {
				originalBalanceOfDestinationAccount = rs2.getInt(1);
				break;
			}
			int newSum = originalBalanceOfDestinationAccount + Integer.parseInt(amount);
			String query4 = "UPDATE P1.ACCOUNT set balance = '" + newSum + "' where number = '" + destAccNum
					+ "' AND status = 'A'";
			stmt.execute(query4);
			stmt.close();
			System.out.println(":: TRANSFER - SUCCESS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display account summary.
	 * 
	 * @param cusID customer ID
	 */
	public static void accountSummary(String cusID) {
		System.out.println(":: ACCOUNT SUMMARY - RUNNING");
		try { //TODO: id needs to be inside the P1.customer
			Statement stmt = con.createStatement();
			int totalAmount = 0;
			String query1 = "SELECT number, balance from P1.ACCOUNT where id = '" + cusID + "'"
					+ "AND status = 'A'"; // Updated
			ResultSet rs = stmt.executeQuery(query1);
			System.out.println("NUMBER      BALANCE     ");
			System.out.println("----------- -----------");
			while (rs.next()) {
				int number = rs.getInt(1);
				int balance = rs.getInt(2);
				System.out.println(" " + number + " |  " + balance);
				totalAmount += balance;
			}
			System.out.println("----------------------");
			System.out.println("TOTAL    " + totalAmount);

			
			stmt.close();
			System.out.println(":: ACCOUNT SUMMARY - SUCCESS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display Report A - Customer Information with Total Balance in Decreasing
	 * Order.
	 */
	public static void reportA() {
		try { //TODO: id needs to be inside the P1.customer
			System.out.println(":: REPORT A - RUNNING");
			Statement stmt = con.createStatement();
			String query1 = "select c.id, name, age, gender, totalOfBalances from p1.customer as c "
					+ "join (select id, sum(balance) as totalOfBalances from p1.account group by id) "
					+ "as a on a.id = c.id"; // Updated
			ResultSet rs = stmt.executeQuery(query1);
			System.out.println("ID          NAME            GENDER AGE         TOTAL ");
			System.out.println("----------- --------------- ------ ----------- ----------- ");

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				int age = rs.getInt(3);
				String gender = rs.getString(4);
				int balance = rs.getInt(5);
				System.out.println(id + "        " + name + "    " + gender+ "     " + age + "  " + balance);
			}
			stmt.close();
			System.out.println(":: REPORT A - SUCCESS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display Report B - Customer Information with Total Balance in Decreasing
	 * Order.
	 * 
	 * @param min minimum age
	 * @param max maximum age
	 */
	public static void reportB(String min, String max) {
		System.out.println(":: REPORT B - RUNNING");
		try { //TODO: id needs to be inside the P1.customer
			System.out.println(":: REPORT B - RUNNING");
			Statement stmt = con.createStatement();
			String query1 = "select avg(balance) from p1.account as a join (select id, age from p1.customer where age <= '"
					+ max + "' and age >= '"
					+ min + "') as c on a.id = c.id"; // Updated
			ResultSet rs = stmt.executeQuery(query1);
			System.out.println("AVERAGE");
			System.out.println("----------- ");

			while (rs.next()) {

				int id = rs.getInt(1);
				System.out.println(" " + id);

			}

			stmt.close();
			System.out.println(":: REPORT B - SUCCESS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

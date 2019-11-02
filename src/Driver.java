import java.util.*;
import java.io.*;
import java.sql.*;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {
//			// 1. Get a connection to the Database
//			Connection con = DriverManager.getConnection("jdbc:db2://127.0.0.1:50000/SAMPLE", "db2inst1", "kenward");
//
//			// 2. Create a statement
//			Statement stmt = con.createStatement();
//
//			// 3. Execute SQL query
//			String query = "SELECT FIRSTNME, LASTNAME, EDLEVEL, SALARY FROM EMPLOYEE";
//			ResultSet rs = stmt.executeQuery(query);
//
//			// 4. Process the result set
//			while (rs.next()) { // Loop through result set and retrieve contents of each row
//				String firstname = rs.getString(1);
//				String lastname = rs.getString(2);
//				int edlevel = rs.getInt(3);
//				double salary = rs.getDouble(4);
//				System.out.println(firstname + ",\t" + lastname + "," + edlevel + ",\t\t" + salary); // Print out each
//																										// row's values
//																										// to the screen
//			}
//
//			String sqlCreate = "CREATE TABLE IF NOT EXISTS P1.CUSTOMER"
//					+ "  (ID           integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),"
//					+ "   name            VARCHAR(15)," 
//					+ "   gender          CHAR," 
//					+ "   age           INTEGER,"
//					+ "   pin           INTEGER," 
//					+ "   type     CHAR," 
//					+ "   status char," + "PRIMARY KEY (ID))";
//
//			stmt.execute(sqlCreate);
//
//			String query1 = "INSERT INTO P1.CUSTOMER (name, gender, age, pin, type, status) "
//					+ "VALUES ('Sammy', 'F', '20', '1990', 'C', 'A')"; // The query to run
//			stmt.execute(query1);
//
//			con.close();
//			rs.close();// Close the result set after we are done with the result set
//			stmt.close(); // Close the statement after we are done with the statement
//			con.close();
//
//		} catch (Exception exc) {
//			exc.printStackTrace();
//		}
	}

}

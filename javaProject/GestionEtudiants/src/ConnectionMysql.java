import java.sql.*;

import javax.swing.*;

public class ConnectionMysql {
	
 Connection con=null;
public static Connection connexiondb(){
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gestionetude","root","");
		return con;
	}
	catch(Exception e) {
		JOptionPane.showMessageDialog(null, e);
	return null;}
}
}

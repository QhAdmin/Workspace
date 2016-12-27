import java.sql.*;
import javax.swing.JOptionPane;

public class javaconnect
{
    
    Connection conn = null;
  public static Connection connerDb(  ){
  
    
    try {
      Class.forName("org.sqlite.JDBC");
     Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\User\\Desktop\\Friend.sqlite");
     //JOptionPane.showMessageDialog(null, "Connect database");
     return conn;
    } catch ( Exception e ) {
       // JOptionPane.showMessageDialog(null, e);
      return null;
    }
    
  }
}

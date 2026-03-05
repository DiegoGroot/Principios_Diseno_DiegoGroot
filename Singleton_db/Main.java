
import java.sql.Connection;

/**
 * Class Main
 */
public class Main {

  //
  // Fields
  //

  
  //
  // Constructors
  //
  public Main () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  //
  // Other methods
  //

  /**
   * @param        args
   */
  public static void main(String[] args)
  {
Connection conn1 = DatabaseConection.getInstance().getConnection();
Connection conn2 = DatabaseConection.getInstance().getConnection();

System.out.println(conn1 == conn2);
  }


}

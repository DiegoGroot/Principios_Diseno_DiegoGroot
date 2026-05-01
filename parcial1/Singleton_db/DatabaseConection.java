import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class DatabaseConection
 */
public class DatabaseConection {

  //
  // Fields
  //
  private static DatabaseConection instance;
  private Connection connection;
  private static String URL = "jdbc:mysql://localhost:3306/test";
  private static  String USER = "DiegoGroot";
  private static  String PASSWORD = "DiegoG";
  
  //
  // Constructors
  //

  private DatabaseConection() {
try{
connection = DriverManager.getConnection(URL,USER,PASSWORD);
System.out.println("Conexion establecida");
} catch(SQLException e){
throw new RuntimeException("Error al conectar la base de datos",e);
}
}

    //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Get the value of instance
   * @return the value of instance
   */
  public static DatabaseConection getInstance () {
   if(instance == null){
instance = new DatabaseConection();
}   
return instance;
  }

  /**
   * Set the value of connection
   * @param newVar the new value of connection
   */
  public void setConnection (Connection newVar) {
    connection = newVar;
  }

  /**
   * Get the value of connection
   * @return the value of connection
   */
  public Connection getConnection () {
    return connection;
  }

  /**
   * Set the value of URL
   * @param newVar the new value of URL
   */
  public void setURL (String newVar) {
    URL = newVar;
  }

  /**
   * Get the value of URL
   * @return the value of URL
   */
  public String getURL () {
    return URL;
  }

  /**
   * Set the value of USER
   * @param newVar the new value of USER
   */
  public void setUSER (String newVar) {
    USER = newVar;
  }

  /**
   * Get the value of USER
   * @return the value of USER
   */
  public String getUSER () {
    return USER;
  }

  /**
   * Set the value of PASSWORD
   * @param newVar the new value of PASSWORD
   */
  public void setPASSWORD (String newVar) {
    PASSWORD = newVar;
  }

  /**
   * Get the value of PASSWORD
   * @return the value of PASSWORD
   */
  public String getPASSWORD () {
    return PASSWORD;
  }


}

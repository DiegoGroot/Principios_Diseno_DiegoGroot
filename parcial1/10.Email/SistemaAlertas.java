

/**
 * Class SistemaAlertas
 */
public class SistemaAlertas {

  //
  // Fields
  //

  
  //
  // Constructors
  //
  public SistemaAlertas () { };
  
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
	  EmailNotificacion email = new EmailNotificacion("Bienvenido", "user@email.com");
	  email.enviar();
	  email.registrarLog();
	  
	  SMSNotificacion sms = new SMSNotificacion("Tu codigo es 1234"," +52202020011");
	  sms.enviar();
	  sms.registrarLog();
  }


}

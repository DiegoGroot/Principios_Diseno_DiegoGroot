/**
 * Class SMSNotificacion
 */
public class SMSNotificacion extends Notificacion implements Envia {

  //
  // Fields
  //

  
  //
  // Constructors
  //
  public SMSNotificacion () { };
  
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
   * @return       String
   */
  public SMSNotificacion(String mensaje, String destinatario)
  {
	  super (mensaje,destinatario);
  }


  /**
   */
   @Override
  public void enviar()
  {
	    System.out.println("Enviando Sms al " + destinatario + "[costo: 0.10]" );
  }


}

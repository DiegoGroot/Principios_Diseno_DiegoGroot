
/**
 * Class EmailNotificacion
 */
public class EmailNotificacion extends Notificacion implements Envia {

  //
  // Fields
  //

  
  //
  // Constructors
  //
  public EmailNotificacion () { };
  
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
   * @param        String
   */
  public EmailNotificacion(String mensaje, String destinatario)
  {
	  super (mensaje,destinatario);
  }


  /**
   */
   @Override
  public void enviar()
  {
	  System.out.println("Enviando Email a " + destinatario + ": " + mensaje);
  }


}

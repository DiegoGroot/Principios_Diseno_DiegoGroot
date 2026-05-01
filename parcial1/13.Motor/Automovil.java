

/**
 * Class Automovil
 */
public class Automovil {

  //
  // Fields
  //
  private final Motor motor;
  private String modelo;
  
  //
  // Constructors
  //
  
  public Automovil(String modelo, String tipoMotor){
     this.modelo = modelo;
	 this.motor = new Motor(tipoMotor);
 }
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of motor
   * @param newVar the new value of motor
   */

  /**
   * Get the value of motor
   * @return the value of motor
   */
  public Motor getMotor () {
    return motor;
  }


  /**
   * Set the value of modelo
   * @param newVar the new value of modelo
   */
  public void setModelo (String newVar) {
    modelo = newVar;
  }

  /**
   * Get the value of modelo
   * @return the value of modelo
   */
  public String getModelo () {
    return modelo;
  }


  //
  // Other methods
  //

  /**
   */
  public void arrancar()
  {
	  System.out.println("Arrancando el modelo: " + modelo);
	  motor.encender();
  }


}

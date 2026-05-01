

/**
 * Class Avion
 */
public class Avion {

  //
  // Fields
  //

  public String aerolinea;
  protected String velocidad;
  private String Cantidad_de_motores;
  private String capacidad;
  
  //
  // Constructors
  //
  public Avion () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of aerolinea
   * @param newVar the new value of aerolinea
   */
  public void setAerolinea (String newVar) {
    aerolinea = newVar;
  }

  /**
   * Get the value of aerolinea
   * @return the value of aerolinea
   */
  public String getAerolinea () {
    return aerolinea;
  }

  /**
   * Set the value of velocidad
   * @param newVar the new value of velocidad
   */
  public void setVelocidad (String newVar) {
    velocidad = newVar;
  }

  /**
   * Get the value of velocidad
   * @return the value of velocidad
   */
  public String getVelocidad () {
    return velocidad;
  }

  /**
   * Set the value of Cantidad_de_motores
   * @param newVar the new value of Cantidad_de_motores
   */
  public void setCantidad_de_motores (String newVar) {
    Cantidad_de_motores = newVar;
  }

  /**
   * Get the value of Cantidad_de_motores
   * @return the value of Cantidad_de_motores
   */
  public String getCantidad_de_motores () {
    return Cantidad_de_motores;
  }

  /**
   * Set the value of capacidad
   * @param newVar the new value of capacidad
   */
  public void setCapacidad (String newVar) {
    capacidad = newVar;
  }

  /**
   * Get the value of capacidad
   * @return the value of capacidad
   */
  public String getCapacidad () {
    return capacidad;
  }

  //
  // Other methods
  //

  /**
   * @param        aerolinea1
   * @param        cantidaddemotores1
   * @param        velocidad1
   * @param        capacidad1
   */
  public Avion(String aerolinea1, String cantidaddemotores1, String velocidad1, String capacidad1)
  {
 this.aerolinea = aerolinea1;
 this.Cantidad_de_motores = cantidaddemotores1;
 this.velocidad = velocidad1;
 this.capacidad = capacidad1;
 System.out.println("Construyo un avion");
  }


  /*
   */
  public void acelerar()
  {
  System.out.println("El avion acelero");
  }


  /**
   */
  public void elevarse()
  {
System.out.println("El avion se elevo");
  }


  /**
   * @param        modelo1
   */
  public void setModelo(String modelo1)
  {
  }


  /**
   * @return       String
   */


}

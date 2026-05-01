

/**
 * Class Autobus
 */
public class Autobus extends Vehiculo {

  //
  // Fields
  //

  private int numAsientos;
  private int numPuertas;
  private int numVentilas;
  
  //
  // Constructors
  //
  public Autobus () { };
  
  public Autobus(String marcaModelo, int precioDia, int numllantas, int numAsientos, int numPuertas, int numVentilas){
  this.marcaModelo = marcaModelo;
  this.marcaModelo  = marcaModelo;
  this.precioDia = precioDia;
  this.numllantas = numllantas;
  this.numAsientos= numAsientos;
  this.numPuertas= numPuertas;
  this.numVentilas = numVentilas;
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of numAsientos
   * @param newVar the new value of numAsientos
   */
  public void setNumAsientos (int newVar) {
    numAsientos = newVar;
  }

  /**
   * Get the value of numAsientos
   * @return the value of numAsientos
   */
  public int getNumAsientos () {
    return numAsientos;
  }

  /**
   * Set the value of numPuertas
   * @param newVar the new value of numPuertas
   */
  public void setNumPuertas (int newVar) {
    numPuertas = newVar;
  }

  /**
   * Get the value of numPuertas
   * @return the value of numPuertas
   */
  public int getNumPuertas () {
    return numPuertas;
  }

  /**
   * Set the value of numVentilas
   * @param newVar the new value of numVentilas
   */
  public void setNumVentilas (int newVar) {
    numVentilas = newVar;
  }

  /**
   * Get the value of numVentilas
   * @return the value of numVentilas
   */
  public int getNumVentilas () {
    return numVentilas;
  }

  //
  // Other methods
  //

  /**
   */
  public void mostrarDatos()
  {
	   System.out.println("marca:" + this.marcaModelo);
	  System.out.println("precio: " + this.precioDia);
	  System.out.println("llantas:" + this.numllantas);
  }


  /**
   */
  public void pruebaDelMotor()
  {
	System.out.println("motor funcionando");  
  }


}

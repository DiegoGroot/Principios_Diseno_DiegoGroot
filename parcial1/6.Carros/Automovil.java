

/**
 * Class Automovil
 */
public class Automovil extends Vehiculo {

  //
  // Fields
  //

  private int numAsientos;
  private int numPuertas_;
  
  //
  // Constructors
  //
  public Automovil (String marcaModelo, int precioDia , int numllantas, int numAsientos, int numPuertas_) { 
  this.marcaModelo  = marcaModelo;
  this.precioDia = precioDia;
  this.numllantas = numllantas;
  this.numAsientos= numAsientos;
  this.numPuertas_ = numPuertas_;
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
   * Set the value of numPuertas_
   * @param newVar the new value of numPuertas_
   */
  public void setNumPuertas_ (int newVar) {
    numPuertas_ = newVar;
  }

  /**
   * Get the value of numPuertas_
   * @return the value of numPuertas_
   */
  public int getNumPuertas_ () {
    return numPuertas_;
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



/**
 * Class Motocicleta
 */
public class Motocicleta  extends Vehiculo{

  //
  // Fields
  //

  private int numAsientos;
  
  //
  // Constructors
  //
  public Motocicleta () { };
  
  public Motocicleta(String marcaModelo, int precioDia , int numllantas, int numAsientos){
    this.marcaModelo  = marcaModelo;
  this.precioDia = precioDia;
  this.numllantas = numllantas;
  this.numAsientos= numAsientos;
  }
  
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

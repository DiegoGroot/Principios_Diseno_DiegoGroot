

/**
 * Class Gato
 */
public class Gato extends Animal implements Sonido {

  //
  // Fields
  //

  
  //
  // Constructors
  //
  public Gato () { };
  
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
   * @param        nombre
   */

  public Gato(String nombre)
  {
	  super(nombre);
  }


  /**
   */
@Override
   public void mover() 
  {
	  System.out.println("El gato salta");
  }

  /**
   */
   @Override
  public void hacerSonido()
  {
	  System.out.println(" miau");
  }


}

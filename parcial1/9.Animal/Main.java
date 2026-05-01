

/**
 * Class Main
 */
public class Main {

  //
  // Fields
  //

  
  //
  // Constructors
  //
  public Main () { };
  
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
	  Perro dog = new Perro("Firulais");
	  Gato cat = new Gato("Michi");
	  
	  dog.mover();
	  dog.hacerSonido();
	  
	  cat.mover();
	  cat.hacerSonido();
  }


}

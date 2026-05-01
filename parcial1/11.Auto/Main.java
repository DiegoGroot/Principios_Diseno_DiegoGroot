

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
	  Auto miauto = new Auto("Toyota");
	  Persona persona = new Persona("Adolfo", miauto);
	  
	  persona.mostrarAuto();
  }

}

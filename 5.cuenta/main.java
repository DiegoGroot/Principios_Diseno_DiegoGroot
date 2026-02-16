

/**
 * Class main
 */
public class main {

  //
  // Fields
  //

  
  //
  // Constructors
  //
  public main () { };
  
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
   * @param        String_
   */
 public static void main(String[] args){
  {
	  CuentaBancaria p1 = new CuentaBancaria(1000);
	  p1.depositar(500);
	  p1.retirar(1500);
	  
	  System.out.println("Saldo actual:" + p1.getSaldo());
	  p1.retirar(500);
  }


}
}



/**
 * Class CuentaBancaria
 */
public class CuentaBancaria {

  //
  // Fields
  //

  private double saldo;
  
  //
  // Constructors
  //
  public CuentaBancaria () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of saldo
   * @param newVar the new value of saldo
   */
  public void setSaldo (double newVar) {
    saldo = newVar;
  }

  /**
   * Get the value of saldo
   * @return the value of saldo
   */
  public double getSaldo () {
    return saldo;
  }

  //
  // Other methods
  //
  /**
   * @param        double
   */
  public CuentaBancaria(double saldoStart)
  {
	  if (saldoStart >=0){
	  this.saldo = saldoStart;
	  System.out.println("Saldo inicial = " +this.saldo);
	  }
  }


  /**
   * @param        double
   */
  public void depositar(double cantidad)
  {
	  if ( cantidad >= 0)
		  saldo += cantidad;
	  System.out.println("deposito =" + cantidad);
  }


  /**
   * @param        double
   */
  public void retirar(double cantidad)
  {
	  System.out.println("retirando: " + cantidad);
	  
	  if(cantidad >=0 &&  cantidad <= saldo){
		  saldo -=cantidad;
		  System.out.println("Retiro "+ cantidad);
	  } else{
		  System.out.println("fondo insufcieitne");
  }
  }


}

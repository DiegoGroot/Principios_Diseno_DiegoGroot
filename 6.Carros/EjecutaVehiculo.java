

/**
 * Class EjecutaVehiculo
 */
public class EjecutaVehiculo {

  //
  // Fields
  //

  
  //
  // Constructors
  //
  public EjecutaVehiculo () { };
  
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
	  System.out.println("Vehiculos disponibles");
	  Automovil v1 = new Automovil("Volvo 500", 120, 4, 5,4);
	  v1.mostrarDatos();
	  v1.pruebaDelMotor();
	  
	  Motocicleta m1 = new Motocicleta("Italika",120,2,2);
	  m1.mostrarDatos();
	  m1.pruebaDelMotor();
	 
	  Autobus b1 = new Autobus("Mercedes",300,8,42,2,2);
	  b1.mostrarDatos();
	  b1.pruebaDelMotor();
	  
	  Autobus b2 = new Autobus("Mercedes smart",250,6,25,1,2);
	  b2.mostrarDatos();
	  b2.pruebaDelMotor();
  }


}

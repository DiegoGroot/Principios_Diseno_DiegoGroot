

/**
 * Class Estudante
 */
public class Estudante extends Persona{

  //
  // Fields
  //

  public int Matricula;
  public double Promedio;
  public String Licenciatura;
  
  //
  // Constructors
  //
  public Estudante () { };
  
  public Estudante(int Maricula, double Promedio, String Licenciatura){
    this.Matricula = Matricula;  
	this.Promedio = Promedio;
	this.Licenciatura= Licenciatura;
  }
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of Matricula
   * @param newVar the new value of Matricula
   */
  public void setMatricula (int newVar) {
    Matricula = newVar;
  }

  /**
   * Get the value of Matricula
   * @return the value of Matricula
   */
  public int getMatricula () {
    return Matricula;
  }

  /**
   * Set the value of Promedio
   * @param newVar the new value of Promedio
   */
  public void setPromedio (double newVar) {
    Promedio = newVar;
  }

  /**
   * Get the value of Promedio
   * @return the value of Promedio
   */
  public double getPromedio () {
    return Promedio;
  }

  /**
   * Set the value of Licenciatura
   * @param newVar the new value of Licenciatura
   */
  public void setLicenciatura (String newVar) {
    Licenciatura = newVar;
  }

  /**
   * Get the value of Licenciatura
   * @return the value of Licenciatura
   */
  public String getLicenciatura () {
    return Licenciatura;
  }

  //
  // Other methods
  //

  /**
   * @param        Matricula
   * @param        Promedio
   * @param        Licenciatura
   */
  public void DatosDeAlumno(int Matricula, double Promedio, String Licenciatura)
  {
	    System.out.println("Datos del Alumno:  " + "Matricula: " + Matricula + "Promedio: " + Promedio + "Licenciatura: " + Licenciatura);
  }


}

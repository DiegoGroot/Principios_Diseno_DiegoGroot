

/**
 * Class Docente
 */
public class Docente extends Persona {

  //
  // Fields
  //

  public int IdTrabajador;
  public double Salario;
  public String Departamento;
  
  //
  // Constructors
  //
  public Docente () { };
  
    public Docente(int IdTrabajador, double Salario, String Departamento){
    this.IdTrabajador = IdTrabajador;  
	this.Salario = Salario;
	this.Departamento = Departamento;
  }
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of IdTrabajdor
   * @param newVar the new value of IdTrabajdor
   */
  public void setIdTrabajador (int newVar) {
    IdTrabajador = newVar;
  }

  /**
   * Get the value of IdTrabajdor
   * @return the value of IdTrabajdor
   */
  public int getIdTrabajador () {
    return IdTrabajador;
  }

  /**
   * Set the value of Salario
   * @param newVar the new value of Salario
   */
  public void setSalario (double newVar) {
    Salario = newVar;
  }

  /**
   * Get the value of Salario
   * @return the value of Salario
   */
  public double getSalario () {
    return Salario;
  }

  /**
   * Set the value of Departamento
   * @param newVar the new value of Departamento
   */
  public void setDepartamento (String newVar) {
    Departamento = newVar;
  }

  /**
   * Get the value of Departamento
   * @return the value of Departamento
   */
  public String getDepartamento () {
    return Departamento;
  }

  //
  // Other methods
  //

  /**
   * @param        Idtrabajador
   * @param        Salario
   * @param        Departamento
   */
  public void DatosDocente(int IdTrabajador, double Salario, String Departamento)
  {
	    System.out.println("Datos del Profesor: " + "Id Trabajador: " + IdTrabajador + "salario: " + Salario + "Departamento:  " + Departamento);
  }


}


import java.util.*;
import java.util.List;

/**
 * Class Universidad
 */
public class Universidad {

  //
  // Fields
  //

  private String nombre;
  private List<Profesor> profesores;
  
  //
  // Constructors
  //
  public Universidad () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of nombre_
   * @param newVar the new value of nombre_
   */
  public void setNombre(String newVar) {
    nombre = newVar;
  }

  /**
   * Get the value of nombre_
   * @return the value of nombre_
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Set the value of profesores
   * @param newVar the new value of profesores
   */
  public void setProfesores (List<Profesor> newVar) {
    profesores = newVar;
  }

  /**
   * Get the value of profesores
   * @return the value of profesores
   */
  public List<Profesor> getProfesores () {
    return profesores;
  }

  //
  // Other methods
  //

  /**
   * @param        nombre
   * @param        profesores
   */
  public Universidad(String nombre, List<Profesor> profesores)
  {
	  this.nombre = nombre;
	  this.profesores = profesores;
  }


  /**
   * @param        profesores
   */
  public void mostrarProfesores()
  {
	  System.out.println("Universidad: " + this.nombre);
        System.out.println("Lista de Profesores:");
        for (Profesor p : profesores) {
            System.out.println("- " + p.getNombre() + " | Especialidad: " + p.getEspecialidad());
        }
  }


}

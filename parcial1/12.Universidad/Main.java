
import java.util.ArrayList;
import java.util.List;
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
	  
        Profesor p1 = new Profesor("Adsofsito", "principios");
        Profesor p2 = new Profesor("gabirel", "IA");
        
        
        List<Profesor> listaProfesores = new ArrayList<>();
        listaProfesores.add(p1);
        listaProfesores.add(p2);
        
 
        Universidad universidad = new Universidad("USBI", listaProfesores);
        

        universidad.mostrarProfesores();
  }


}

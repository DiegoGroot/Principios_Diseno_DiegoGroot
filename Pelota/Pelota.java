

/**
 * Class Pelota
 */
public class Pelota {

  //
  // Fields
  //

  public String forma;
  public String tipo;
  protected String material;
  private String Color;
  
  //
  // Constructors
  //
  public Pelota () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of forma
   * @param newVar the new value of forma
   */
  public void setForma (String newVar) {
    forma = newVar;
  }

  /**
   * Get the value of forma
   * @return the value of forma
   */
  public String getForma () {
    return forma;
  }

  /**
   * Set the value of tipo
   * @param newVar the new value of tipo
   */
  public void setTipo (String newVar) {
    tipo = newVar;
  }

  /**
   * Get the value of tipo
   * @return the value of tipo
   */
  public String getTipo () {
    return tipo;
  }

  /**
   * Set the value of material
   * @param newVar the new value of material
   */
  public void setMaterial (String newVar) {
    material = newVar;
  }

  /**
   * Get the value of material
   * @return the value of material
   */
  public String getMaterial () {
    return material;
  }

  /**
   * Set the value of Color
   * @param newVar the new value of Color
   */
  public void setColor (String newVar) {
    Color = newVar;
  }

  /**
   * Get the value of Color
   * @return the value of Color
   */
  public String getColor () {
    return Color;
  }

  //
  // Other methods
  //

  /**
   * @param        forma1
   * @param        color1
   * @param        material1
   * @param        tipo1
   */
  public Pelota(String forma1, String color1, String material1, String tipo1)
  {
  this.forma = forma1;
  this.Color = color1;
  this.material = material1;
  this.tipo = tipo1;
  System.out.println("Construyo una pelota");
  }


  /**
   */
  public void botar()
  {
  System.out.println("La pelota esta botando");
  }


  /**
   */
  public void desinflar()
  {
  System.out.println("Pelota ya se desinflo");
  }


  /**
   * @param        forma1
   */



  /**
   * @return       String
   */



}

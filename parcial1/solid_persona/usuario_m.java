class Usuario{
private String nombre;
private String email;

public Usuario(String nombre, String email){
this.nombre = nombre;
this.email = email;
}

public void guardarEnBasesdeDatos(){
System.out.println("Guardando usuario en BD");
}

public void enviarEmailBienvenidad(){
System.out.prinln("Enviado email");
 }
}

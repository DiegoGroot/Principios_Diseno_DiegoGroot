class Cuadrado extends Rectangulo{

@Override
public void setAncho(int ancho){
this.ancho = ancho;
this.alto = ancho;

}

@Override
public void setAlto(int alto){
this.alto = alto;
this.ancho = alto;
}
}

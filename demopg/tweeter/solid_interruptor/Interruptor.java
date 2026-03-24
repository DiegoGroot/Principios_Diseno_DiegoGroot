class Interruptor{
private Bombilla bombilla;

public Interruptor(){
this.bombilla = new Bombilla();
}

public void presionar(){
bombilla.encender();
}
}

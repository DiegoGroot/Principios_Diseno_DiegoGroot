class ProcesadorPago{

public void procesar(String tipoPago){

if(tipoPago.equals("Tarjeta")){
System.out.println("Procesador pago con tarjeta");
}
else if(tipoPago.equals("Paypal")){
System.out.prinln("Procesando pago con Paypal");
}
else if(tipoPago.equals("Intraferencia")){
System.out.println("Procesando transferencia");
}
}
}

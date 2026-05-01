class AdaptadorAmericanoEuropeo implements EnchufeEuropeo{

private EnchufeAmericano enchufeAmericano;

public AdaptadorAmericanoEuropeo(EnchufeAmericano enchufeAmericano){
this.enchufeAmericano = enchufeAmericano;
}

@Override
public void conectar(){
enchufeAmericano.plugIn();
}
}

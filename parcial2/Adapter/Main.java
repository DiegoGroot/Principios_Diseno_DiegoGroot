public class Main{
public static void main(String[] args){
EnchufeAmericano enchufeUSA = new EnchufeAmericano();

EnchufeEuropeo enchufeEU = new AdaptadorAmericanoEuropeo(enchufeUSA);

enchufeEU.conectar();
}
}

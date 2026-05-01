public class Main{

public static void main(String[] args){

HouseBuilder builder = new ConcreteHouseBuilder();;
Director director = new Director();

House house = Director.construct(builder);
house.show();

}
}

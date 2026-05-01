public class Main{
public static void main(String[] args){

Graphic circle1 = new Circle();
Graphic circle2 = new Circle();

CompositeGraphic group = new CompositeGraphic();
group.add(Circle1);
group.add(Circle2);

group.draw();
}
}

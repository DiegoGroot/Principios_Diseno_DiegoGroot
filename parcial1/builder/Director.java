public class Director{

public House coonstruc(HouseBuilder builder){

builder.buildWalls();
builder.buildRoof();
builder.buildGarage();

return builder.getHouse();
}
}

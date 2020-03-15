import java.util.List;

public class AdminUnit {
    String name;
    int adminLevel;
    double population;
    double area;
    double density;
    AdminUnit parent;
    BoundingBox bbox = new BoundingBox();
    List<AdminUnit> children;

    @Override
    public String toString() {
        return "name: " + name + ", admin level: " + adminLevel + ", area: " + area +
                ", population: " + population + ", density: " + density + "\n" + bbox.toString();
    }
}
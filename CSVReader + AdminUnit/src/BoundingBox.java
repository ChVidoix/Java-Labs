import static java.lang.Math.min;
import static java.lang.Math.max;

public class BoundingBox {
    Double xmin = Double.NaN;
    Double ymin = Double.NaN;
    Double xmax = Double.NaN;
    Double ymax = Double.NaN;

    /**
     * Powiększa BB tak, aby zawierał punkt (x,y)
     * @param x - współrzędna x
     * @param y - współrzędna y
     */
    void addPoint(double x, double y){
        if(contains(x, y)) return;
        if(isEmpty()) {
            xmin = xmax = x;
            ymin = ymax = y;
        }
        xmin = min(xmin, x);
        ymin = min(ymin, y);
        xmax = max(xmax, x);
        ymax = max(ymax, y);
    }

    /**
     * Sprawdza, czy BB zawiera punkt (x,y)
     * @param x
     * @param y
     * @return
     */
    private boolean contains(double x, double y){
        if(isEmpty()) return false;
        return xmin <= x && x <= xmax && ymin <= y && y <= ymax;
    }

    /**
     * Sprawdza czy dany BB zawiera bb
     * @param bb
     * @return
     */
    boolean contains(BoundingBox bb){
        return contains(bb.xmin, bb.ymin) && contains(bb.xmax, bb.ymax);
    }

    /**
     * Sprawdza, czy dany BB przecina się z bb
     * @param bb
     * @return
     */
    boolean intersects(BoundingBox bb){
        return (!isEmpty() && !bb.isEmpty())
                && (xmin <= bb.xmax)
                && (xmax >= bb.xmin)
                && (ymin <= bb.ymax)
                && (ymax >= bb.ymin);
    }
    /**
     * Powiększa rozmiary tak, aby zawierał bb oraz poprzednią wersję this
     * @param bb
     * @return
     */
    BoundingBox add(BoundingBox bb){
        xmin = min(xmin, bb.xmin);
        ymin = min(ymin, bb.ymin);
        xmax = max(xmax, bb.xmax);
        ymax = max(ymax, bb.ymax);
        return this;
    }
    /**
     * Sprawdza czy BB jest pusty
     * @return
     */
    private boolean isEmpty(){
        return xmin.isNaN() || xmax.isNaN() || ymin.isNaN() || ymax.isNaN();
    }

    /**
     * Oblicza i zwraca współrzędną x środka
     * @return if !isEmpty() współrzędna x środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    private double getCenterX(){
        if(isEmpty()) throw new RuntimeException("Not implemented");
        return (xmin + xmax) / 2;
    }
    /**
     * Oblicza i zwraca współrzędną y środka
     * @return if !isEmpty() współrzędna y środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    private double getCenterY(){
        if(isEmpty()) throw new RuntimeException("Not implemented");
        return (ymin + ymax) / 2;
    }

    /**
     * Oblicza odległość pomiędzy środkami this bounding box oraz bbx
     * @param bbx prostokąt, do którego liczona jest odległość
     * @return if !isEmpty odległość, else wyrzuca wyjątek lub zwraca maksymalną możliwą wartość double
     * Ze względu na to, że są to współrzędne geograficzne, zamiast odległosci euklidesowej możesz użyć wzoru haversine
     * (ang. haversine formula)
     */
    double distanceTo(BoundingBox bbx){
        if(isEmpty()) throw new RuntimeException("Not implemented");
        double R = 6372.800;
        double dLat = Math.toRadians(Math.abs(bbx.getCenterY() - getCenterY()));
        double dLon = Math.toRadians(Math.abs(bbx.getCenterX() - getCenterX()));
        double a = Math.pow(Math.sin(dLat / 2), 2);
        double b = Math.pow(Math.sin(dLon / 2), 2) * Math.cos(bbx.getCenterY()) * Math.cos(getCenterY());
        return 2 * R * Math.asin(Math.sqrt(a + b));
    }

    @Override
    public String toString(){
        return "BoundingBox{" + "xmin=" + xmin + ", xmax=" + xmax + ", ymin=" + ymin + ", ymax=" + ymax + '}';
    }
}
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();
    Map<Long, AdminUnit> id2Unit = new HashMap<>();
    Map<AdminUnit, Long> unit2ParentId = new HashMap<>();
    Map<Long, List<AdminUnit>> parentId2Child = new HashMap<>();

    public void read(String filename) throws IOException {
        CSVReader reader = new CSVReader(filename, ",", true);
        while(reader.next()){
            AdminUnit unit = new AdminUnit();
            unit.name = reader.get("name");
            unit.adminLevel = reader.getInt("admin_level");
            unit.area = reader.getDouble("area");
            unit.density = reader.getDouble("density");
            unit.population = reader.getDouble("population");
            unit.bbox = new BoundingBox();
            unit.bbox.addPoint(reader.getDouble("x1"), reader.getDouble("y1"));
            unit.bbox.addPoint(reader.getDouble("x2"), reader.getDouble("y2"));
            unit.bbox.addPoint(reader.getDouble("x3"), reader.getDouble("y3"));
            unit.bbox.addPoint(reader.getDouble("x4"), reader.getDouble("y4"));
            units.add(unit);

            id2Unit.put(reader.getLong("id"), unit);
            unit2ParentId.put(unit, reader.getLong("parent"));
            long parentID = reader.getLong("parent");

            if(parentID != 0) {
                if (parentId2Child.containsKey(parentID)) {
                    parentId2Child.get(parentID).add(unit);
                } else {
                    List<AdminUnit> children = new ArrayList<>();
                    children.add(unit);
                    parentId2Child.put(parentID, children);

                }
            }

        }


        for(AdminUnit u: this.units){
            long par = unit2ParentId.get(u);
            u.parent = id2Unit.get(par); // ustawi null jesli par == 0
            if(u.parent != null){
                if (parentId2Child.containsKey(par)) u.parent.children = parentId2Child.get(par);
                else u.parent.children = new ArrayList<>();
            } else {
                u.children = new ArrayList<>();
            }
        }

        for(AdminUnit u: units){
            fixMissingValues(u);
        }
    }

    private void fixMissingValues(AdminUnit au) {

            if(au.parent != null && au.density == 0 && au.parent.density == 0) {
                fixMissingValues(au.parent);}
            if (au.parent != null && au.density == 0 && au.parent.density != 0){
                au.density = au.parent.density;
            }
            if (au.population == 0){
                // i tak w niektórych przypadkach pozostanie population = 0, ponieważ w kilku krotkach area = 0
                au.population = au.area * au.density;
            }
    }

    public void list(PrintStream out){
        for(AdminUnit unit: units){
            out.println(unit.toString());
        }
    }

    public void list(PrintStream out, int offset, int limit ){
        for(int i = offset; i < offset + limit; ++i){
            out.println(units.get(i).toString());
        }
    }

    private void add(AdminUnit au){
        units.add(au);
    }

    private AdminUnitList selectByName(String pattern, boolean regex){
        AdminUnitList ret = new AdminUnitList();
        for(AdminUnit unit:units){
            if(regex && unit.name.matches(pattern)){
                ret.add(unit);
            } else if (unit.name.contains(pattern)){
                ret.add(unit);
            }
        }
        return ret;
    }

    AdminUnit getUnit(String pattern){
        AdminUnitList r = this.selectByName(pattern,false);
        if(r.units.size() == 1){
            return r.units.get(0);
        }
        return null;
    }

    /**
     * Zwraca listę jednostek sąsiadujących z jendostką unit na tym samym poziomie hierarchii admin_level.
     * Czyli sąsiadami wojweództw są województwa, powiatów - powiaty, gmin - gminy, miejscowości - inne miejscowości
     * @param unit - jednostka, której sąsiedzi mają być wyznaczeni
     * @param maxdistance - parametr stosowany wyłącznie dla miejscowości, maksymalny promień odległości od środka unit,
     *                    w którym mają sie znaleźć punkty środkowe BoundingBox sąsiadów
     * @return lista wypełniona sąsiadami
     */
    AdminUnitList getNeighbours(AdminUnit unit, double maxdistance){
        AdminUnitList result = new AdminUnitList();
        for(AdminUnit u: units){
            if(unit.name.equals(u.name)){
                continue;
            }
            if(unit.adminLevel == u.adminLevel && unit.adminLevel == 11){
                if(unit.bbox.intersects(u.bbox) && (unit.bbox.distanceTo(u.bbox) <= maxdistance )){
                    result.add(u);
                }
            } else if (unit.adminLevel == u.adminLevel && unit.bbox.intersects(u.bbox)) {
                result.add(u);
            }
        }
        return result;
    }


    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */

    AdminUnitList sortInplaceByName() {
        class AdminUnitComparator implements Comparator<AdminUnit> {
            @Override
            public int compare(AdminUnit t, AdminUnit t1) {
                return t.name.compareTo(t1.name);
            }
        }
        units.sort(new AdminUnitComparator());
        return this;
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */

    AdminUnitList sortInplaceByArea(){
        units.sort(new Comparator<AdminUnit>() {
            @Override
            public int compare(AdminUnit t1, AdminUnit t2) {
                if(t1.area > t2.area) return -1;
                else if (t1.area == t2.area) return 0;
                else return 1;
            }
        });
        return this;
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */

    private AdminUnitList sortInplaceByPopulation(){
        units.sort((t1, t2) -> { // z compareTo byłyby 2 linie, ale wtedy nie chce poprawnie działać
            if(t1.population > t2.population) return -1;
            else if (t1.population == t2.population) return 0;
            else return 1;
        });
        return this;
    }

    AdminUnitList sortInplace(Comparator<AdminUnit> cmp) {
        units.sort(cmp);
        return this;
    }

    AdminUnitList sort(Comparator<AdminUnit> cmp) {
        AdminUnitList newUnits = new AdminUnitList();
        newUnits.units = new ArrayList<>(this.units);
        newUnits.units.sort(cmp);
        return newUnits;
    }

    AdminUnitList filter(Predicate<AdminUnit> pdc) {
        AdminUnitList result = new AdminUnitList();
        for (AdminUnit u : units) {
            if (pdc.test(u)) {
                result.units.add(u);
            }
        }
        return result;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred, int limit) {
        AdminUnitList result = new AdminUnitList();
        for (AdminUnit unit: units) {
            if (pred.test(unit)) {
                result.units.add(unit);
                if (result.units.size() == limit) break;
            }
        }
        return result;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred, int offset, int limit) {
        AdminUnitList result = new AdminUnitList();
        for (AdminUnit unit : units) {
            if (--offset >= 0) continue;
            if (pred.test(unit)) {
                result.units.add(unit);
                if (result.units.size() == limit) break;
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        AdminUnitList list = new AdminUnitList();
        list.read("admin-units.csv");
//        for(AdminUnit u: list.sortInplaceByArea().units){
//            System.out.println(u.population + " " + u.name + " " + u.area + " " + u.density);
//        }
        AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(list)
                .where(b->b.area>10)
                .and(b->b.name.startsWith("Sz"))
                .sort((b,a)->Double.compare(b.area,a.area))
                .limit(100);
        query.execute().list(System.out);
    }
}
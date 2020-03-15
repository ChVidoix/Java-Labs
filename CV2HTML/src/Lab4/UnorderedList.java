package Lab4;

import java.util.ArrayList;
import java.util.List;

public class UnorderedList{
    List<ListItem> items = new ArrayList<>();

    void writeHTML(){
        System.out.println("<ul>");
        for(ListItem i: items){
            i.writeHTML();
        }
        System.out.println("</ul>");
    }
}
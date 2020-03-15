package Lab4;

public class ListItem {
    String content;

    ListItem(String c){
        content = c;
    }

    void writeHTML(){
        System.out.println("<li>" + content + "</li>");
    }
}

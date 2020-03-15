package Lab4;

import java.util.ArrayList;
import java.util.List;

public class Document  {
    private String title;
    Photo photo;
    private List<Section> sections = new ArrayList<>();

    private Document(String t){
        title = t;
    }

    public void setTitle(String newTitle){
        title = newTitle;
    }

    private void addPhoto(String url){
        photo = new Photo(url);
    }

    private Section addSection(String s){
        sections.add(new Section(s));
        return sections.get(sections.size() - 1);
    }

    private void writeHTML(){
        System.out.println("<!DOCTYPE html>");
        System.out.println("<html>");
        System.out.println("<head>");
        System.out.println("<title>" + title + "</title>");
        System.out.println("</head>");
        System.out.println("<body>");
        System.out.println("<h1>" + title + "</h1>");
        photo.writeHTML();
        for(Section i: sections){
            i.writeHTML();
        }
        System.out.println("</body>");
        System.out.println("</html>");
    }

    public static void main(String[] args) {
        Document cv = new Document("Jana Kowalski - CV");
        cv.addPhoto("mojafotka");
        cv.addSection("Wykształcenie")
                .addParagraph("2000-2005 Przedszkole im. Królewny Snieżki w")
                .addParagraph("2006-2012 SP7 im Ronalda Regana w ...")
                .addParagraph("...");
        cv.addSection("Umiejętności")
                .addParagraph(
                        new ParagraphWithList("Umiejętności")
                                .addListItem("C")
                                .addListItem("C++")
                                .addListItem("Java")
                );


        cv.writeHTML();
    }
}

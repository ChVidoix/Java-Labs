package Lab4;

import java.util.ArrayList;
import java.util.List;

public class Section {
    String title;
    List<Paragraph> paragraphs = new ArrayList<>();

    Section(String t){
        title = t;
    }

    public void setTitle(String t){
        title = t;
    }

    Section addParagraph(String p){
        Paragraph para = new Paragraph(p);
        paragraphs.add(para);
        return this;
    }

    Section addParagraph(ParagraphWithList para){
        paragraphs.add(para);
        return this;
    }

    void writeHTML(){
        System.out.println("<div>");
        for(Paragraph p: paragraphs){
            p.writeHTML(System.out);
        }
        System.out.println("</div>");
    }
}

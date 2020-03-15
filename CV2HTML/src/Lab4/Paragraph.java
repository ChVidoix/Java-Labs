package Lab4;

import java.io.PrintStream;

public class Paragraph {
    String content;

    Paragraph(String content){
        this.content = content;
    }

    public void setContent(String c){
        content = c;
    }

    public void writeHTML(PrintStream out){
        out.println("<p>" + content + "</p>");
    }
}

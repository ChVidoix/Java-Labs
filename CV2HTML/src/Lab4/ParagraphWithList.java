package Lab4;

import java.io.PrintStream;

public class ParagraphWithList extends Paragraph{
    UnorderedList unorderedList;

    ParagraphWithList(String c){
        super(c);
        this.unorderedList = new UnorderedList();
    }

    public void setContent(String c){
        super.setContent(c);
    }

    ParagraphWithList addListItem(String s){
        unorderedList.items.add(new ListItem(s));
        return this;
    }

    @Override
    public void writeHTML(PrintStream out) {
        super.writeHTML(out);
        unorderedList.writeHTML();
    }
}

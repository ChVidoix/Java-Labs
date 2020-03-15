package Lab4;

import java.io.PrintStream;

class Photo {
    String url;

    Photo(String url){
        this.url = url;
    }
    void writeHTML(){
        System.out.printf("<img src=\"%s\" alt=\"twarz\" height=\"42\" width=\"42\"/>\n", url);
    }
}
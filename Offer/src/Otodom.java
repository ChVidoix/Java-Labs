import java.io.*;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Otodom {
    static String readWebsite(String link) throws IOException {
        URL url = new URL(link);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null){
            stringBuilder.append(inputLine);
            stringBuilder.append(System.lineSeparator());
        }
        in.close();
        return stringBuilder.toString();
    }

    static void saveWebsite(String content, String fileName) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, false));
        bufferedWriter.write(content);
        bufferedWriter.close();
    }

    static void download(String link, int threadsNumber) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);

        String content = readWebsite(link);
        Set<String> offerLinks = new TreeSet<>();

        for (int i = 0; i < content.length(); ++i) {
            i = content.indexOf("https://www.otodom.pl/oferta", i);
            if(i < 0){
                break;
            }
            String subLink = content.substring(i).split(".html")[0];
            offerLinks.add(subLink);
        }
        for (String offerLink : offerLinks) {
            executorService.submit(() -> {
                        try {
                            saveWebsite(readWebsite(offerLink),
                                    offerLink.substring(offerLink.indexOf("oferta/")+7) + ".html");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        executorService.shutdown();
    }

    public static void main(String[] args) throws Exception {
        download("https://www.otodom.pl/sprzedaz/mieszkanie/warszawa", 15);
        download("https://www.otodom.pl/sprzedaz/mieszkanie/krakow", 10);
        download("https://www.otodom.pl/sprzedaz/mieszkanie/gdansk", 30);
    }
}

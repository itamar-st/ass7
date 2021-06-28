import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParser {
    public void parseString(BufferedReader buffer, String outputPath) throws IOException {

        PrintWriter os = null;
        SuchAs suchAs = new SuchAs();
        String patternString = suchAs.getRegex();
        String line;
        int counter = 0;
        try {
            os = new PrintWriter( // wrapper with many ways of writing strings
                    new OutputStreamWriter( // wrapper that can write strings
                            new FileOutputStream(outputPath)));

        while ((line = buffer.readLine()) != null) { // 'null '->no more data in the stream
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(line);
            Pattern npPattern = Pattern.compile("<np>[^<]*</np>");
            while (matcher.find()) {
                String match = line.substring(matcher.start(), matcher.end());
                System.out.println(match);
                Matcher npMatcher = npPattern.matcher(match);
//                npMatcher.find();
//                String npMatch = match.substring(npMatcher.start(), npMatcher.end()) + ": ";
//                System.out.println(npMatch);
                ArrayList tmpList = new ArrayList();
                while (npMatcher.find()) {
                    String npMatch = match.substring(npMatcher.start(),
                            npMatcher.end()).replaceAll("<np>|</np>", "");
                    tmpList.add(npMatch);
//                    if (matcher.group(1) != null) {
//                        counter += 1;
//                    }
                    System.out.println(npMatch);
                    os.println(npMatch);

                }
            }
        }
            System.out.println("total!!! ! ! !! ! :    " + counter);
        } catch (IOException e) {
            System.out.println(" Something went wrong while writing !");
        } finally {
            if (os != null) { // Exception might have happened at constructor
                os.close(); // closes fileOutputStream too
            }
        }
    }



}

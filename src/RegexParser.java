import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParser {
    public void parseString(BufferedReader buffer, String outputPath) throws IOException {
        PrintWriter os = null;
        SuchXAs suchAs = new SuchXAs();
        String patternString = suchAs.getRegex();
        TreeMap<String, TreeMap<String, Integer>> map = new TreeMap<>();
        try {
            os = new PrintWriter(// wrapper with many ways of writing strings
                    new OutputStreamWriter(// wrapper that can write strings
                            new FileOutputStream(outputPath)));

            TreeMap<String, TreeMap<String, Integer>> mapTreeMap = writeToMap(buffer, os, patternString, map);
            Set<String> keys = mapTreeMap.keySet();
            for (String key: keys) {
                String outStr = key + ": ";
                TreeMap<String, Integer> innerMap = mapTreeMap.get(key);
                Set<String> innerSet = innerMap.keySet();
                for (String innerStr : innerSet) {
                    outStr = outStr + innerStr + " (" + innerMap.get(innerStr) + "), ";
                }
                System.out.println(outStr);
                os.println(outStr);

            }
        } catch (IOException e) {
            System.out.println(" Something went wrong while writing !");
        } finally {
            if (os != null) { // Exception might have happened at constructor
                os.close(); // closes fileOutputStream too
            }
        }
    }

    private TreeMap<String, TreeMap<String, Integer>> writeToMap(
            BufferedReader buffer, PrintWriter os, String patternString,
            TreeMap<String, TreeMap<String, Integer>> map) throws IOException {

        String line;
        while ((line = buffer.readLine()) != null) { // 'null '->no more data in the stream
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(line);
            Pattern npPattern = Pattern.compile("<np>[^<]*</np>");
            while (matcher.find()) {
                String match = line.substring(matcher.start(), matcher.end());
                System.out.println(match);
                Matcher npMatcher = npPattern.matcher(match);
                ArrayList<String> tmpList = new ArrayList<>();
                while (npMatcher.find()) {
                    String npMatch = match.substring(npMatcher.start(),
                            npMatcher.end()).replaceAll("<np>|</np>", "");
                    tmpList.add(npMatch);
                    System.out.println(npMatch);
//                    os.println(npMatch);
                }
                TreeMap<String, Integer> lineMap;
                if (map.containsKey(tmpList.get(0))) {
                    lineMap = map.get(tmpList.get(0));
                    for (int i = 1; i < tmpList.size(); i++) {
                        String currentHyper = tmpList.get(i);
                        if (lineMap.containsKey(currentHyper)) {
                            Integer j = lineMap.get(currentHyper);
                            lineMap.put(currentHyper, j + 1);
                        } else {
                            lineMap.put(tmpList.get(i), 1);
                        }
                    }
                } else {
                    lineMap = new TreeMap<>();
                    for (int i = 1; i < tmpList.size(); i++) {
                        lineMap.put(tmpList.get(i), 1);
                    }
                    map.put(tmpList.get(0), lineMap);
                }
            }
        }
        return map;
    }


}

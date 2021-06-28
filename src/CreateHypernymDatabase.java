import java.io.*;

public class CreateHypernymDatabase {
    public static void main(String[] args) {
        String corpusPath = args[0];
        File[] courpusDir = new File(corpusPath).listFiles();
        String outputPath = args[1];
        BufferedReader is = null;
        RegexParser regexParser = new RegexParser();
        try {
            for (File file : courpusDir) {
                is = new BufferedReader( // wrapper that reads ahead
                        new InputStreamReader( // wrapper that reads characters
                                new FileInputStream(file)));
                regexParser.parseString(is, outputPath);
//                String line;
//                while ((line = is.readLine()) != null) { // 'null '->no more data in the stream
//                    System.out.print(line);
//                }
            }
        } catch (IOException e1) {
            System.out.println(" Something went wrong while reading !");
        } catch (NullPointerException e2) {
            System.out.println(" no files in the directory!");
        } finally {
            if (is != null) { // Exception might have happened at constructor
                try {
                    is.close(); // closes FileInputStream too
                } catch (IOException e) {
                    System.out.println(" Failed closing the file !");
                }
            }
        }
    }
}

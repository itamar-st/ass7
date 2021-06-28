public class SuchAs {

    private String trigger = new String("such as");
    private String NP = new String("<np>[^<]*</np>");
    private String rawRegex = new String("<np>[^<]*</np>[ ,]*such as" +
            "[ ,]*<np>[^<]*</np>(([ ,]*<np>[^<]*</np>)*[ ,]*(and|or)?[ ,]*<np>[^<]*</np>)?");
    private String regex = new String(
            NP + "[ ,]*" + trigger + "[ ,]*" + NP + "(([ ,]*" + NP + ")*[ ,]*(and|or)?[ ,]*" + NP + ")?");

    public String getRegex() {
        return rawRegex;
    }
}

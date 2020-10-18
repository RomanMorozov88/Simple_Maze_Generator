package mazegenerator;

/**
 * Enum для символов стен и проходов, а так же рамки.
 */
public enum PathWallSymbols {

    STRING_PATH("   "),
    STRING_WALL("MMM"),
    STRING_HORIZON_BORDER("---"),
    STRING_VERTICAL_BORDER(" | ");

    private String value;

    PathWallSymbols(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

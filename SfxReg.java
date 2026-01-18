package src;

public enum SfxReg {
    // Between these brackets, write sound files as follows:
    // EXAMPLE("sounds/anyname.wav")

    TEST("sounds/thisdoesntexist.wav");

    private final String path;

    SfxReg(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}

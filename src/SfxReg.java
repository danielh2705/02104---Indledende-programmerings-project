package src;

// SfxReg.java written by Aran

public enum SfxReg {
    // Between these brackets, write sound files as follows:
    // EXAMPLE("resources\\sounds\\anyname.wav")

    APPLECONSUME("resources\\sounds\\appleConsume.wav"), // sourced from mixkit.co
    STARCONSUME("resources\\sounds\\starConsume.wav"), // sourced from Elden Ring
    MUSHROOMCONSUME("resources\\sounds\\mushroomConsume.wav"), // sourced from Super Mario Bros.
    COCONUTCONSUME("resources\\sounds\\coconutConsume.wav"), // sourced from Roblox
    POISONSHROOMCONSUME("resources\\sounds\\poisonShroomConsume.wav"), // sourced from Minecraft
    EXPLOSION("resources\\sounds\\explosion.wav"), // sourced from Roblox
    GAMEOVER("resources\\sounds\\gameOver.wav"); // sourced from Super Mario World

    private final String path;

    SfxReg(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}

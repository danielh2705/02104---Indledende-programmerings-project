// SfxReg.java written by Aran

public enum SfxReg {
    // Between these brackets, write sound files as follows:
    // EXAMPLE("/sounds/anyname.wav")

    APPLECONSUME("sounds/appleConsume.wav"), // sourced from mixkit.co
    STARCONSUME("sounds/starConsume.wav"), // sourced from Elden Ring
    MUSHROOMCONSUME("sounds/mushroomConsume.wav"), // sourced from Super Mario Bros.
    COCONUTCONSUME("sounds/coconutConsume.wav"), // sourced from Roblox
    POISONSHROOMCONSUME("sounds/poisonShroomConsume.wav"), // sourced from Minecraft
    EXPLOSION("sounds/explosion.wav"), // sourced from Roblox
    GAMEOVER("sounds/gameOver.wav"); // sourced from Super Mario World

    private final String path;

    SfxReg(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}

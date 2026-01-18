package src;

// SfxReg.java written by Aran

public enum SfxReg {
    // Between these brackets, write sound files as follows:
    // EXAMPLE("recourses\\sounds\\anyname.wav")

    APPLECONSUME("recourses\\sounds\\appleConsume.wav"), // sourced from mixkit.co
    STARCONSUME("recourses\\sounds\\starConsume.wav"), // sourced from Elden Ring
    MUSHROOMCONSUME("recourses\\sounds\\mushroomConsume.wav"), // sourced from Super Mario Bros.
    COCONUTCONSUME("recourses\\sounds\\coconutConsume.wav"), // sourced from Roblox
    POISONSHROOMCONSUME("recourses\\sounds\\poisonShroomConsume.wav"), // sourced from Minecraft
    EXPLOSION("recourses\\sounds\\explosion.wav"), // sourced from Roblox
    GAMEOVER("recourses\\sounds\\gameOver.wav"); // sourced from Super Mario World

    private final String path;

    SfxReg(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}

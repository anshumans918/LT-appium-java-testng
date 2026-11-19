import io.github.cdimascio.dotenv.Dotenv;

/**
 * Loads LambdaTest credentials from the .env file in the project root.
 * Values are read from .env only (OS environment variables are ignored).
 */
public final class LTCredentials {

    private static final Dotenv DOTENV = Dotenv.configure()
            .directory(System.getProperty("user.dir"))
            .filename(".env")
            .load();

    public static final String USERNAME = require("LT_USERNAME");
    public static final String ACCESS_KEY = require("LT_ACCESS_KEY");

    private LTCredentials() {
    }

    private static String require(String key) {
        // DECLARED_IN_ENV_FILE returns only values defined in .env, not OS env vars
        String value = DOTENV.entries(Dotenv.Filter.DECLARED_IN_ENV_FILE).stream()
                .filter(e -> e.getKey().equals(key))
                .map(e -> e.getValue())
                .findFirst()
                .orElse(null);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException(key + " is not set in the .env file");
        }
        return value.trim();
    }
}

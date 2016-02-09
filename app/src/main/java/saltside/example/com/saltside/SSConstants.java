package saltside.example.com.saltside;

/**
 * Created by Ajay.Davanam on 2/8/2016.
 *
 * All constants used in the app.
 */
public class SSConstants {
    /** The backend URI to connect */
    public static final String HOST_NAME = "https://gist.githubusercontent.com/maclir/" +
            "f715d78b49c3b4b3b77f/raw/8854ab2fe4cbe2a5919cea97d71b714ae5a4838d/items.json";

    /** Connection timeout */
    public static final int CONN_TIMEOUT = 15000;

    /** HTTP read timeout */
    public static final int HTTP_TIMEOUT = 30000;

    /** HTTP read chunk size */
    public static final int READ_CHUNK_SIZE = (16 * 1024);

    /** Image key */
    public static final String RES_KEY_IMAGE = "image";

    /** Description key */
    public static final String RES_KEY_DESCRIPTION = "description";

    /** Title key */
    public static final String RES_KEY_TITLE = "title";
}

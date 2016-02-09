package saltside.example.com.saltside;

/**
 * Created by Ajay.Davanam on 2/8/2016.
 *
 * Detailed info of each item
 */
public class SSItemDetailedInfo {

    /** Title String */
    private String mStrTitle;

    /** Image URL */
    private String mStrImageURL;

    /** Description String*/
    private String mStrDescription;

    /**
     * @return Return the title (String)
     */
    public String getTitle() {
        return mStrTitle;
    }

    /**
     * Set the title
     *
     * @param strTitle title string
     */
    public void setTitle(String strTitle) {
        this.mStrTitle = strTitle;
    }

    /**
     * @return Return the image url (String)
     */
    public String getImageURL() {
        return mStrImageURL;
    }

    /**
     * Set the image URL
     *
     * @param strImageURL image URL string
     */
    public void setImageURL(String strImageURL) {
        this.mStrImageURL = strImageURL;
    }

    /**
     * @return Get the description (String)
     */
    public String getDescription() {
        return mStrDescription;
    }

    /**
     * Set the description
     *
     * @param strDescription the description string
     */
    public void setDescription(String strDescription) {
        this.mStrDescription = strDescription;
    }
}

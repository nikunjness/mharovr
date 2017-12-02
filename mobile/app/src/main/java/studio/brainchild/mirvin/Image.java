package studio.brainchild.mirvin;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

/**
 * Created by viraj on 26-01-2017.
 */

public class Image implements Serializable {
    private String mTitle;
    private URL mURL;
    private String mId;
    private Bitmap mBitmap;


    public Image(String mId, String mTitle, URL mURL) throws IOException {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mURL = mURL;
        this.mBitmap = null;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    @Override
    public String toString() {
        return "Image{" +
                "mTitle='" + mTitle + '\'' +
                ", mURL=" + mURL +
                ", mId='" + mId + '\'' +
                '}';
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public URL getURL() {
        return mURL;
    }

    public void setURL(URL mURL) {
        this.mURL = mURL;
    }


    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }
}

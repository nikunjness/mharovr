package studio.brainchild.mirvin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by viraj on 26-01-2017.
 */

public class ImageDownloaderTask extends AsyncTask<URL, Void, Bitmap> {
    Bitmap bitmap;
    private AsynResponse response = null;
    private String TAG = "ImageDownloaderTask";

    ImageDownloaderTask(AsynResponse asynResponse) {
        this.response = asynResponse;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
//        Log.i(TAG, "onPostExecute: " + bitmap.toString());
        response.processFinish(bitmap);
    }

    @Override
    protected Bitmap doInBackground(URL... params) {

        URL[] urls = params;

        for (URL url : urls) {
            try {

                Log.i(TAG, "doInBackground: " + url);
                InputStream ip = url.openConnection().getInputStream();
                Log.i(TAG, "doInBackground: " + ip);
                bitmap = BitmapFactory.decodeStream(ip);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmap;

    }


}

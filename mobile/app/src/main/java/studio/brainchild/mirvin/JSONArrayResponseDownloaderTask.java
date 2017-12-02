package studio.brainchild.mirvin;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by viraj on 28-01-2017.
 */

public class JSONArrayResponseDownloaderTask extends AsyncTask<String, Void, Image[]> {

    private final String TAG = "JSONDownloaderTask";
    private  String URL = "";
    String key;
    private AsynResponse response = null;

    JSONArrayResponseDownloaderTask(AsynResponse response, String key, String url) {
        this.response = response;
        this.key = key;
        this.URL = url;
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    @Override
    protected Image[] doInBackground(String... params) {
        Image[] images = null;
        String[] urls = params;

        for (String url : urls) {
            try {
                String response = readUrl(url);
                Log.i(TAG, "doInBackground: " + url);
                JSONArray arr = new JSONArray(response);
//                JSONArray arr = jsonObjectResp.getJSONArray(key);
                images = new Image[arr.length()];
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonObject = arr.getJSONObject(i);
                    String id = jsonObject.getString("imageId");
                    String title = jsonObject.getString("imageTitle");
                    URL imageURL = new URL(URL+jsonObject.getString("imageURL"));
                    images[i] = new Image(id, title, imageURL);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return images;
    }

    @Override
    protected void onPostExecute(Image[] images) {
        response.processFinish(images);
    }
}

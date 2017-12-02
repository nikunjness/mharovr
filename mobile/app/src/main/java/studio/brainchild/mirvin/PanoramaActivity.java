package studio.brainchild.mirvin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.net.URL;

public class PanoramaActivity extends AppCompatActivity {

    private String URL = "";
    private String TAG = "PanoramaActivity";
    private VrPanoramaView panoWidgetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);

//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        URL = this.getResources().getString(R.string.server_url)+"retriveThumbanilImages.php?thumbId=";
        Intent i = getIntent();

        Image img = (Image) i.getSerializableExtra("Image");

        new JSONObjectResponseDownloaderTask(new AsynResponse() {
            @Override
            public void processFinish(Object output) {

                Image img = ((Image) output);

                try {
                    loadPanoImage(img.getURL());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, "resp",  this.getResources().getString(R.string.server_url)).execute(URL + img.getId());

        Log.i(TAG, "onCreate: " + img.toString());

        panoWidgetView = (VrPanoramaView) findViewById(R.id.pano_view);

        panoWidgetView.setTouchTrackingEnabled(true);
        panoWidgetView.setInfoButtonEnabled(false);
        panoWidgetView.setClickable(true);
        panoWidgetView.setFocusable(true);
        panoWidgetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked(v);
            }
        });
    }

    private void loadPanoImage(URL url) throws Exception {

        final VrPanoramaView.Options viewOptions = new VrPanoramaView.Options();
        viewOptions.inputType = VrPanoramaView.Options.TYPE_MONO;

        new ImageDownloaderTask(new AsynResponse() {
            @Override
            public void processFinish(Object output) {
                panoWidgetView.loadImageFromBitmap((Bitmap) output, viewOptions);
            }
        }).execute(url);

    }

    @Override
    public void onPause() {
        panoWidgetView.pauseRendering();
        super.onPause();
    }

    @Override
    public void onResume() {
        panoWidgetView.resumeRendering();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        // Destroy the widget and free memory.
        panoWidgetView.shutdown();
        super.onDestroy();
    }

    public void clicked(View view) {

        VrPanoramaView p = (VrPanoramaView) view;
        float x = p.getX();
        Log.i(TAG, "onClick: " + x);
        Toast.makeText(this, "PanClick", Toast.LENGTH_SHORT).show();
    }

}

package studio.brainchild.mirvin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class GridViewActivity extends AppCompatActivity {

    private final String TAG = "GridViewActivity";
    private String URL = "";
    GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        URL = this.getResources().getString(R.string.server_url);

        new JSONArrayResponseDownloaderTask(new AsynResponse() {
            @Override
            public void processFinish(final Object output) {


                Log.i(TAG, "processFinish: "+((Image[])output));
                mGridView.setAdapter(new ImageAdapter(getBaseContext(), (Image[]) output));
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        Toast.makeText(GridViewActivity.this, "" + id, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(GridViewActivity.this, PanoramaActivity.class).putExtra("Image", ((Image[]) output)[position]));

                    }
                });
            }
        }, "resp",  this.getResources().getString(R.string.server_url)).execute(URL+"retriveThumbnail.php");



        mGridView = (GridView) findViewById(R.id.activity_grid_view);

    }


}

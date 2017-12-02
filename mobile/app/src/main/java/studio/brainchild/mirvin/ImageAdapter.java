package studio.brainchild.mirvin;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by viraj on 26-01-2017.
 */

public class ImageAdapter extends BaseAdapter {
    private final Image[] mImages;
    LayoutInflater inflater;
    private String TAG = "ImageAdapter";
    private Context mContext;


    public ImageAdapter(Context mContext, Image[] mImages) {
        this.mContext = mContext;
        this.mImages = mImages;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public Object getItem(int position) {
        return mImages[position];
    }

    @Override
    public long getItemId(int position) {
        return Long.decode(mImages[position].getId());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.list_adapter_layout, null);
            final ImageView imageView = (ImageView) grid.findViewById(R.id.imageView);
            new ImageDownloaderTask(new AsynResponse() {
                @Override
                public void processFinish(Object output) {
                    imageView.setImageBitmap((Bitmap) output);

                }
            }).execute(mImages[position].getURL());

        } else {
            grid = (View) convertView;
        }

        return grid;


    }
}

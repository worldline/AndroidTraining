package worldline.ssm.rd.ux.wltwitter.async;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import worldline.ssm.rd.ux.wltwitter.helpers.TwitterHelper;

public class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    // The ImageView in which to display the image
    private final ImageView mImageView;



    public DownloadImageAsyncTask (ImageView imageView){
        mImageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        // Retrieve the image url in params
        if ((null != params) && (params.length > 0)){
            final String imageUrl = params[0];
            try {
                final Bitmap bitmap = TwitterHelper.getTwitterUserImage(imageUrl);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if ((null != result) && (null != mImageView)){
            mImageView.setImageBitmap(result);
        }
    }
}

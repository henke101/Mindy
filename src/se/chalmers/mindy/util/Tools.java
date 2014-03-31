package se.chalmers.mindy.util;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.widget.ImageView;

public class Tools {
	public static String getCurrentDateTime() {
		return getStringFromDate(new Date());
	}

	public static String getStringFromDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

		return dateFormat.format(date);
	}

	public static Date getDateFromString(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

		Date date;

		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {
			Log.e("Tools.getDateFromString() error: Cannot parse string ", e.getLocalizedMessage());
			date = new Date();
		}

		return date;
	}

	public static boolean isRuntimePastIceCreamSandwich() {
		return android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
	}

	public static Bitmap renderBlur(Context context, Bitmap originalBitmap) {

		Bitmap outBitmap = originalBitmap.copy(originalBitmap.getConfig(), true);

		// Create the context and I/O allocations
		final RenderScript rs = RenderScript.create(context);
		final Allocation input = Allocation.createFromBitmap(rs, originalBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
		final Allocation output = Allocation.createTyped(rs, input.getType());

		// Blur the image
		final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
		script.setRadius(10f);
		script.setInput(input);
		script.forEach(output);
		output.copyTo(outBitmap);

		// We don't need RenderScript anymore
		rs.destroy();

		return outBitmap;

	}

	/** 
	 * Sets the image source of an ImageView in two steps; first loads low-res version of an image, then loads the high-res version in a background thread in order not to block UI thread.
	 *
	 * @param context Application context 
	 * @param drawableId The Id of the image to set as source for the ImageView
	 * @param imageView The view to alter
	 */
	public static void setTwoStepBitmapBackground(final Context context, final int drawableId, final ImageView imageView) {

		imageView.setImageBitmap(Tools.decodeSampledBitmapFromResource(context.getResources(), drawableId, 100, 50));

		// Load the background image in a background thread
		Tools.loadBitmap(context, drawableId, imageView);
	}

	public static void loadBitmap(Context context, int resId, ImageView imageView) {
		BitmapWorkerTask task = new BitmapWorkerTask(context, imageView);
		task.execute(resId);
	}

	/**
	 * 
	 * Loads a Bitmap in a background thread, in order to avoid blocking the UI thread
	 *
	 */
	private static class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
		private final WeakReference<ImageView> imageViewReference;
		private final Context context;
		private int data = 0;

		public BitmapWorkerTask(Context context, ImageView imageView) {
			// Use a WeakReference to ensure the ImageView can be garbage
			// collected
			imageViewReference = new WeakReference<ImageView>(imageView);
			this.context = context;
		}

		// Decode image in background.
		@Override
		protected Bitmap doInBackground(Integer... params) {
			data = params[0];
			return renderBlur(context, decodeSampledBitmapFromResource(context.getResources(), data, 100, 50));
		}

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (imageViewReference != null && bitmap != null) {
				final ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}

	}

	private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both height and width larger than the requested height and
			// width.
			while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
}

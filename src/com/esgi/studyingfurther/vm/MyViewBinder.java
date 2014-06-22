package com.esgi.studyingfurther.vm;

import android.graphics.Bitmap;
import android.widget.SimpleAdapter.ViewBinder;
import android.view.View;
import android.widget.ImageView;

public class MyViewBinder implements ViewBinder {

	@Override
	public boolean setViewValue(View view, Object data, String arg2) {
		// TODO Auto-generated method stub
		if ((view instanceof ImageView) & (data instanceof Bitmap)) {
			ImageView iv = (ImageView) view;
			Bitmap bm = (Bitmap) data;
			iv.setImageBitmap(bm);
			return true;
		}

		return false;
	}

}

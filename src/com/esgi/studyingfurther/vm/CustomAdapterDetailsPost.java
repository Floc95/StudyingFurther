package com.esgi.studyingfurther.vm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import com.esgi.studyingfurther.R;
import com.esgi.studyingfurther.bl.Comment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

@SuppressLint({ "NewApi", "DefaultLocale" })
public class CustomAdapterDetailsPost extends BaseAdapter {

	private final ArrayList<Comment> listItem;
	private final Context context;

	public CustomAdapterDetailsPost(Context context, ArrayList<Comment> listItem) {

		this.context = context;
		this.listItem = listItem;

	}

	@Override
	public int getCount() {

		return listItem.size();
	}

	@Override
	public Object getItem(int arg0) {

		return listItem.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.activity_items__comments,parent, false);

			holder = new ViewHolder(convertView);

			convertView.setTag(holder);

		}

		  holder = (ViewHolder) convertView.getTag();
		
		  Comment comment  = listItem.get(position);
		
		  try {
			 holder.contenu.setText(MainViewModel.decodeString(comment.getContent()));
			 holder.heurPubP.setText(comment.getCreationDate());
			  if(comment.getstatutCurrentUser()==3) 
			  {
				  holder.plusun.setVisibility(View.VISIBLE);
			  } 
			  if(comment.getPlusun().toLowerCase().trim().equals("true")) 
			  {
				  holder.plusun.setChecked(true); 
			  }
			  Drawable BitmapDrawable=new BitmapDrawable(convertView.getResources(), comment.getAvatar());
			  holder.avatarP.setBackground(BitmapDrawable);
			  
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		return convertView;
	}

	private static class ViewHolder {

		private final ToggleButton plusun;
		private final TextView contenu;
		private final TextView heurPubP;
		private final ImageView avatarP;

		public ViewHolder(View view) {
			plusun = (ToggleButton) view.findViewById(R.id.plusun);
			contenu = (TextView) view.findViewById(R.id.contenu);
			heurPubP = (TextView) view.findViewById(R.id.heurPubP);
			avatarP = (ImageView) view.findViewById(R.id.avatarP);

		}
	}

}

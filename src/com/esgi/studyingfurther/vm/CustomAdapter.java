package com.esgi.studyingfurther.vm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.esgi.studyingfurther.R;
import com.esgi.studyingfurther.bl.Post;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

@SuppressLint({ "NewApi", "DefaultLocale" })
public class CustomAdapter extends BaseAdapter {
	
	private final ArrayList<Post> listItem;
	private final Context context;
	
	public CustomAdapter(Context context, ArrayList<Post> listItem ) {
		
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
			
			convertView = inflater.inflate(R.layout.activity_item_news_feed, parent, false);
			
			holder = new ViewHolder(convertView);
			
			convertView.setTag(holder);
			
		

		}
		
		
			
		holder = (ViewHolder) convertView.getTag();	
		Post post = listItem.get(position);	
		Log.v("post",post.getStatutCurrentUser()+"");
		try {
			holder.contenu.setText(MainViewModel.decodeString(post.getContent()));
			holder.heurPubP.setText(post.getCreationDate());
			holder.nbcommentaires.setText(post.getNbcommentaires()+"");
			Drawable btmpDrawable=new BitmapDrawable(convertView.getResources(), post.getImage());
			holder.newspic.setBackground(btmpDrawable);
			if(post.getStatutCurrentUser()==3)
			{
			  holder.plusun.setVisibility(View.VISIBLE);
			}
			if( post.getPlusun().toLowerCase().trim().equals("true"))
			{
				holder.plusun.setChecked(true);
			}
			holder.titleP.setText(post.getTitle());
			Drawable avatar=new BitmapDrawable(convertView.getResources(), post.getAvatar());
			holder.avatarP.setBackground(avatar);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		return convertView;
	}
	
  
	
	
	private static  class ViewHolder {
		
	    private final ToggleButton plusun;
		private final TextView titleP;
		private final TextView contenu;
		private final TextView heurPubP;
		private final TextView nbcommentaires;
		private final ImageView newspic;
		private final ImageView avatarP;
		
	    public ViewHolder(View view) {
	    	plusun = (ToggleButton) view.findViewById(R.id.plusun);
	    	titleP = (TextView) view.findViewById(R.id.titleP);
	    	contenu = (TextView) view.findViewById(R.id.contenu);
	    	heurPubP = (TextView) view.findViewById(R.id.heurPubP);
	    	nbcommentaires = (TextView) view.findViewById(R.id.nbcommentaires);
	    	newspic = (ImageView) view.findViewById(R.id.newspicdetails);
	    	avatarP = (ImageView) view.findViewById(R.id.avatarP);
	    	
		}
	}

}

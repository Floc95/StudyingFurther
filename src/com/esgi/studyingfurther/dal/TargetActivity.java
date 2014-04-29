import android.app.Activity;

import android.os.Bundle;

 

public class TargetActivity extends Activity {

  @Override

  protected void onCreate(Bundle savedInstanceState) {

      // TODO Auto-generated method stub

      super.onCreate(savedInstanceState);

      setContentView(R.layout.main);

      long lv_item_id = getIntent().getLongExtra("lv_item_id", -1);
	  title = (TextView)this.findViewById(R.id.title);
      Bundle bundle = this.getIntent().getExtras();
      int id = bundle.getInt("lv_item_id");
      title.setText(id+"");
      if(lv_item_id != -1) {

         

      }

  }

}

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
 
public class ListviewActivity extends ListActivity implements OnItemClickListener {

    /** Called when the activity is first created. */

    @Override
	private List<Post> list = new LinkedList<Post>();

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

      ListView mListView = getListView();
	//mylistview = (ListView)findViewById(R.id.listview);
	
	@TODO
	list = getPost();

      mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list));

      mListView.setOnItemClickListener(this);

  }



  @Override

  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    Intent intent = new Intent(this,TargetActivity.class);
	
	Bundle bundle=new Bundle();
	
	
	bundle.putInt("lv_item_id", id);

    intent.putExtra(bundle);

    startActivity(intent);

  }



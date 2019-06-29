package myAi.Knowanything.windows;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;
import java.util.ArrayList;
import android.widget.Toast;
import java.util.Arrays;
import java.util.Comparator;
import android.content.Intent;
import android.kz.Toast.Toastkeeper;

public class ChoiceTextActivity extends Activity
{

	private FrameLayout copyNodeViewContainer;
	

	private int height = 15;

	
ArrayList<String> output=new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);

		setContentView(R.layout.choicetext_layout);

		copyNodeViewContainer = (FrameLayout)findViewById(R.id.framelayout_root);

		Bundle extras = getIntent().getExtras();
		if (extras==null){
			finish();
			return;
		}
		extras.setClassLoader(ContentNode.class.getClassLoader());
		
		ArrayList<ContentNode> nodeList = extras.getParcelableArrayList("text_nodes");

		if(nodeList!=null && nodeList.size()>0){
			toa("开始历遍");
			//Toast.makeText(this,"开始遍历",Toast.LENGTH_SHORT).show();
			ContentNode[] nodes = (ContentNode[])nodeList.toArray(new ContentNode[0]);
			Arrays.sort(nodes,new CopyNodeComparator());
			output.clear();
			for(int i  = 0; i < nodes.length; ++i) {
				
				if(!nodes[i].getContent().equals("")){
		
					(new TextNodeView(this, nodes[i])).addToFrameLayout(copyNodeViewContainer, height);
					output.add((new TextNodeView(this, nodes[i])).getContentDescription().toString());
//					Log.e("ChoiceTectActivity.onCreate()",
//						"addedBound:left"+nodes[i].getNodeRect().left
//						+ " top"+ nodes[i].getNodeRect().top
//						+ " right"+ nodes[i].getNodeRect().right
//						+ " bottom"+ nodes[i].getNodeRect().bottom
//						+ " width"+nodes[i].getNodeRect().width()
//						+ " height"+nodes[i].getNodeRect().height());
				}
			}
			Intent i =new Intent(ChoiceTextActivity.this,show.class);
			i.putStringArrayListExtra("data",output);
			startActivity(i);

			
			
		}
	}
	void toa(String in){

		Toastkeeper.getInstance()
			.createBuilder(this)
			.setMessage(in)
			.setGravity(Toastkeeper.GRAVITY_TOP)
			.show();
	}
	public class CopyNodeComparator implements Comparator<ContentNode> {
		//按面积从大到小排序
		public int compare(ContentNode o1, ContentNode o2) {
			long o1Size = o1.caculateSize();
			long o2Size = o2.caculateSize();
			return o1Size < o2Size?-1:(o1Size == o2Size?0:1);
		}
	}

}


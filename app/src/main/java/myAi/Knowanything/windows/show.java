package myAi.Knowanything.windows;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import java.util.ArrayList;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.view.View;
import android.net.Uri;
import android.os.Build;


public class show extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		setContentView(R.layout.show);
		super.onCreate(savedInstanceState);
		
		Intent i =getIntent();

       ArrayList<String> put= i.getStringArrayListExtra("data");
		
		WarpLinearLayout fixGridLayout = (WarpLinearLayout) findViewById(R.id.showFixGridLayout1);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( 
			ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); 
		
		for(String o:put){
			TextView viewright = new TextView(this); 
			viewright.setLayoutParams(lp);
			viewright.setText(o);
			viewright.setBackgroundResource(R.drawable.shape);
			viewright.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						
						Intent intent= new Intent();        
						intent.setAction("android.intent.action.VIEW"); 
						TextView a=(TextView) p1;
						Uri content_url = Uri.parse("http://www.baidu.com/s?wd="+a.getText().toString());   
						intent.setData(content_url);  
						startActivity(intent);
					}
				});
			fixGridLayout.addView(viewright);
		}
			
		}
	
}

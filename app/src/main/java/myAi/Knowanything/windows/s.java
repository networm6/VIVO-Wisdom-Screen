package myAi.Knowanything.windows;
import android.kz.BaseBean.Base;
import android.os.Bundle;
import android.widget.ImageView;
import android.content.Intent;
import com.nostra13.universalimageloader.core.ImageLoader;

public class s extends Base
{
	
ImageView ss;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		inlayout(R.layout.s);
        super.onCreate(savedInstanceState);
		ss=findViewById(R.id.sImageView1);
		Intent i =getIntent();

		String put= i.getStringExtra("data");
		ImageLoader .getInstance().displayImage(put,ss);
		
		}
		
}

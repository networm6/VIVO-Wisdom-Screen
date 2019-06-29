package myAi.Knowanything.windows;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.widget.Toast;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.graphics.Color;

public class TextNodeView extends View {
    private Rect bound;
    private String content;
   
	private Context context;

	private boolean isSelected;

    public TextNodeView(final Context context, ContentNode contentNode) {
        super(context);
		this.context = context;
        this.bound = contentNode.getNodeRect();
        this.content = contentNode.getContent();
		setContentDescription(content);

		isSelected = false;
		changeBackground(isSelected);
		setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
//					if(isSelected){
//						isSelected = false;
//					}else{
//						isSelected = true;
//					}
//					
//					changeBackground(isSelected);

					ClipData mClipData = ClipData.newPlainText("Simple test", TextNodeView.this.getContentDescription().toString());
					ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
					mClipboardManager.setPrimaryClip(mClipData);
					Toast.makeText(TextNodeView.this.context,TextNodeView.this.getContentDescription().toString(),0).show();

				}

			});
    }

    public void addToFrameLayout(FrameLayout frameLayout, int height) {
        LayoutParams var3 = new LayoutParams(this.bound.width(), this.bound.height());
        var3.leftMargin = this.bound.left;

		var3.topMargin = Math.max(0,this.bound.top-60);
        var3.width = this.bound.width();
        var3.height = this.bound.height();
        frameLayout.addView(this,0,var3);

		//Log.e("TextNodeView",">>>>>>addToFramLayout");
    }

	public void changeBackground(boolean state){
		if(state){
			setBackgroundColor(Color.parseColor("#000000"));
			setAlpha(0.5f);
		}else{
			setBackgroundColor(Color.parseColor("#6699FF"));
			setAlpha(0.5f);
		}
	}
}



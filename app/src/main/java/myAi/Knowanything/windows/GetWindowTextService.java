package myAi.Knowanything.windows;
import android.view.WindowManager;
import android.widget.Button;
import android.os.Handler;
import java.util.ArrayList;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityEvent;
import android.accessibilityservice.AccessibilityService;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;
import android.util.Log;
import android.util.DisplayMetrics;
import android.content.Intent;
import android.app.ActivityOptions;
import android.graphics.Rect;
import android.os.Build;
import android.graphics.Color;
import android.content.pm.PackageManager;
import android.view.Gravity;
import android.content.Context;
import android.kz.Toast.Toastkeeper;

public class GetWindowTextService extends AccessibilityService
{

	private static WindowManager.LayoutParams wmParams;

	private WindowManager mWindowManager;

	private Button button_start;

	private Handler handler = new Handler();

	public static ArrayList<ContentNode> nodeList = new ArrayList<>();

	public static ArrayList<AccessibilityNodeInfo> iaWindowList = new ArrayList<>();

	@Override
	public void onAccessibilityEvent(AccessibilityEvent p1)
	{
		// TODO: Implement this method
	}
	void toa(String in){

		Toastkeeper.getInstance()
			.createBuilder(this)
			.setMessage(in)
			.setGravity(Toastkeeper.GRAVITY_TOP)
			.show();
	}
	@Override
	public void onInterrupt()
	{
		// TODO: Implement this method
	}

	@Override
	public void onCreate()
	{
		toa("服务开始");
		//Toast.makeText(getApplicationContext(),"服务已创建",Toast.LENGTH_SHORT).show();
		super.onCreate();

		createFloatWindow();

		button_start.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
				    getScreenContent();
				}


			});
		button_start.setOnLongClickListener(new View.OnLongClickListener() { //其实就是增加了长按监听事件
				@Override
				public boolean onLongClick(View v) {
					toa("悬浮窗关闭，到软件里可以重新启用");
				
					android.os.Process.killProcess(android.os.Process.myPid());
					return false;
				}
			});
	}

	int i = 0;
	public void getScreenContent(){
		if(i<10){	
			AccessibilityNodeInfo rootInActiveWindow = this.getRootInActiveWindow();

			String packageName;
			if(rootInActiveWindow != null) {
				packageName = String.valueOf(rootInActiveWindow.getPackageName());
			} else {
				packageName = null;
			}


			if(rootInActiveWindow == null){
				Log.e("eeeee",">>>>>>>>rootInActivitveWindow:null");
				handler.postDelayed(new Runnable(){

						@Override
						public void run()
						{
							i++;
							getScreenContent();
						}


					},100);
				return;
			}

			//获取屏幕高宽，用于遍历数据时确定边界。
			WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics displayMetrics = new DisplayMetrics();
			windowManager.getDefaultDisplay().getMetrics(displayMetrics);
			int heightPixels = displayMetrics.heightPixels;
			int widthPixels = displayMetrics.widthPixels;

			ergodicInActiveWindow(rootInActiveWindow);

			nodeList = getWindowNodeContent(iaWindowList,widthPixels,heightPixels);

			Intent intent = new Intent(GetWindowTextService.this,ChoiceTextActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putParcelableArrayListExtra("text_nodes",nodeList);
			intent.putExtra("source_package", packageName);

			startActivity(intent, ActivityOptions.makeCustomAnimation(this.getBaseContext(), android.R.anim.fade_in, android.R.anim.fade_out).toBundle());

			iaWindowList = new ArrayList<>();
			nodeList = new ArrayList<>();
		}
	}


	/**
     * 该方法利用递归来遍历根窗口下的所有子对象，将其添加到iaWindowList中
     *
     * @param rootInActiveWindow 要遍历的根窗口对象
     * @param screenWidth 屏幕宽
     * @param screenHeight 屏幕高
     */
    public void ergodicInActiveWindow(AccessibilityNodeInfo rootInActiveWindow) {
        if (rootInActiveWindow != null) {
            for (int i = 0; i < rootInActiveWindow.getChildCount(); i++) {
                iaWindowList.add(rootInActiveWindow.getChild(i));
                ergodicInActiveWindow(rootInActiveWindow.getChild(i));
            }
        }
    }


    /**
     * 该方法用于遍历数组中的窗口对象，获取它们的文本内容和视图信息，
     * 然后保存到数据结构ContentNode中，并添加到数组
     *
     * @param windowNodeList 窗口对象数组
     * @return ContentNode数组
     */
    public ArrayList<ContentNode> getWindowNodeContent(ArrayList<AccessibilityNodeInfo> windowNodeList,int screenWidth,int screenHeight) {
        ArrayList<ContentNode> nodeList = new ArrayList<>();

        if (windowNodeList == null) {
            return null;
        }

        for (AccessibilityNodeInfo windowNode : windowNodeList) {
            if (windowNode != null) {
                String contentText = "";

				if(windowNode.getText() != null){
					contentText = windowNode.getText().toString();
				}else if(windowNode.getContentDescription() != null){
					contentText = windowNode.getContentDescription().toString();
				}
                Rect bound = new Rect();
                windowNode.getBoundsInScreen(bound);

                if(checkBound(bound,screenWidth,screenHeight)){
					nodeList.add(new ContentNode(bound, contentText));

					Log.e("GetWindowTextService.getWindowNodeContent()",
						  "Bound:left"+bound.left
						  + " top"+bound.top
						  + " right"+bound.right
						  + " bottom"+bound.bottom
						  + " width"+bound.width()
						  + " height"+bound.height());
				}
            }
        }
        return nodeList;
    }

//	@Override
//	public int onStartCommand(Intent intent, int flags, int startId)
//	{
//		Toast.makeText(getApplicationContext(),"服务已通过startService启动",Toast.LENGTH_SHORT).show();
//		// TODO: Implement this method
//		return super.onStartCommand(intent, flags, startId);
//	}
//
//	@Override
//	public void onStart(Intent intent, int startId)
//	{
//		Toast.makeText(getApplicationContext(),"服务已启动",Toast.LENGTH_SHORT).show();
//		// TODO: Implement this method
//		super.onStart(intent, startId);
//	}

	@Override
	public void onDestroy()
	{
		toa("服务完全关闭");
		//Toast.makeText(getApplicationContext(),"服务已销毁",Toast.LENGTH_SHORT).show();
		// TODO: Implement this method
		super.onDestroy();
	}


	/**
     * 初始位置为屏幕的右下角位置。
     */
    public void createFloatWindow() {
        wmParams = new WindowManager.LayoutParams();
        WindowManager windowManager = getWindowManager(this);

		// 新建悬浮窗控件
        button_start = new Button(getApplicationContext());
        button_start.setText("开始取词");
        button_start.setBackgroundResource(R.drawable.shape);
button_start.setTextColor(Color.BLACK);
        if (Build.VERSION.SDK_INT >= 24) { /*android7.0不能用TYPE_TOAST*/
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        } else { /*以下代码块使得android6.0之后的用户不必再去手动开启悬浮窗权限*/
            String packgname = getPackageName();
            PackageManager pm = getPackageManager();
            boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.SYSTEM_ALERT_WINDOW", packgname));
            if (permission) {
                wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            } else {
                wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            }
        }

        //设置图片格式，效果为背景透明
        //wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.START | Gravity.TOP;

        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        int screenWidth = dm.widthPixels;
        //窗口高度
        int screenHeight = dm.heightPixels;
        //以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = screenWidth;
        wmParams.y = 500;

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		windowManager.addView(button_start, wmParams);
    }


	/**
     * 返回当前已创建的WindowManager。
     */
    private WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }


	private boolean checkBound(Rect var1, int var2, int var3) {
		//检测边界是否符合规范
		return var1.bottom >= 0 && var1.right >= 0 && var1.top <= var3 && var1.left <= var2;
	}

}


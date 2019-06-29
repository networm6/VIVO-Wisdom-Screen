package myAi.Knowanything.windows;

import android.app.Activity;
import android.os.Bundle;
import android.kz.BaseBean.Base;
import android.kz.Toast.Toastkeeper;
import android.content.Intent;
import android.kz.Permission.KzPermissions;
import android.view.View;
import java.util.ArrayList;
import android.kz.Image.MediaView;

public  class MainActivity extends Base 
{

	private ArrayList<String> allpath = new ArrayList<>();
	private MediaView mediaView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

		inlayout(R.layout.activity_main);
        super.onCreate(savedInstanceState);
		Bar(R.id.mainView1);
		//注意上面的顺序
		allpath.add("http://bmob-cdn-22178.b0.upaiyun.com/2019/02/11/2e600860409f7c9780c6722aa92da0b6.jpg");
		allpath.add("http://bmob-cdn-22178.b0.upaiyun.com/2019/05/12/bcca16234016436880af1e37ff131c1a.png");
		allpath.add("http://bmob-cdn-22178.b0.upaiyun.com/2019/05/12/46e01c5a40557cc28040c61bbea7a5ae.png");
		mediaView=findViewById(R.id.media_view);
		mediaView.setMedias(allpath);
		mediaView.setOnMediaClickListener(new MediaView.OnMediaClickListener() {
				@Override
				public void onMediaClick(View view, int index) {
					//toa("onClickIndex :" + index);
					Intent i =new Intent(MainActivity.this,s.class);
					i.putExtra("data",allpath.get(index));
					startActivity(i);
					}
			});

	}
	public void setting(View view) {
		toa("正在进入");
        KzPermissions.gotoPermissionSettings(MainActivity.this);
    }
	public void open (View v){
		try{
			//跳转系统自带界面 辅助功能界面
			Intent intent =new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
			startActivity(intent);
			toa("请打开");
		}catch(Exception e) {
			e.printStackTrace();
		}
		}
	void toa(String in){

		Toastkeeper.getInstance()
			.createBuilder(this)
			.setMessage(in)
			.setGravity(Toastkeeper.GRAVITY_TOP)
			.show();
	}
	}

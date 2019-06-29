package myAi.Knowanything.windows;
import android.app.Application;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoader;

public class App extends Application
{

	@Override
	public void onCreate()
	{
		// TODO: Implement this method
		super.onCreate();
		

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
            .threadPoolSize(30)//配置加载图片的线程数
            .threadPriority(1000)//配置线程的优先级
            .build();//完成

		ImageLoader.getInstance().init(config);
	
	}
	
}

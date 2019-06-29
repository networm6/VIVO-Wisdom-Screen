package myAi.Knowanything.windows;
import android.graphics.Rect;
import android.os.Parcelable;
import android.os.Parcel;

public class ContentNode implements Parcelable
{
	private Rect bound;
	private String content;

	public static Creator<ContentNode> CREATOR = new Creator<ContentNode>() {

		@Override
		public ContentNode createFromParcel(Parcel p1)
		{
			// TODO: Implement this method
			return new ContentNode(p1);
		}

		@Override
		public ContentNode[] newArray(int p1)
		{
			// TODO: Implement this method
			return new ContentNode[p1];
		}
    };


	public long caculateSize()
	{
		// TODO: Implement this method
		return this.bound.width()*this.bound.height();
	}

	@Override
	public int describeContents()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p1, int i1)
	{
		p1.writeInt(this.bound.left);
        p1.writeInt(this.bound.top);
        p1.writeInt(this.bound.right);
        p1.writeInt(this.bound.bottom);
        p1.writeString(this.content);
	}


	public ContentNode(Parcel p1){
		this.bound = new Rect(p1.readInt(),p1.readInt(),p1.readInt(),p1.readInt());
		this.content = p1.readString();
	}

	public ContentNode(Rect r1,String s1){
		this.bound = r1;
		this.content = s1;
	}

	public Rect getNodeRect(){
		return this.bound;
	}

	public String getContent(){
		return this.content;
	}

	@Override
    public String toString() {
        return "ContentNode{" +
			"bound=" + bound +
			", content='" + content + '\'' +
			'}';
    }
}


package liuliu.babyshow.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import java.util.List;

import liuliu.babyshow.R;
import liuliu.custom.method.volley.BitmapCache;
import liuliu.custom.method.volley.VolleyItem;

public class VolleyListAdapter extends BaseAdapter {
	private final String TAG = getClass().getSimpleName();

	private RequestQueue mQueue;
	private ImageLoader mImageLoader;

	private LayoutInflater mInflater;
	private List<VolleyItem> items;

	public VolleyListAdapter(Context context, RequestQueue queue, List<VolleyItem> itemList) {
		super();
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.items = itemList;

		mQueue = queue;
		// 初始化Volley图片Loader
		mImageLoader = new ImageLoader(mQueue, new BitmapCache());
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_volley_list, null);

			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.item_img);
			holder.name = (TextView) convertView.findViewById(R.id.item_txt);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		VolleyItem item = items.get(position);
		holder.name.setText(item.getName());

		// 利用Volley加载图片
		ImageListener listener = ImageLoader.getImageListener(holder.img, 0, R.mipmap.ic_launcher);
		mImageLoader.get(item.getImgUrl(), listener);

		return convertView;
	}

	private static class ViewHolder {
		ImageView img;
		TextView name;
	}

	/**
	 * 刷新列表
	 *
	 * @param newList
	 */
	public void notifyDataSetChanged(List<VolleyItem> newList) {
		this.items = newList;
		notifyDataSetChanged();
	}

}

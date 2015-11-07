package liuliu.babyshow.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import java.util.List;

import liuliu.babyshow.R;
import liuliu.babyshow.type.ArticleType;
import liuliu.custom.method.volley.BitmapCache;
import liuliu.babyshow.model.VolleyItem;

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
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        /*
        * 一张图片随意显示
		* 两张。一行显示两张（等比例缩小）
		* 三张同上
		* 四张，两行，一行两个
		* 五张，两行。上面三张下面两张
		* */
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_volley_list, null);

            holder = new ViewHolder();
            holder.title_msg = (LinearLayout) convertView.findViewById(R.id.title_msg_ll_item);
            holder.normal_msg= (LinearLayout) convertView.findViewById(R.id.normal_msg_ll_item);
            holder.img = (ImageView) convertView.findViewById(R.id.title_item_img);
            holder.name = (TextView) convertView.findViewById(R.id.title_item_txt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VolleyItem item = items.get(position);
        //置顶跟活动
        if (item.getArticleType() == ArticleType.STICK || item.getArticleType() == ArticleType.ACTIVITY) {
            holder.title_msg.setVisibility(View.VISIBLE);

        } else {//一般
            holder.title_msg.setVisibility(View.GONE);
        }
        holder.name.setText(item.getUser().getNickname());

        // 利用Volley加载图片
        ImageListener listener = ImageLoader.getImageListener(holder.img, 0, R.mipmap.ic_launcher);
        mImageLoader.get(item.getArticleImageList().get(0).getImgUrl(), listener);
        return convertView;
    }

    private static class ViewHolder {
        LinearLayout title_msg;//置顶消息处理
        LinearLayout normal_msg;//一般消息处理
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

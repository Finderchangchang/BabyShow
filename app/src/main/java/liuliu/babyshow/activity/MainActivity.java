package liuliu.babyshow.activity;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.CodeNote;

import java.util.ArrayList;
import java.util.List;

import liuliu.babyshow.R;
import liuliu.babyshow.adapter.RefreshListView;
import liuliu.babyshow.base.BaseActivity;
import liuliu.babyshow.control.main.IMainView;
import liuliu.babyshow.control.main.MainListener;
import liuliu.babyshow.model.User;
import liuliu.custom.control.toolbar.TToolbar;
import liuliu.custom.method.Utils;
import liuliu.babyshow.model.VolleyItem;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainView, RefreshListView.IReflashListener {
    public static MainActivity mIntaile;
    FinalDb finalDb;
    RequestQueue mQueue;//初始化Volley
    @CodeNote(id = R.id.user_img_left_menu_main)
    ImageView user_img;//用户头像
    @CodeNote(id = R.id.user_name_tv_main_left_menu_main)
    TextView user_name_tv;//用户名
    @CodeNote(id = R.id.user_desc_tv_main_left_menu_main)
    TextView user_desc_tv;//用户描述
    @CodeNote(id = R.id.user_ll_left_nav_view_main, click = "onClick")
    LinearLayout user_title_ll_left;//左部菜单头点击
    @CodeNote(id = R.id.fab, click = "onClick")
    FloatingActionButton floatingActionButton;//浮动Button
    @CodeNote(id = R.id.drawer_layout)
    DrawerLayout mDrawer;//左部菜单
    @CodeNote(id = R.id.toolbar_main)
    TToolbar mToolbar;//页面的toolbar
    @CodeNote(id = R.id.main_left_nav_view)
    NavigationView navigationView;//左部菜单
    RefreshListView mListView;//自定义ListView
    VolleyListAdapter mAdapter;//ListView的adapter
    MainListener mListener;
    //存储显示的数据
    List<VolleyItem> items;
    // 列表长度
    static final int LIST_SIZE = 20;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_main);
        mIntaile = this;
        finalDb = FinalDb.create(this);
        navigationView.setNavigationItemSelectedListener(this);
        mListener = new MainListener(this, finalDb);
        mListener.GetUserCache();//获得登录用户信息
    }

    /*click事件处理*/
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_ll_left_nav_view_main://点击左侧title跳转到个人中心
                break;
            case R.id.fab://浮动button点击事件

                break;
        }
    }

    @Override
    public void initEvents() {
        // TODO 初始化VolleyRequestQueue对象,这个对象是Volley访问网络的直接入口
        mQueue = Volley.newRequestQueue(this);
        initList(null);
        mToolbar.setCenterTitle("首页");
        mToolbar.setLeftOnClick(new TToolbar.LeftOnClickListener() {
            @Override
            public void leftclick() {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });
    }

    /*初始化ListView*/
    private void initList(List<VolleyItem> apk_list) {
        if (mAdapter == null) {
            mListView = (RefreshListView) findViewById(R.id.volley_listview);
            mListView.setiReflashListener(this);//设置刷新数据接口
            items = new ArrayList<VolleyItem>();
            String imgUrl = "http://pic24.nipic.com/20120920/10361578_112230424175_2.jpg";
            for (int i = 1; i <= LIST_SIZE; i++) {
                VolleyItem item = new VolleyItem();
                item.setName("我所看到的香港-" + i);
                // TODO 为图片地址添加一个尾数,是为了多次访问图片,而不是读取第一张图片的缓存
                item.setImgUrl(imgUrl + "?rank=" + i);
                items.add(item);
            }
            mAdapter = new VolleyListAdapter(this, mQueue, items);// TODO 绑定数据
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged(apk_list);
        }
    }

    /*刷新ListView数据*/
    private void setRefreshData() {
        String imgUrl = "http://img0.imgtn.bdimg.com/it/u=4028229798,320603902&fm=21&gp=0.jpg";
        for (int i = 1; i <= 2; i++) {
            VolleyItem item = new VolleyItem();
            item.setName("顶部刷新数据" + i);
            item.setImgUrl(imgUrl + "?rank=" + i);
            items.add(0, item);
        }
    }

    /*刷新ListView处理*/
    @Override
    public void onReflash() {
        //获取最新数据
        setRefreshData();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //通知ui更新
                initList(items);
                //通知listview刷新数据
                mListView.reflashComplete();
            }
        }, 2000);
    }

    /*获得用户信息*/
    @Override
    public void OnLoadUser(User user) {
        if (user != null) {
            Utils.setText(user_name_tv, user.getNickname(), "未知");
            Utils.setText(user_desc_tv, user.getContent(), "这个人很懒什么都没留下...");
            if (user.getHeadimg_big() != null) {
                user_img.setImageBitmap(user.getHeadimg_big());
                mToolbar.setLeftUserImg(user.getHeadimg_big());
            } else {
                user_img.setImageBitmap(user.getHeadimg_small());
                mToolbar.setLeftUserImg(user.getHeadimg_small());
            }
        }
    }

    /*点击返回按钮关闭左侧菜单*/
    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*左侧菜单各个item的点击事件*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camara) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*页面销毁*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQueue.cancelAll(this);// TODO 取消所有未执行完的网络请求
    }
}

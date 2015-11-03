package liuliu.babyshow.activity;

import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import net.tsz.afinal.annotation.view.CodeNote;
import net.tsz.afinal.cache.ACache;

import java.util.ArrayList;
import java.util.List;

import liuliu.babyshow.R;
import liuliu.babyshow.adapter.RefreshListView;
import liuliu.babyshow.base.BaseActivity;
import liuliu.babyshow.control.main.IMainView;
import liuliu.babyshow.model.User;
import liuliu.custom.method.volley.VolleyItem;
import liuliu.custom.model.Cache;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainView {
    @CodeNote(id = R.id.user_img_left_menu_main)
    ImageView user_img;
    @CodeNote(id = R.id.user_name_tv_main_left_menu_main)
    TextView user_name_tv;
    @CodeNote(id = R.id.user_desc_tv_main_left_menu_main)
    TextView user_desc_tv;
    @CodeNote(id = R.id.user_ll_left_nav_view_main, click = "onClick")
    LinearLayout user_title_ll_left;//左部菜单头点击
    @CodeNote(id = R.id.fab, click = "onClick")
    FloatingActionButton floatingActionButton;
    @CodeNote(id = R.id.drawer_layout)
    DrawerLayout mDrawer;
    @CodeNote(id = R.id.toolbar)
    Toolbar mToolbar;
    @CodeNote(id = R.id.main_left_nav_view)
    NavigationView navigationView;
    ACache mCache;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_main);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        mCache = ACache.get(this);
    }

    /*click事件处理*/
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_ll_left_nav_view_main://点击左侧title跳转到个人中心
                break;
            case R.id.fab://浮动button点击事件
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();//底部消息提示栏
                mAdapter.notifyDataSetChanged(newList);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    // 列表长度
    static final int LIST_SIZE = 2000;

    private RefreshListView mListView;
    private VolleyListAdapter mAdapter;
    private RequestQueue mQueue;
    List<VolleyItem> newList;

    @Override
    public void initEvents() {
        Bitmap big_img = mCache.getAsBitmap(Cache.CACHE_USER_BIG_IMG);
        Bitmap small_img = mCache.getAsBitmap(Cache.CACHE_USER_SMALL_IMG);
        if (big_img != null) {
            user_img.setImageBitmap(big_img);
        } else if (small_img != null) {
            user_img.setImageBitmap(small_img);
        } else {
            user_img.setImageResource(R.mipmap.ic_launcher);
        }
        User user = finalDb.findAll(new User().getClass()).get(0);
        if (user != null) {
            if (user.getNickname() != null) {
                user_name_tv.setText(user.getNickname());
            }
            user_desc_tv.setText(user.getContent());
        }
        // TODO 初始化VolleyRequestQueue对象,这个对象是Volley访问网络的直接入口
        mQueue = Volley.newRequestQueue(this);

        initList();
    }

    /**
     * 初始化List
     */
    private void initList() {
        mListView = (RefreshListView) findViewById(R.id.volley_listview);

        // TODO 初始化数据
        ArrayList<VolleyItem> items = new ArrayList<VolleyItem>(LIST_SIZE);
        String imgUrls = "http://img0.bdstatic.com/img/image/%E6%9C%AA%E6%A0%87%E9%A2%98-1.jpg";
        String imgUrl = "http://pic24.nipic.com/20120920/10361578_112230424175_2.jpg";
        for (int i = 1; i <= LIST_SIZE; i++) {
            VolleyItem item = new VolleyItem();
            item.setName("我所看到的香港-" + i);
            // TODO 为图片地址添加一个尾数,是为了多次访问图片,而不是读取第一张图片的缓存
            item.setImgUrl(imgUrl + "?rank=" + i);
            items.add(item);
        }
        newList = new ArrayList<VolleyItem>();
        for (int i = 0; i < 5; i++) {
            VolleyItem item = new VolleyItem();
            item.setName("添加的内容");
            item.setImgUrl(imgUrls);
            newList.add(item);
        }
        // TODO 绑定数据
        mAdapter = new VolleyListAdapter(this, mQueue, items);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO 取消所有未执行完的网络请求
        mQueue.cancelAll(this);
    }

    /*点击返回按钮关闭左侧菜单*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*设置按钮点击显示的内容*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*设置弹出的具体点击内容*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

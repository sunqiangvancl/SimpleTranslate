package xyz.mrseng.fasttranslate.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.global.Canstant;
import xyz.mrseng.fasttranslate.service.TransService;
import xyz.mrseng.fasttranslate.ui.base.BaseAppCompatActivity;
import xyz.mrseng.fasttranslate.ui.holder.HomeBottomHolder;
import xyz.mrseng.fasttranslate.ui.holder.InputHolder;
import xyz.mrseng.fasttranslate.ui.holder.LangHolder;
import xyz.mrseng.fasttranslate.ui.holder.LeftCardHolder;
import xyz.mrseng.fasttranslate.utils.ActivityUtils;

/**
 * Created by MrSeng on 2016/12/12.
 * 主页面Activity
 */
public class HomeActivity extends BaseAppCompatActivity {

    private InputHolder mInputHolder;
    private LangHolder mLangHolder;
    private FrameLayout mFl_bottom;
    private FrameLayout mFl_top;
    private FrameLayout mFl_center;
    private HomeBottomHolder mBottomHolder;
    private DrawerLayout mDl;
    private ActionBarDrawerToggle mToggle;
    private TransService transService = TransService.getNewInstance();

    //当前翻译的配置信息
    private TransBean mTransInfo = new TransBean();

    public TransBean getTransInfo() {
        return mTransInfo;
    }

    /** 每当数据改变，就更新翻译 */
    public void setTransInfo(TransBean transInfo) {
        if (transInfo.token == null) {
            transInfo.token = Canstant.TOKEN_NET;
        }
        this.mTransInfo = transInfo;
    }

    /** 提醒做翻译更新 */
    public void notifyTransInfoChanged() {
        transService.doTranslate(mTransInfo);
    }

    /** 外部获取翻译的service */
    public TransService getTransService() {
        return transService;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = View.inflate(this, R.layout.activity_home, null);
        //主内容fragment
        setContentView(rootView);
        initWidget(rootView);
        initData();
        //侧边栏
        initLeftMenu();
        initActionBar();
    }

    private void initLeftMenu() {
        FrameLayout mFl_menu = (FrameLayout) findViewById(R.id.fl_left_menu);
        LeftCardHolder leftHolder = new LeftCardHolder(this);
        mFl_menu.removeAllViews();
        mFl_menu.addView(leftHolder.getRootView());
    }

    private void initWidget(View view) {
        mDl = (DrawerLayout) view.findViewById(R.id.dl_home_left);

        //top bar
        mFl_top = (FrameLayout) view.findViewById(R.id.fl_top_home);
        mLangHolder = new LangHolder(this);
        mFl_top.addView(mLangHolder.getRootView());

        //center
        mFl_center = (FrameLayout) view.findViewById(R.id.fl_center_home);
        mInputHolder = new InputHolder(this);
        mFl_center.addView(mInputHolder.getRootView());
        mInputHolder.getEt_Input().setOnClickListener(new View.OnClickListener() {//当用户尝试输入文本，应该隐藏历史记录面板
            @Override
            public void onClick(View v) {
                //TODO 当用户打开输入法，没有输入内容，由关闭了输入法，此时应该显示历史记录，而当前无法显示
                mBottomHolder.setData(HomeBottomHolder.SHOW_NONE);
                getTransInfo().token = Canstant.TOKEN_NET;
            }
        });

        //bottom
        mFl_bottom = (FrameLayout) view.findViewById(R.id.fl_bottom_home);
        mBottomHolder = new HomeBottomHolder(this);
        mFl_bottom.addView(mBottomHolder.getRootView());
    }

    /** 有肯能是从好词好句Activity条转过来的 */
    private void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TransBean marked = (TransBean) extras.getSerializable(Canstant.EXTRA_MARKED);
            if (marked != null) {
                getTransInfo().marked = marked.marked;
                getTransInfo().fromCode = marked.fromCode;
                getTransInfo().fromWord = marked.fromWord;
                getTransInfo().toCode = marked.toCode;
                getTransInfo().toWord = marked.toWord;
                //提示这个翻译是来自mark页面的
                getTransInfo().token = Canstant.TOKEN_LOCAL;
                notifyTransInfoChanged();
            }
        }
    }

    @Override
    public void finish() {
        if (!TextUtils.isEmpty(getTransInfo().fromWord)) {
            getTransInfo().fromWord = "";
            notifyTransInfoChanged();
            return;
        }
        //侧边栏没关闭应该先关闭侧边栏
        if (mDl.isDrawerOpen(Gravity.LEFT)) {
            closeDrawer();
        } else {
            super.finish();
            ActivityUtils.getNewInstance().exit();
        }
    }

    //actionbar和侧边栏
    private void initActionBar() {
        //这三行代码可以是左侧边栏显示成菜单图标
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        mToggle = new ActionBarDrawerToggle(this, mDl, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /** 关闭抽屉 */
    public void closeDrawer() {
        mDl.closeDrawer(Gravity.LEFT);
    }


    @Override
    public void onResume() {
        super.onResume();
        mLangHolder.onActivityResume();
        mInputHolder.onActivityResume();
//        mBottomHolder.onActivityResume();
    }


}

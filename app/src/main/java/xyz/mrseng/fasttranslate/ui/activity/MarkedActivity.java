package xyz.mrseng.fasttranslate.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.global.Canstant;
import xyz.mrseng.fasttranslate.service.MarkService;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.ui.base.BaseMenuActivity;
import xyz.mrseng.fasttranslate.ui.base.MyBaseAdapter;
import xyz.mrseng.fasttranslate.utils.SPUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

public class MarkedActivity extends BaseMenuActivity {

    private LinearLayout mLl_noMark;
    private ListView mLv_marked;
    private MarkService markService = new MarkService();
    private ArrayList<TransBean> markedList;
    private boolean selectMode = false;//标记当前是否处于选择模式，便于处理点击事件


    @Override
    protected View getView() {
        View view = UIUtils.inflate(R.layout.activity_marked);
        //查找好词好句集合
        mLl_noMark = (LinearLayout) view.findViewById(R.id.ll_no_mark);
        mLv_marked = (ListView) view.findViewById(R.id.lv_mark);
        mLv_marked.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        mLv_marked.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!selectMode) {
                    Intent intent = new Intent(MarkedActivity.this, HomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Canstant.EXTRA_MARKED, markedList.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    // TODO: 2016/12/20 listview 多选，删除
                    mLv_marked.setItemChecked(position, mLv_marked.isItemChecked(position));
                }
            }
        });
        mLv_marked.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }

    private void initData() {
        boolean sortbytime = SPUtils.getBoolean(SPUtils.KEY_SORT_BY_TIME, true);
        markedList = sortbytime ? markService.getMarked(0) : markService.getMarkedSortedByABC(0);
        if (markedList == null && markedList.size() == 0) {
            mLl_noMark.setVisibility(View.VISIBLE);
            mLv_marked.setVisibility(View.GONE);
        } else {
            mLl_noMark.setVisibility(View.GONE);
            mLv_marked.setVisibility(View.VISIBLE);
            if (mLv_marked.getAdapter() == null) {
                mLv_marked.setAdapter(new MyBaseAdapter<TransBean>(markedList) {
                    @Override
                    public BaseHolder<TransBean> getHolder() {
                        return new BaseHolder<TransBean>(MarkedActivity.this) {

                            private TextView tv_tw;
                            private TextView tv_fw;

                            @Override
                            public View initView() {
                                View view = UIUtils.inflate(R.layout.item_list_marked);
                                tv_fw = (TextView) view.findViewById(R.id.tv_from_word_mark);
                                tv_tw = (TextView) view.findViewById(R.id.tv_to_word_mark);
                                return view;
                            }

                            @Override
                            public void onRefresh(TransBean data) {
                                tv_fw.setText(data.fromWord);
                                tv_tw.setText(data.toWord);
                            }
                        };
                    }
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if (position == getCount()-1){
                            loadMore();
                        }
                        return super.getView(position, convertView, parent);
                    }

                    //加载更多
                    private void loadMore() {
                        if (markedList!=null){
                            if (markedList.size() % Canstant.LIMIT_PAGE_SIZE == 0){
                                int page = markedList.size()  / Canstant.LIMIT_PAGE_SIZE;
                                ArrayList<TransBean> newData = markService.getMarked(page);
                                if (newData!=null && newData.size()>0){
                                    markedList.addAll(newData);
                                    this.notifyDataSetChanged(markedList);
                                    mLv_marked.setSelection(markedList.size());
                                    //// TODO: 2016/12/20 更新完数据需要滑动至最底部
                                }
                            }
                        }
                    }

                });
            } else {
                ((MyBaseAdapter) mLv_marked.getAdapter()).notifyDataSetChanged(markedList);
            }
        }
    }

    @Override
    protected String getTitleStr() {
        return UIUtils.getString(R.string.menu_mark);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO: 2016/12/20 ActionBar右侧图标应该更小一点
        getMenuInflater().inflate(R.menu.menu_mark, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort://排序
                showSelectSortDialog();
                break;
            case R.id.menu_more://选择
                selectMode = true;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSelectSortDialog() {
        View root = View.inflate(this, R.layout.dialog_sort_mark, null);
        RadioGroup rg = (RadioGroup) root.findViewById(R.id.rg_sort);
        rg.check(SPUtils.getBoolean(SPUtils.KEY_SORT_BY_TIME, true) ? R.id.rb_sort_time : R.id.rb_sort_abc);
        final AlertDialog dialog = new AlertDialog.Builder(this).setTitle(R.string.sort).setView(root).create();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SPUtils.setBoolean(SPUtils.KEY_SORT_BY_TIME, checkedId == R.id.rb_sort_time);
                initData();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

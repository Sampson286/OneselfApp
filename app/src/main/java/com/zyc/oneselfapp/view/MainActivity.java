package com.zyc.oneselfapp.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zyc.oneselfapp.R;
import com.zyc.oneselfapp.utilities.AppConstant;
import com.zyc.oneselfapp.utilities.CommUtils;
import com.zyc.oneselfapp.utilities.FileUtils;
import com.zyc.oneselfapp.view.adapter.MainPopupRecyclerAdapter;
import com.zyc.oneselfapp.view.adapter.MainRecyclerViewAdapter;
import com.zyc.oneselfapp.viewmodel.CommPackageInfo;
import com.zyc.oneselfapp.viewmodel.DataControl;
import com.zyc.oneselfapp.viewmodel.GoodsList;
import com.zyc.oneselfapp.viewmodel.ProgressSubscriber;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

public class MainActivity extends BaseActivity {
    private String TAG = "MainActivity";
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.main_swiprefreshlayout)
    SwipeRefreshLayout main_swiprefreshlayout;
    @BindView(R.id.main_activity_top_ll_left)
    LinearLayout main_activity_top_ll_left;
    @BindView(R.id.main_activity_top_ll_left_text)
    TextView main_activity_top_ll_left_text;
    @BindView(R.id.main_activity_top_ll_left_img)
    ImageView main_activity_top_ll_left_img;
    @BindView(R.id.main_activity_top_ll_right)
    LinearLayout main_activity_top_ll_rghit;
    @BindView(R.id.main_activity_top_ll_right_text)
    TextView main_activity_top_ll_right_text;
    @BindView(R.id.main_activity_top_ll_right_img)
    ImageView main_activity_top_ll_right_img;
    @BindView(R.id.main_activity_top_ll)
    LinearLayout main_activity_top_ll;
    RecyclerView main_activity_popupwindow_rv;
    private String img_url;
    private String info_url;
    private int startIndex;
    private DataControl dataControl = DataControl.getInstance();
    private GoodsList goodsList;
    private List<String> propsList;
    private int index = -1;
    MainRecyclerViewAdapter adapter;
    MainPopupRecyclerAdapter mainPopupRecyclerAdapter;
    PopupWindow popupWindow;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1;
    String root= FileUtils.getRootFile();
    File rootFile=new File(root);
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialized() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if(!rootFile.exists()){
                rootFile.mkdirs();
            }
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        File goodsFile=new File(AppConstant.goodsListPath);
        if(!goodsFile.exists()){
            initData();
        }else{
            goodsList = dataControl.getGoodList();
            initView();
        }

    }

    /**
     * 初始化数据
     */
    private void initData() {
        //暂时处理到时候修改为根据时间进行更新
        dataControl.getCommPackageInfo("commpackage_info.js").flatMap(new Func1<CommPackageInfo, Observable<ResponseBody>>() {
            @Override
            public Observable<ResponseBody> call(CommPackageInfo commPackageInfo) {
                img_url = commPackageInfo.getImg().getImg_url();
                startIndex = img_url.lastIndexOf("/");
                info_url = commPackageInfo.getInfo().getJson_url();
                startIndex = info_url.lastIndexOf("/");
                return dataControl.downloadFile(img_url.substring(startIndex + 1));
            }
        }).flatMap(new Func1<ResponseBody, Observable<ResponseBody>>() {
            @Override
            public Observable<ResponseBody> call(ResponseBody responseBody) {
                InputStream inputStream = responseBody.byteStream();
                String file = FileUtils.getRootFile() + "/hero/";
                File file1 = new File(file);
                if (!file1.exists()) {
                    file1.mkdir();
                }
                FileUtils.writeToDiskAndUnzip(inputStream, file + img_url.substring(startIndex + 1));
                return dataControl.downloadFile(info_url.substring(startIndex + 1));
            }
        }).subscribe(new ProgressSubscriber<ResponseBody>(this, true){

            @Override
            public void onNext(ResponseBody responseBody) {
                InputStream inputStream = responseBody.byteStream();
                String file = FileUtils.getRootFile() + "/hero/";
                File file1 = new File(file);
                if (!file1.exists()) {
                    file1.mkdir();
                }
                FileUtils.writeToDiskAndUnzip(inputStream, file + info_url.substring(startIndex + 1));
                goodsList = dataControl.getGoodList();
                initView();
            }
        });

    }

    private void initView() {
        View popupWindowContentView = CommUtils.getViewById(R.layout.main_activity_popupwindow_ll, null, false);
        main_activity_popupwindow_rv = (RecyclerView) popupWindowContentView.findViewById(R.id.main_activity_popupwindow_rv);
        popupWindow = new PopupWindow();
        popupWindow.setContentView(popupWindowContentView);
        popupWindow.setAnimationStyle(R.style.MainPopupWindowEnter);
        
        popupWindowContentView.findViewById(R.id.main_activity_shadow).setOnClickListener(v -> {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        });
        //设置popupWindow的宽高
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        propsList = goodsList.getProps();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        main_swiprefreshlayout.setColorSchemeResources(R.color.green);
        adapter = new MainRecyclerViewAdapter(goodsList);
        adapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(MainActivity.this, GoodDetailActivity.class);
            intent.putExtra("goodid", goodsList.getItems().get(position).getGood_id() + "");
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        main_swiprefreshlayout.setOnRefreshListener(() -> {
            if (index < propsList.size() - 1) {
                index++;
            } else {
                index = 0;
            }
            goodsList = dataControl.getGoodsListByProp(dataControl.getGoodList(), propsList.get(index));
            adapter.setGoodsList(goodsList);
            adapter.notifyDataSetChanged();
            main_swiprefreshlayout.setRefreshing(false);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @OnClick({R.id.main_activity_top_ll_left, R.id.main_activity_top_ll_right})
    void Onclick(View view) {
        switch (view.getId()) {
            case R.id.main_activity_top_ll_left:
                if (!popupWindow.isShowing()) {
                    if (goodsList.getMaps() != null && goodsList.getMaps().size() > 0) {
                        if (!goodsList.getMaps().get(0).equals(CommUtils.getResourceById(R.string.goodsortbymap))) {
                            goodsList.getMaps().add(0, CommUtils.getResourceById(R.string.goodsortbymap));
                        }
                    }
                    if (mainPopupRecyclerAdapter == null) {
                        mainPopupRecyclerAdapter = new MainPopupRecyclerAdapter(goodsList.getMaps());
                    } else {
                        mainPopupRecyclerAdapter.setList(goodsList.getMaps());
                    }
                } else {
                    mainPopupRecyclerAdapter.setList(goodsList.getMaps());
                }
                if (main_activity_popupwindow_rv != null) {
                    main_activity_popupwindow_rv.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                    main_activity_popupwindow_rv.setAdapter(mainPopupRecyclerAdapter);
                }
                break;
            case R.id.main_activity_top_ll_right:
                if (!popupWindow.isShowing()) {
                    if (goodsList.getProps() != null && goodsList.getProps().size() > 0) {
                        if (!goodsList.getProps().get(0).equals(CommUtils.getResourceById(R.string.goodsortbyprop))) {
                            goodsList.getProps().add(0, CommUtils.getResourceById(R.string.goodsortbyprop));
                        }
                    }
                    if (mainPopupRecyclerAdapter == null) {
                        mainPopupRecyclerAdapter = new MainPopupRecyclerAdapter(goodsList.getProps());
                    } else {
                        mainPopupRecyclerAdapter.setList(goodsList.getProps());
                    }
                } else {
                    mainPopupRecyclerAdapter.setList(goodsList.getProps());
                }
                if (main_activity_popupwindow_rv != null) {
                    main_activity_popupwindow_rv.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                    main_activity_popupwindow_rv.setAdapter(mainPopupRecyclerAdapter);
                }
                break;
        }
        mainPopupRecyclerAdapter.notifyDataSetChanged();
        popupWindow.showAsDropDown(main_activity_top_ll, 0, 0);
        mainPopupRecyclerAdapter.setOnItemClickListener((view1, position) -> {
            if (position == 0) {
                goodsList = dataControl.getGoodList();
            } else if (mainPopupRecyclerAdapter.getList().size() == goodsList.getMaps().size()) {
                goodsList = dataControl.getGoodsListByMap(dataControl.getGoodList(), mainPopupRecyclerAdapter.getList().get(position));
            } else {
                goodsList = dataControl.getGoodsListByProp(dataControl.getGoodList(), mainPopupRecyclerAdapter.getList().get(position));
            }
            mainPopupRecyclerAdapter.setSelectIndex(position);
            view1.setSelected(true);
            main_activity_top_ll_left_text.setText(mainPopupRecyclerAdapter.getList().get(position));
            adapter.setGoodsList(goodsList);
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, mainPopupRecyclerAdapter.getList().get(position), Toast.LENGTH_LONG).show();
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(!rootFile.exists()){
                        rootFile.mkdirs();
                    }
                }else{
                    Toast.makeText(this,"SD卡权限没有被授予",Toast.LENGTH_SHORT).show();
                }
            }
            return;
        }

    }
}

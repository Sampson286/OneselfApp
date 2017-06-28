package com.zyc.oneselfapp.view;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyc.oneselfapp.R;
import com.zyc.oneselfapp.utilities.AppConstant;
import com.zyc.oneselfapp.utilities.CommUtils;
import com.zyc.oneselfapp.viewmodel.DataControl;
import com.zyc.oneselfapp.viewmodel.Good;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class GoodDetailActivity extends BaseActivity {
    /*@BindView(R.id.fab)
    FloatingActionButton floatingActionButton;*/
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.gooddetaile_text_content)
    TextView gooddetaile_text_content;
    @BindView(R.id.gooddetaile_advance_ll)
    LinearLayout gooddetaile_advance_ll;
    private DataControl dataControl=DataControl.getInstance();
    private Good detaileGood;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_good_detail;
    }
    @Override
    protected void initialized() {
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        String id=intent.getStringExtra("goodid");
        if(!TextUtils.isEmpty(id)){
            detaileGood=dataControl.getGoodById(id);
        }
        setTitle(detaileGood.getName());
        gooddetaile_text_content.setText(detaileGood.getGood_desc());
        List<String> canproducelist= detaileGood.getCanproducelist();
        for (int i=0;i<canproducelist.size();i++){
            ImageView imageView=new ImageView(this);
            imageView.setPadding(0,CommUtils.dip2Pixel(5),CommUtils.dip2Pixel(5),0);
            imageView.setImageBitmap(BitmapFactory.decodeFile(AppConstant.goodImgPath+ File.separator+canproducelist.get(i)+".png"));
            gooddetaile_advance_ll.addView(imageView);
            int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(GoodDetailActivity.this,GoodDetailActivity.class);
                    intent.putExtra("goodid",canproducelist.get(finalI));
                    startActivity(intent);
                }
            });
        }
    }
    /*@OnClick({R.id.fab})
    void Onclick(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }*/
}

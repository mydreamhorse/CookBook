package com.redstar.cookbook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.redstar.cookbook.cookbook.bean.CookBook;
import com.redstar.cookbook.cookbook.bean.Step;
import com.redstar.cookbook.util.DensityUtil;
import com.squareup.picasso.Picasso;
import com.wefika.flowlayout.FlowLayout;

import java.util.List;

/**
 * Created by geek99.com on 2016/8/3.
 */
public class DetailActivity extends AppCompatActivity {

    ImageView coverImageView;
    TextView titleTextView;
    TextView introTextView;
    FlowLayout tagFlowLayout;

    // 食材和调料
    String[] mainList,tiaoliaoList;

    List<Step> stepList;

    ListView mainshicaiListView;
    ListView tiaoliaoListView;
    ListView stepListView;

    CookBook cookBook;

    int step=0;

    // 工具栏
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        cookBook = (CookBook) intent.getSerializableExtra("cookBook");
        mainList = cookBook.getIngredients().split(";");
        tiaoliaoList = cookBook.getBurden().split(";");
        stepList = cookBook.getSteps();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.finish();
            }
        });

        Log.i("test", "size:" + mainList.length);

        Log.i("test", "step size:" + stepList.size());

        initviews();
    }

    private void initviews() {

        // 初始化封面
        coverImageView = (ImageView) findViewById(R.id.coverImageView);
        String url = cookBook.getAlbums().get(0);
        Picasso.with(this).load(url).into(coverImageView);

        // 标题
        titleTextView = (TextView)findViewById(R.id.titleTextView);
        titleTextView.setText(cookBook.getTitle());

        // 简介
        introTextView = (TextView) findViewById(R.id.introTextView);
        introTextView.setText(cookBook.getIntro());

        // 标签
        tagFlowLayout = (FlowLayout) findViewById(R.id.tagFlowLayout);
        String tags = cookBook.getTags();
        String[] tagArray = tags.split(";");

        // 流动布局
        FlowLayout.LayoutParams mLayoutParams = new FlowLayout.LayoutParams(
                FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);

        mLayoutParams.leftMargin = DensityUtil.dip2px(this,2);
        mLayoutParams.rightMargin = DensityUtil.dip2px(this,2);
        mLayoutParams.bottomMargin = DensityUtil.dip2px(this,4);

        for (String tag : tagArray) {
            TextView tv = new TextView(this);
            tv.setText(tag);
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundResource(R.color.colorGray);
            tagFlowLayout.addView(tv,mLayoutParams);
        }

        // 初始化主食材ListView
        mainshicaiListView = (ListView) findViewById(R.id.mainshicaiListView);
        mainshicaiListView.setAdapter(new MainshicaiListViewAdapter());
        // 初始化调料ListView
        tiaoliaoListView = (ListView)findViewById(R.id.tiaoliaoListView);
        tiaoliaoListView.setAdapter(new TiaoliaoListViewAdapter());

        // step
        stepListView = (ListView) findViewById(R.id.stepListView);

        StepListViewAdapter stepListViewAdapter = new StepListViewAdapter();
        stepListView.setAdapter(stepListViewAdapter);

        int count = stepListViewAdapter.getCount();

        stepListView.getLayoutParams().height = DensityUtil.dip2px(this, 280)*count;

    }

    // 步骤适配器
    class StepListViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if (stepList == null) {
                return 0;
            }
            return stepList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View myView;
            if (view == null) {
                myView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_item_step, null);
            }else{
                myView = view;
            }

            ImageView iv = (ImageView) myView.findViewById(R.id.imageview);
            TextView tv1 = (TextView) myView.findViewById(R.id.textview1);
            TextView tv2 = (TextView) myView.findViewById(R.id.textview2);

            Step step = stepList.get(i);

            String url = step.getImg();
            String desc = step.getStep();

            Picasso.with(getApplicationContext()).load(url).into(iv);

            tv1.setText(""+(i+1));
            tv2.setText(desc);

            return myView;
        }
    }


    // 调料适配器
    class TiaoliaoListViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return tiaoliaoList.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View myView ;
            if (view == null) {
                myView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_item_zhushicai, null);
            }else{
                myView = view;
            }

            TextView tv1 = (TextView) myView.findViewById(R.id.textview1);
            TextView tv2 = (TextView) myView.findViewById(R.id.textview2);

            String item = tiaoliaoList[i];

            String[] array = item.split(",");

            tv1.setText(array[0]);
            tv2.setText(array[1]);

            return myView;
        }
    }

    // 主食材适配器
    class MainshicaiListViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mainList.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View myView ;
            if (view == null) {
                myView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_item_zhushicai, null);
            }else{
                myView = view;
            }

            TextView tv1 = (TextView) myView.findViewById(R.id.textview1);
            TextView tv2 = (TextView) myView.findViewById(R.id.textview2);

            String item = mainList[i];

            String[] array = item.split(",");

            tv1.setText(array[0]);
            tv2.setText(array[1]);

            return myView;
        }
    }

}

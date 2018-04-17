package com.redstar.cookbook.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.redstar.cookbook.DetailActivity;
import com.redstar.cookbook.MyActivity;
import com.redstar.cookbook.R;
import com.redstar.cookbook.cookbook.CookBookPresenter;
import com.redstar.cookbook.cookbook.CookBookPresenterImpl;
import com.redstar.cookbook.cookbook.CookBookView;
import com.redstar.cookbook.cookbook.bean.CookBook;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements CookBookView {

    ConvenientBanner convenientBanner;

    CookBookPresenter cookBookPresenter;
    ListView listView;
    List<CookBook> cookBookList;
    MyAdpater myAdpater;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cookBookPresenter = new CookBookPresenterImpl(this);
        cookBookPresenter.getCookBookListByKey(getActivity(),"家常菜");
    }

    static List<Integer> localImages = new ArrayList<>();

    static {
        localImages.add(R.drawable.test_01);
        localImages.add(R.drawable.test02);
        localImages.add(R.drawable.test03);
        localImages.add(R.drawable.test04);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);

        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        return view;
    }

    class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    private void initViews(View view) {
        listView = (ListView)  view.findViewById(R.id.listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                CookBook cookBook = cookBookList.get(i);
                intent.putExtra("cookBook", cookBook);
                startActivity(intent);
            }
        });

        cookBookList = new ArrayList<>();
        myAdpater = new MyAdpater();
        listView.setAdapter(myAdpater);
    }

    class MyAdpater extends BaseAdapter {

        @Override
        public int getCount() {
            if (cookBookList != null) {
                return cookBookList.size();
            }else{
                return 0;
            }
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
                myView = getActivity().getLayoutInflater().inflate(R.layout.listview_item, null);
            }else{
                myView = view;
            }

            ImageView iv = (ImageView) myView.findViewById(R.id.imageview);
            TextView tv = (TextView) myView.findViewById(R.id.textview1);
            TextView tv2 = (TextView) myView.findViewById(R.id.textview2);

            CookBook cookBook = cookBookList.get(i);

            String url = cookBook.getAlbums().get(0);

            String title = cookBook.getTitle();

            String tag = cookBook.getTags();

            tv.setText(title);
            tv2.setText(tag);

            Picasso.with(getActivity()).load(url).resize(120,120).centerCrop().into(iv);

            return myView;
        }
    }

    @Override
    public void setCookBookList(List<CookBook> list) {
        this.cookBookList = list;
        Log.i("test","size:"+list.size());
        myAdpater.notifyDataSetChanged();
    }

    @Override
    public void setFail() {

    }
}

package com.redstar.cookbook.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.redstar.cookbook.DetailActivity;
import com.redstar.cookbook.R;
import com.redstar.cookbook.cookbook.CookBookPresenter;
import com.redstar.cookbook.cookbook.CookBookPresenterImpl;
import com.redstar.cookbook.cookbook.CookBookView;
import com.redstar.cookbook.cookbook.bean.CookBook;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by redstar.com on 2016/8/3.
 */
public class SearchFragment extends Fragment implements CookBookView {
    ProgressBar pb;
    CookBookPresenter cookBookPresenter;
    ListView listView;
    List<CookBook> cookBookList;
    MyAdpater myAdpater;
    String key;
    private static final String KEY = "key";

    public static SearchFragment newInstance(String key) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(KEY, key);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            key = getArguments().getString(KEY);
        }
        cookBookPresenter = new CookBookPresenterImpl(this);
        Log.i("test", "key:" + key);
        cookBookPresenter.getCookBookListByKey(getActivity(),key);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initViews(view);
        return view;
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

        pb = (ProgressBar)  view.findViewById(R.id.pb);

        pb.setVisibility(View.VISIBLE);

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
        pb.setVisibility(View.GONE);
    }

    @Override
    public void setFail() {

    }
}

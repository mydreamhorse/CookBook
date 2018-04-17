package com.redstar.cookbook.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.redstar.cookbook.DetailActivity;
import com.redstar.cookbook.MyActivity;
import com.redstar.cookbook.R;
import com.redstar.cookbook.category.CategoryPresenter;
import com.redstar.cookbook.category.CategoryPresenterImpl;
import com.redstar.cookbook.category.CategoryView;
import com.redstar.cookbook.category.bean.Category;
import com.redstar.cookbook.category.bean.Category1;
import com.redstar.cookbook.category.bean.Category2;
import com.redstar.cookbook.cookbook.CookBookPresenter;
import com.redstar.cookbook.cookbook.CookBookPresenterImpl;
import com.redstar.cookbook.cookbook.CookBookView;
import com.redstar.cookbook.cookbook.bean.CookBook;
import com.squareup.picasso.Picasso;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by redstar.com on 2016/8/3.
 */
public class CategoryFragment extends Fragment implements CategoryView,CookBookView {

    ProgressBar pb;
    CookBookPresenter cookBookPresenter;

    CategoryPresenter presenter;

    List<Category2> subCategoryList;
    Category category;
    LinearLayout container;

    ListView listView;
    List<CookBook> cookBookList;

    MyAdpater myAdpater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        container = (LinearLayout) view.findViewById(R.id.tree_container);

        listView = (ListView)view.findViewById(R.id.listview);

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

        presenter = new CategoryPresenterImpl(getActivity(),this);
        cookBookPresenter = new CookBookPresenterImpl(this);
        presenter.getCategory();

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
            } else {
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

    class MyHolder extends TreeNode.BaseNodeViewHolder<IconTreeItem> {

        public MyHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, IconTreeItem value) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.menu_item, null, false);

            TextView tv1 = (TextView) view.findViewById(R.id.textview);
            tv1.setText(value.text);

            if(value.isSub){
                view.setPadding(52,20,10,20);
                tv1.setTextSize(12);
            }

            ImageView iv = (ImageView) view.findViewById(R.id.imageview);
            iv.setImageResource(value.icon);
            return view;
        }
    }

    class IconTreeItem {
        public IconTreeItem(int icon, String text,boolean isSub) {
            this.icon = icon;
            this.text = text;
            this.isSub = isSub;
        }
        boolean isSub;
        public int icon;
        public String text;
    }

    void initTree() {

        TreeNode root = TreeNode.root();

        final  Category c = this.category;
        List<Category1> list1 = c.getResult();

        for (Category1 c1 : list1) {
            IconTreeItem parentItem = new IconTreeItem(R.drawable.root_icon,c1.getName(),false);
            TreeNode parent = new TreeNode(parentItem).setViewHolder(new MyHolder(getActivity()));

            List<Category2> list2 = c1.getList();

            for(final Category2 c2 : list2){
                IconTreeItem item1 = new IconTreeItem(R.drawable.sub_icon,c2.getName(),true);
                TreeNode child1 = new TreeNode(item1).setViewHolder(new MyHolder(getActivity()));
                child1.setClickListener(new TreeNode.TreeNodeClickListener() {
                    @Override
                    public void onClick(TreeNode node, Object value) {
                        cookBookPresenter.getCookBookListById(getActivity(),c2.getId());
                    }
                });
                parent.addChild(child1);
            }
            root.addChild(parent);
        }

        AndroidTreeView tView = new AndroidTreeView(getActivity(), root);
        container.addView(tView.getView());
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
        initTree();
        pb.setVisibility(View.GONE);
    }

    @Override
    public void setCookBookList(List<CookBook> list) {
        this.cookBookList = list;
        myAdpater.notifyDataSetChanged();
    }

    @Override
    public void setFail() {
        Toast.makeText(getActivity(), "error...", Toast.LENGTH_SHORT).show();
        //pb.setVisibility(View.GONE);
    }
}

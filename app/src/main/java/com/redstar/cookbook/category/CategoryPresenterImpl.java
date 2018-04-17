package com.redstar.cookbook.category;

import android.content.Context;

import com.redstar.cookbook.category.bean.Category;

/**
 * Created by geek99.com on 2016/7/31.
 */
public class CategoryPresenterImpl implements CategoryPresenter,CategoryDao.CategoryDaoListener {

    CategoryDao dao;
    CategoryView categoryView;

    Context context;
    public CategoryPresenterImpl(Context context, CategoryView categoryView) {
        this.context = context;
        this.categoryView = categoryView;
        dao = new CategoryDaoImpl();
    }

    @Override
    public void onSuccess(Category category) {
        categoryView.setCategory(category);
    }

    @Override
    public void onFail() {
        categoryView.setFail();
    }

    @Override
    public void getCategory() {
        dao.getCategory(context,this);
    }
}

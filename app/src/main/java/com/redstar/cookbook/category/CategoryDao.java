package com.redstar.cookbook.category;

import android.content.Context;

import com.redstar.cookbook.category.bean.Category;

/**
 * Created by geek99.com on 2016/7/31.
 */
public interface CategoryDao {
    public void getCategory(Context context, CategoryDaoListener listener);
    interface CategoryDaoListener {
        public void onSuccess(Category category);
        public void onFail();
    }
}

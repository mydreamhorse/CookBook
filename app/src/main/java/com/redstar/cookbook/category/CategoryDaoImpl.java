package com.redstar.cookbook.category;

import android.content.Context;

import com.android.volley.VolleyError;
import com.redstar.cookbook.category.bean.Category;
import com.redstar.cookbook.networking.MyVolley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by geek99.com on 2016/7/31.
 */


public class CategoryDaoImpl implements CategoryDao,MyVolley.CallBack {
    CategoryDaoListener listener;
    @Override
    public void getCategory(Context context,CategoryDaoListener listener) {
        this.listener = listener;
        MyVolley volley =  MyVolley.newMyVolley();
        String url="http://apis.juhe.cn/cook/category?parentid=&dtype=&key=c18acf5c7fd60c3c1974dae1e08cf5cc";
        volley.stringRequestGet(context,url,this);
    }

    @Override
    public void onStringSuccess(String response) {
        Gson gson = new Gson();
        Category c = gson.fromJson(response,
                new TypeToken<Category>() {}.getType());
        listener.onSuccess(c);
    }

    @Override
    public void onFailure(VolleyError error) {
        listener.onFail();
    }
}

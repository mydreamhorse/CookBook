package com.redstar.cookbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.redstar.cookbook.category.bean.Category;
import com.redstar.cookbook.category.bean.Category1;
import com.redstar.cookbook.category.bean.Category2;
import com.redstar.cookbook.cookbook.bean.CookBook;
import com.redstar.cookbook.cookbook.bean.TagCategoryList;
import com.redstar.cookbook.networking.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MyVolley.CallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view) {
        //String url="http://apis.juhe.cn/cook/category?parentid=&dtype=&key=c18acf5c7fd60c3c1974dae1e08cf5cc";
        //String url = "http://apis.juhe.cn/cook/index?cid=119&dtype=&pn=&rn=&format=&key=c18acf5c7fd60c3c1974dae1e08cf5cc";
        String url = "http://apis.juhe.cn/cook/queryid?id=100&dtype=&key=c18acf5c7fd60c3c1974dae1e08cf5cc";
        //String url = "http://apis.juhe.cn/cook/query.php?menu=%E9%B1%BC%E9%A6%99%E8%82%89%E4%B8%9D&dtype=&pn=&rn=&albums=&key=c18acf5c7fd60c3c1974dae1e08cf5cc";
        MyVolley myVolley = MyVolley.newMyVolley();
        myVolley.stringRequestGet(this, url, this);
    }

    @Override
    public void onStringSuccess(String response) {
        Log.i("test",response);

        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObject1 = jsonObject.getJSONObject("result");

            TagCategoryList tcl = gson.fromJson(jsonObject1.toString(),new TypeToken<TagCategoryList>() {
            }.getType());

            List<CookBook> list = tcl.getData();

            for(CookBook c1 :list) {
                Log.i("test",c1.getTitle());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*Gson gson = new Gson();
        Category c = gson.fromJson(response,new TypeToken<Category>() {
        }.getType());

        List<Category1> list1 = c.getResult();

        for(Category1 c1: list1) {
            Log.i("test1",c1.getName());
            List<Category2> list2 = c1.getList();
            for(Category2 c2 : list2) {
                Log.i("test", c2.getName());
            }
        }*/
    }

    @Override
    public void onFailure(VolleyError error) {
        Log.i("test", error.toString());
    }
}

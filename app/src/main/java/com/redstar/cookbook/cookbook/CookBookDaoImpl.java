package com.redstar.cookbook.cookbook;

import android.content.Context;

import com.android.volley.VolleyError;
import com.redstar.cookbook.cookbook.bean.CookBook;
import com.redstar.cookbook.cookbook.bean.TagCategoryList;
import com.redstar.cookbook.networking.MyVolley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by redstar.com on 2016/8/2.
 */
public class CookBookDaoImpl implements CookBookDao,MyVolley.CallBack {
    CookBookListener listener;
    @Override
    public void getCookBookListById(Context context,String id, CookBookListener listener) {
        this.listener = listener;
        MyVolley volley =  MyVolley.newMyVolley();
        String url = "http://apis.juhe.cn/cook/index?dtype=&pn=&rn=&format=&key=c18acf5c7fd60c3c1974dae1e08cf5cc&cid="+id;
        volley.stringRequestGet(context,url,this);
    }

    @Override
    public void getCookBookListByKey(Context context, String key, CookBookListener listener) {
        this.listener = listener;
        MyVolley volley =  MyVolley.newMyVolley();
        String url = "http://apis.juhe.cn/cook/query.php?&dtype=&pn=&rn=&albums=&key=c18acf5c7fd60c3c1974dae1e08cf5cc&menu="+key;
        volley.stringRequestGet(context,url,this);
    }

    @Override
    public void onStringSuccess(String response) {

        JSONObject jsonObject = null;
        String result = null;
        try {
            jsonObject = new JSONObject(response);
            result = jsonObject.getString("result");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        Gson gson = new Gson();
        TagCategoryList c = gson.fromJson(result,
                new TypeToken<TagCategoryList>() {}.getType());
        List<CookBook> list = c.getData();
        listener.onSuccess(list);
    }

    @Override
    public void onFailure(VolleyError error) {
        listener.onFail();
    }
}

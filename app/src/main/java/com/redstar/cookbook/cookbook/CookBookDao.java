package com.redstar.cookbook.cookbook;

import android.content.Context;

import com.redstar.cookbook.cookbook.bean.CookBook;

import java.util.List;

/**
 * Created by geek99.com on 2016/8/2.
 */
public interface CookBookDao {

    interface CookBookListener{
        public void onSuccess(List<CookBook> list);

        public void onFail();
    }

    public void getCookBookListById(Context context, String id, CookBookListener listener);
    public void getCookBookListByKey(Context context, String key, CookBookListener listener);

}

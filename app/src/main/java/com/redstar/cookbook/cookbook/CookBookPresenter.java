package com.redstar.cookbook.cookbook;

import android.content.Context;

/**
 * Created by geek99.com on 2016/8/2.
 */
public interface CookBookPresenter  {
    public void getCookBookListById(Context context, String id);
    public void getCookBookListByKey(Context context, String key);
}

package com.redstar.cookbook.cookbook;

import android.content.Context;

import com.redstar.cookbook.cookbook.bean.CookBook;

import java.util.List;

/**
 * Created by geek99.com on 2016/8/2.
 */
public class CookBookPresenterImpl implements CookBookPresenter,CookBookDao.CookBookListener {
    CookBookView cookBookView;
    CookBookDao dao;

    public CookBookPresenterImpl(CookBookView cookBookView) {
        this.cookBookView = cookBookView;
        dao = new CookBookDaoImpl();
    }
    @Override
    public void getCookBookListById(Context context, String id) {
        dao.getCookBookListById(context, id, this);
    }

    public void getCookBookListByKey(Context context, String key) {
        dao.getCookBookListByKey(context, key,this);
    }

    @Override
    public void onSuccess(List<CookBook> list) {
        cookBookView.setCookBookList(list);
    }

    @Override
    public void onFail() {
        cookBookView.setFail();
    }
}

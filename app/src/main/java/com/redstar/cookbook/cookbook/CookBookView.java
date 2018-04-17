package com.redstar.cookbook.cookbook;

import com.redstar.cookbook.cookbook.bean.CookBook;

import java.util.List;

/**
 * Created by geek99.com on 2016/8/2.
 */
public interface CookBookView {
    public void setCookBookList(List<CookBook> list);
    public void setFail();
}

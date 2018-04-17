package com.redstar.cookbook.cookbook.bean;

import java.util.List;

/**
 * Created by geek99.com on 2016/7/31.
 */
public class TagCategoryList {
    List<CookBook> data;

    public TagCategoryList() {

    }

    public TagCategoryList(List<CookBook> data) {
        this.data = data;
    }

    public List<CookBook> getData() {
        return data;
    }

    public void setData(List<CookBook> data) {
        this.data = data;
    }
}

package com.redstar.cookbook.category.bean;

import java.util.List;

/**
 * Created by geek99.com on 2016/7/31.
 */
public class Category {

    String resultCode;
    String reason;
    List<Category1> result;

    public Category() {
    }

    public Category(String resultCode, String reason, List<Category1> result) {
        this.resultCode = resultCode;
        this.reason = reason;
        this.result = result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Category1> getResult() {
        return result;
    }

    public void setResult(List<Category1> result) {
        this.result = result;
    }
}

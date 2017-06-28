package com.zyc.oneselfapp.viewmodel;

import java.util.List;

/**
 * Created by zyc on 2016/12/19.
 * 商品类型
 */

public class Good {
    private String greatguide;
    private String name;
    private int good_id;
    private String price;
    private String last_modify_date;
    private String saleprice;
    private String coprice;
    private boolean active;
    private String good_desc;
    private int id;
    private String insert_date;
    private List<String> canproducelist;
    private List<String> produceneedlist;
    private List<String> suithero;
    private List<String> othernames;
    private List<String> maplist;
    private List<String> proplist;

    public void setGreatguide(String greatguide) {
        this.greatguide = greatguide;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setLast_modify_date(String last_modify_date) {
        this.last_modify_date = last_modify_date;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    public void setCoprice(String coprice) {
        this.coprice = coprice;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setGood_desc(String good_desc) {
        this.good_desc = good_desc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInsert_date(String insert_date) {
        this.insert_date = insert_date;
    }

    public void setCanproducelist(List<String> canproducelist) {
        this.canproducelist = canproducelist;
    }

    public void setProduceneedlist(List<String> produceneedlist) {
        this.produceneedlist = produceneedlist;
    }

    public void setSuithero(List<String> suithero) {
        this.suithero = suithero;
    }

    public void setOthernames(List<String> othernames) {
        this.othernames = othernames;
    }

    public void setMaplist(List<String> maplist) {
        this.maplist = maplist;
    }

    public void setProplist(List<String> proplist) {
        this.proplist = proplist;
    }

    public String getGreatguide() {
        return greatguide;
    }

    public String getName() {
        return name;
    }

    public int getGood_id() {
        return good_id;
    }

    public String getPrice() {
        return price;
    }

    public String getLast_modify_date() {
        return last_modify_date;
    }

    public String getSaleprice() {
        return saleprice;
    }

    public String getCoprice() {
        return coprice;
    }

    public boolean getActive() {
        return active;
    }

    public String getGood_desc() {
        return good_desc;
    }

    public int getId() {
        return id;
    }

    public String getInsert_date() {
        return insert_date;
    }

    public List<String> getCanproducelist() {
        return canproducelist;
    }

    public List<String> getProduceneedlist() {
        return produceneedlist;
    }

    public List<String> getSuithero() {
        return suithero;
    }

    public List<String> getOthernames() {
        return othernames;
    }

    public List<String> getMaplist() {
        return maplist;
    }

    public List<String> getProplist() {
        return proplist;
    }
}

package com.zyc.oneselfapp.viewmodel;

import java.util.List;

/**
 * Created by zyc on 2016/12/16.
 */

public class GoodsList{
    private List<ItemsEntity> items;
    private List<String> props;
    private List<String> maps;

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public void setProps(List<String> props) {
        this.props = props;
    }

    public void setMaps(List<String> maps) {
        this.maps = maps;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public List<String> getProps() {
        return props;
    }

    public List<String> getMaps() {
        return maps;
    }

    public static class ItemsEntity {
        /**
         * othernames : ["小饼干"]
         * maplist : ["召唤师峡谷","水晶之痕","扭曲丛林","嚎哭深渊"]
         * name : 活力夹心饼干
         * good_id : 2009
         * proplist : ["对线","消耗品"]
         */
        private String name;
        private int good_id;
        private List<String> othernames;
        private List<String> maplist;
        private List<String> proplist;

        public void setName(String name) {
            this.name = name;
        }

        public void setGood_id(int good_id) {
            this.good_id = good_id;
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

        public String getName() {
            return name;
        }

        public int getGood_id() {
            return good_id;
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
}

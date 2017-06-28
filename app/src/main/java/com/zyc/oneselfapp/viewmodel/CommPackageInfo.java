package com.zyc.oneselfapp.viewmodel;

/**
 * Created by zyc on 2016/12/16.
 */

public class CommPackageInfo {
    private InfoEntity info;
    private UpdateImgEntity update_img;
    private ImgEntity img;

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public void setUpdate_img(UpdateImgEntity update_img) {
        this.update_img = update_img;
    }

    public void setImg(ImgEntity img) {
        this.img = img;
    }

    public InfoEntity getInfo() {
        return info;
    }

    public UpdateImgEntity getUpdate_img() {
        return update_img;
    }

    public ImgEntity getImg() {
        return img;
    }

    public static class InfoEntity {
        private String json_url;
        private String json_last_modify_time;
        private int json_size;
        private String json_md5;

        public void setJson_url(String json_url) {
            this.json_url = json_url;
        }

        public void setJson_last_modify_time(String json_last_modify_time) {
            this.json_last_modify_time = json_last_modify_time;
        }

        public void setJson_size(int json_size) {
            this.json_size = json_size;
        }

        public void setJson_md5(String json_md5) {
            this.json_md5 = json_md5;
        }

        public String getJson_url() {
            return json_url;
        }

        public String getJson_last_modify_time() {
            return json_last_modify_time;
        }

        public int getJson_size() {
            return json_size;
        }

        public String getJson_md5() {
            return json_md5;
        }
    }

    public static class UpdateImgEntity {
        private int img_size;
        private String img_url;
        private String img_last_modify_time;
        private String img_md5;

        public void setImg_size(int img_size) {
            this.img_size = img_size;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public void setImg_last_modify_time(String img_last_modify_time) {
            this.img_last_modify_time = img_last_modify_time;
        }

        public void setImg_md5(String img_md5) {
            this.img_md5 = img_md5;
        }

        public int getImg_size() {
            return img_size;
        }

        public String getImg_url() {
            return img_url;
        }

        public String getImg_last_modify_time() {
            return img_last_modify_time;
        }

        public String getImg_md5() {
            return img_md5;
        }
    }

    public static class ImgEntity {
        private int img_size;
        private String img_url;
        private String img_last_modify_time;
        private String img_md5;

        public void setImg_size(int img_size) {
            this.img_size = img_size;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public void setImg_last_modify_time(String img_last_modify_time) {
            this.img_last_modify_time = img_last_modify_time;
        }

        public void setImg_md5(String img_md5) {
            this.img_md5 = img_md5;
        }

        public int getImg_size() {
            return img_size;
        }

        public String getImg_url() {
            return img_url;
        }

        public String getImg_last_modify_time() {
            return img_last_modify_time;
        }

        public String getImg_md5() {
            return img_md5;
        }
    }
}

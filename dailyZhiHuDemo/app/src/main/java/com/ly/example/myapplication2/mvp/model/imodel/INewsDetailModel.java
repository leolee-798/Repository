package com.ly.example.myapplication2.mvp.model.imodel;

import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.mvp.RequestImp;

public interface INewsDetailModel {

    void loadNewsDetail(int newsId, RequestImp<NewsDetailBean> requestImp);
}

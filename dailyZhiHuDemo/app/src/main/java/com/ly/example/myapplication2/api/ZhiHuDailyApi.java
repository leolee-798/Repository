package com.ly.example.myapplication2.api;

import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.api.apibean.CreativesListBean;
import com.ly.example.myapplication2.api.apibean.ExtraBean;
import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.api.apibean.RecommendersBean;
import com.ly.example.myapplication2.api.apibean.ReplyBean;
import com.ly.example.myapplication2.api.apibean.SectionNewsBean;
import com.ly.example.myapplication2.api.apibean.SectionsBean;
import com.ly.example.myapplication2.api.apibean.ThemeNewsBean;
import com.ly.example.myapplication2.api.apibean.ThemesBean;
import com.ly.example.myapplication2.api.apibean.VersionBean;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface ZhiHuDailyApi {

    /**
     * URL: http://news-at.zhihu.com/api/7/prefetch-launch-images/1080*1920
     * 启动界面图像获取
     * prefetch-launch-images 后为图像分辨率，接受任意的 number*number 格式，
     * number 为任意非负整数，返回值均相同。
     *
     * @param DPI 所需的图片dpi
     * @return {@link CreativesListBean}
     */
    @GET("7/prefetch-launch-images/{DPI}")
    Observable<CreativesListBean> prefetchLaunchImages(@Path("DPI") String DPI);

    /**
     * Android: http://news-at.zhihu.com/api/4/version/android/2.3.0
     * 软件版本查询
     * URL 最后部分的数字代表所安装『知乎日报』的版本
     *
     * @param number 版本号
     * @return {@link VersionBean}
     */
    @GET("4/version/android/{number}")
    Observable<VersionBean> version(@Path("number") String number);

    /**
     * URL: http://news-at.zhihu.com/api/4/news/latest
     * 最新消息
     *
     * @return {@link NewsBean}
     */
    @GET("4/news/latest")
    Observable<NewsBean> lastNews();

    /**
     * URL: http://news-at.zhihu.com/api/4/news/3892357
     * 消息内容获取与离线下载
     *
     * @param id 消息id
     * @return {@link NewsDetailBean}
     */
    @GET("4/news/{id}")
    Observable<NewsDetailBean> newsDetail(@Path("id") int id);

    /**
     * URL: http://news-at.zhihu.com/api/4/news/before/20131119
     * 过往消息
     * 若果需要查询 11 月 18 日的消息，before 后的数字应为 20131119
     *
     * @param date 日期 格式：20131119
     * @return {@link NewsBean}
     */
    @GET("4/news/before/{date}")
    Observable<NewsBean> before(@Path("date") String date);

    /**
     * URL: http://news-at.zhihu.com/api/4/story-extra/#{id}
     * 新闻额外信息
     * 输入新闻的ID，获取对应新闻的额外信息，如评论数量，所获的『赞』的数量。
     *
     * @param id 消息id
     * @return {@link ExtraBean}
     */
    @GET("4/story-extra/{id}")
    Observable<ExtraBean> storyExtra(@Path("id") int id);

    /**
     * URL: http://news-at.zhihu.com/api/4/story/8997528/long-comments
     * 新闻对应长评论查看
     * 使用在 最新消息 中获得的 id，在 http://news-at.zhihu.com/api/4/story/#{id}/long-comments
     * 中将 id 替换为对应的 id，得到长评论 JSON 格式的内容
     *
     * @param id 消息id
     * @return {@link CommentsBean}
     */
    @GET("4/story/{id}/long-comments")
    Observable<CommentsBean> longComments(@Path("id") int id);

    /**
     * URL:https://news-at.zhihu.com/api/4/story/9449109/long-comments/before/29154756
     * 长评论加载更多
     *
     * @param id         消息id
     * @param comment_id 最后一条回复id
     * @return {@link CommentsBean}
     */
    @GET("4/story/{id}/long-comments/before/{comment_id}")
    Observable<CommentsBean> longCommentsBefore(@Path("id") int id, @Path("comment_id") int comment_id);

    /**
     * URL: http://news-at.zhihu.com/api/4/story/4232852/short-comments
     * 新闻对应短评论查看
     * 使用在 最新消息 中获得的 id，在 http://news-at.zhihu.com/api/4/story/#{id}/short-comments
     * 中将 id 替换为对应的 id，得到短评论 JSON 格式的内容
     *
     * @param id 消息id
     * @return {@link CommentsBean}
     */
    @GET("4/story/{id}/short-comments")
    Observable<CommentsBean> shortComments(@Path("id") int id);

    /**
     * URL:https://news-at.zhihu.com/api/4/story/9449109/short-comments/before/29159285
     * 短评论加载更多
     *
     * @param id         消息id
     * @param comment_id 最后一条回复id
     * @return {@link CommentsBean}
     */
    @GET("4/story/{id}/short-comments/before/{comment_id}")
    Observable<CommentsBean> shortCommentsBefore(@Path("id") int id, @Path("comment_id") int comment_id);

    /**
     * URL: http://news-at.zhihu.com/api/4/themes
     * 主题日报列表查看
     *
     * @return {@link ThemesBean}
     */
    @GET("4/themes")
    Observable<ThemesBean> themes();

    /**
     * URL: http://news-at.zhihu.com/api/4/theme/11
     * 使用在 主题日报列表查看 中获得需要查看的主题日报的 id，
     * 拼接在 http://news-at.zhihu.com/api/4/theme/ 后，得到对应主题日报 JSON 格式的内容
     *
     * @param id 主题id
     * @return {@link ThemeNewsBean}
     */
    @GET("4/theme/{id}")
    Observable<ThemeNewsBean> themeNewsList(@Path("id") int id);

    /**
     * URL:https://news-at.zhihu.com/api/4/theme/13/before/4739134
     * 主题列表上拉加载更多时，加载以前的内容
     *
     * @param theme_id 主题id
     * @param last_id  上次请求数据列表中最后一条的id
     * @return {@link ThemeNewsBean}
     */
    @GET("4/theme/{theme_id}/before/{last_id}")
    Observable<ThemeNewsBean> themeNewsListBefore(@Path("theme_id") int theme_id, @Path("last_id") int last_id);

    /**
     * URL: http://news-at.zhihu.com/api/3/sections
     * 栏目总览
     *
     * @return {@link SectionsBean}
     */
    @GET("4/sections")
    Observable<SectionsBean> sections();

    /**
     * URL: http://news-at.zhihu.com/api/3/section/1
     * URL 最后的数字见『栏目总览』中相应栏目的 id 属性
     *
     * @param id 消息id
     * @return {@link SectionNewsBean}
     */
    @GET("3/section/{id}")
    Observable<SectionNewsBean> sectionNewsList(@Path("id") int id);

    /**
     * URL: http://news-at.zhihu.com/api/4/story/7101963/recommenders
     * 查看新闻的推荐者
     *
     * @param id 推荐者id
     * @return
     */
    @GET("4/story/{id}/recommenders")
    Observable<RecommendersBean> recommenders(@Path("id") int id);

    /**
     * 点赞
     *
     * @param id   消息id
     * @param data 0：取消点赞  1：点赞
     * @return 空字符串
     */
    @FormUrlEncoded
    @POST("4/vote/story/{id}")
    Observable<ExtraBean> voteStory(@Path("id") int id, @Field("data") int data);

    /**
     * POST添加到收藏
     *
     * @param id id
     * @param fk 随便写的，实际不需要参数，但用POST需要一个Field,传null即可
     * @return 随便写的, 实际成功时返回{}
     */
    @FormUrlEncoded
    @POST("4/favorite/{id}")
    Observable<ExtraBean> collectStory(@Path("id") int id, @Field("fk") String fk);

    /**
     * DELETE从收藏中移除
     *
     * @param id id
     * @return 随便写的, 实际成功时返回{}
     */
    @DELETE("4/favorite/{id}")
    Observable<ExtraBean> collectStory(@Path("id") int id);

    /**
     * POST添加到收藏
     *
     * @param id id
     * @param fk 随便写的，实际不需要参数，但用POST需要一个Field,传null即可
     * @return 随便写的, 实际成功时返回{}
     */
    @FormUrlEncoded
    @POST("4/vote/comment/{id}")
    Observable<ExtraBean> voteComment(@Path("id") int id, @Field("fk") String fk);

    /**
     * DELETE从收藏中移除
     *
     * @param id id
     * @return 随便写的, 实际成功时返回{}
     */
    @DELETE("4/vote/comment/{id}")
    Observable<ExtraBean> voteComment(@Path("id") int id);

    /**
     * 回复评论
     *
     * @param id       回复的news的id
     * @param content  回复的内容
     * @param share_to 是否转发到微博 sina、tencent 可为null
     * @param reply_to 回复的评论的id 可为null
     * @return {}
     */
    @POST("4/news/{id}/comment")
    Observable<ReplyBean> replyComment(@Path("id") int id, @Body RequestBody reply);

    /**
     * 删除评论
     *
     * @param id 要删除的评论id
     * @return {}
     */
    @DELETE("4/comment/{id}")
    Observable<ReplyBean> deleteComment(@Path("id") int id);
}

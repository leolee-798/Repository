package com.ly.example.myapplication2.utils;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.api.apibean.ThemeNewsBean;
import com.ly.example.myapplication2.widgets.ExpandTextView;
import com.ly.example.myapplication2.widgets.ImageLoader;

import java.util.List;

import timber.log.Timber;

public class DataBindingUtil {

    @BindingAdapter("image")
    public static void imageLoader(ImageView imageView, String imageUrls) {
        if (!TextUtils.isEmpty(imageUrls)) {
            ImageLoader.loadXMLImage(imageView.getContext(), imageUrls, imageView);
        }
    }

    @BindingAdapter("avatar")
    public static void avatarLoader(ImageView imageView, String imageUrls) {
        ImageLoader.loadCircleImage(imageView.getContext(), imageUrls, imageView);
    }

    @BindingAdapter("selected")
    public static void itemSelected(View view, boolean isSelected) {
        view.setSelected(isSelected);
    }

    @BindingAdapter("editors")
    public static void loadEditors(LinearLayout layout, List<ThemeNewsBean.EditorsBean> editors) {
        if (layout.getChildCount() > 1) {
            layout.removeViews(1, layout.getChildCount() - 1);
        }
        if (editors != null && editors.size() > 0) {
            for (ThemeNewsBean.EditorsBean editor : editors) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        (int) CommonUtils.getDimens(R.dimen.x30),
                        (int) CommonUtils.getDimens(R.dimen.x30));
                params.gravity = Gravity.CENTER_VERTICAL;
                params.rightMargin = CommonUtils.getDimension(R.dimen.x10);
                ImageView imageView = new ImageView(layout.getContext());
                imageView.setLayoutParams(params);
                layout.addView(imageView);
                ImageLoader.loadCircleImage(imageView.getContext(), editor.getAvatar(), imageView);
            }
        }
    }

    @BindingAdapter("android:layout_marginTop")
    public static void setTopMargin(View view, int topMargin) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, topMargin,
                layoutParams.rightMargin, layoutParams.bottomMargin);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("selected")
    public static void selected(ImageView view, boolean selected) {
        view.setSelected(selected);
    }

    @BindingAdapter("comments")
    public static void comments(ExpandTextView view, CommentsBean.CommentBean commentBean) {
        Timber.d("DataBindingUtil mRoot: %s", view.toString());
        StringBuffer content = new StringBuffer();
        int reply_author_length;
        if (commentBean.getReply_to() != null && commentBean.getReply_to().getAuthor() != null) {
            content.append("//").append(commentBean.getReply_to().getAuthor()).append(":");
            content.append(commentBean.getReply_to().getContent());
            reply_author_length = commentBean.getReply_to().getAuthor().length() + 3;
            SpannableString ss = new SpannableString(content);
            ss.setSpan(new StyleSpan(Typeface.BOLD), 0,
                    reply_author_length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(CommonUtils.getColor(R.color.comments_quote)),
                    reply_author_length, content.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            view.setContent(ss);
            view.setVisibility(View.VISIBLE);
            view.setExpandState(commentBean.isExpand());
        } else {
            view.setVisibility(View.GONE);
        }
    }
}

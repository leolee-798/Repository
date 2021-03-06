package com.ly.example.myapplication2.mvp.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.api.apibean.ReplyBean;
import com.ly.example.myapplication2.databinding.ActivityReplyBinding;
import com.ly.example.myapplication2.mvp.contact.ReplyContact;
import com.ly.example.myapplication2.mvp.presenter.ReplyPresenter;
import com.ly.example.myapplication2.utils.Constant;


public class ReplyActivity extends BaseActivity implements ReplyContact.View, View.OnClickListener {
    private ActivityReplyBinding binding;
    private CommentsBean.CommentBean mCommentBean;
    private ReplyPresenter replyPresenter;
    private int newsId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reply);

        newsId = getIntent().getIntExtra(Constant.Intent_Extra.NEWS_ID, 0);
        if (newsId == 0) {
            finish();
        }

        mCommentBean = (CommentsBean.CommentBean) getIntent()
                .getSerializableExtra(Constant.Intent_Extra.REPLY_COMMENT);

        initView();
        initData();
    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        StringBuffer title = new StringBuffer();
        if (mCommentBean == null) {
            title.append(getString(R.string.comment_write));
        } else {
            title.append(getString(R.string.reply)).append(mCommentBean.getAuthor());
        }
        binding.commentToolbar.toolbar.setTitle(title);
        binding.commentToolbar.toolbar.setTitleTextColor(Color.WHITE);
        binding.commentToolbar.toolbar.setNavigationIcon(R.drawable.back_alpha);
        binding.commentToolbar.toolbar.inflateMenu(R.menu.toolbar_comment_publish);
        binding.commentToolbar.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.comment_publish) {
                if (TextUtils.isEmpty(binding.etContent.getText().toString().trim())) {
                    Toast.makeText(ReplyActivity.this, R.string.content_cannot_null, Toast.LENGTH_SHORT).show();
                    return true;
                }
                replyComment();
            }
            return true;
        });
        binding.commentToolbar.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initData() {
        replyPresenter = new ReplyPresenter(this);
        binding.ivShareSina.setOnClickListener(this);
        binding.ivShareTencent.setOnClickListener(this);
    }

    private void replyComment() {
        ReplyBean replyBean = new ReplyBean();
        StringBuffer share_to = null;
        if (binding.ivShareSina.isSelected()) {
            share_to = new StringBuffer();
            share_to.append(getString(R.string.sina));
        }
        if (binding.ivShareTencent.isSelected()) {
            if (share_to == null) {
                share_to = new StringBuffer();
                share_to.append(getString(R.string.tencent));
            } else {
                share_to.append(",");
                share_to.append(getString(R.string.tencent));
            }
        }
        if (share_to != null) {
            replyBean.setShare_to(share_to.toString());
        }
        if (mCommentBean != null) {
            replyBean.setReply_to(mCommentBean.getId());
        }
        replyBean.setContent(binding.etContent.getText().toString());

        replyPresenter.replyComment(newsId, replyBean);
    }


    @Override
    public void onLoadingShow() {
        showLoading();
    }

    @Override
    public void onLoadingDismiss() {
        dismiss();
    }

    @Override
    public void onReplySuccess() {
        finish();
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(ReplyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_share_sina:
                binding.ivShareSina.setSelected(!binding.ivShareSina.isSelected());
                break;
            case R.id.iv_share_tencent:
                binding.ivShareTencent.setSelected(!binding.ivShareTencent.isSelected());
                break;
        }
    }
}

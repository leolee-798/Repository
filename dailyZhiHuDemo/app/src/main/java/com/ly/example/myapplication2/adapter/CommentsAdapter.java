package com.ly.example.myapplication2.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.databinding.ItemCommentEmptyBinding;
import com.ly.example.myapplication2.databinding.ItemCommentsCommentBinding;
import com.ly.example.myapplication2.databinding.ItemCommentsCountBinding;
import com.ly.example.myapplication2.utils.CommonUtils;
import com.ly.example.myapplication2.widgets.OnReadMoreClickListener;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends BaseRecyclerViewAdapter<CommentsBean.CommentBean,
        BaseRecyclerViewHolder> implements View.OnClickListener {

    private static final int COMMENTS_COUNT_LONG = 1;
    private static final int COMMENTS_COUNT_SHORT = 2;
    private static final int COMMENTS_COMMENT = 3;
    private static final int COMMENTS_EMPTY = 4;

    private static List<CommentsBean.CommentBean> longComments;
    private static List<CommentsBean.CommentBean> shortComments;

    private static int longCommentsCount;
    private static int shortCommentsCount;

    private OnItemClickListener<CommentsBean.CommentBean> onItemClickListener;


    private int mDefaultClickPosition;

    public CommentsAdapter(int long_comments, int short_comments) {
        longComments = new ArrayList<>();
        shortComments = new ArrayList<>();
        longCommentsCount = long_comments;
        shortCommentsCount = short_comments;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case COMMENTS_COUNT_LONG:
                View longCountView = View.inflate(parent.getContext(), R.layout.item_comments_count, null);
                return new CountLongViewHolder(longCountView);
            case COMMENTS_COUNT_SHORT:
                View shortCountView = View.inflate(parent.getContext(), R.layout.item_comments_count, null);
                shortCountView.setOnClickListener(this);
                return new CountShortViewHolder(shortCountView);
            case COMMENTS_COMMENT:
                View commentView = View.inflate(parent.getContext(), R.layout.item_comments_comment, null);
                commentView.setOnClickListener(this);
                CommentViewHolder commentViewHolder = new CommentViewHolder(commentView);
                commentViewHolder.onReadMoreHolderListener = new CommentViewHolder.OnReadMoreHolderListener() {
                    @Override
                    public void onExpand(CommentsBean.CommentBean commentBean) {
                        if (longComments.contains(commentBean)) {
                            longComments.get(longComments.indexOf(commentBean)).setExpand(true);
                        } else if (shortComments.contains(commentBean)) {
                            shortComments.get(shortComments.indexOf(commentBean)).setExpand(true);
                        }
                    }

                    @Override
                    public void onFold(CommentsBean.CommentBean commentBean) {
                        if (longComments.contains(commentBean)) {
                            longComments.get(longComments.indexOf(commentBean)).setExpand(false);
                        } else if (shortComments.contains(commentBean)) {
                            shortComments.get(shortComments.indexOf(commentBean)).setExpand(false);
                        }
                    }
                };
                return commentViewHolder;
            case COMMENTS_EMPTY:
                View emptyView = View.inflate(parent.getContext(), R.layout.item_comment_empty, null);
                return new EmptyViewHolder(emptyView);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        holder.itemView.setTag(R.id.tag_position, position);
        if (holder instanceof CountLongViewHolder) {
            ((CountLongViewHolder) holder).bind(new CommentsBean.CommentBean());
        }
        if (holder instanceof CountShortViewHolder) {
            ((CountShortViewHolder) holder).bind(new CommentsBean.CommentBean());
            ((CountShortViewHolder) holder).itemView.setTag(
                    R.id.tag_type, COMMENTS_COUNT_SHORT);
            ((CountShortViewHolder) holder).itemView.setTag(
                    R.id.tag_content, null);
        }
        if (holder instanceof EmptyViewHolder) {
            holder.bind(new CommentsBean.CommentBean());
        }
        if (holder instanceof CommentViewHolder) {
            if (longComments.size() == 0) {
                ((CommentViewHolder) holder).bind(shortComments.get(position - 3));
                ((CommentViewHolder) holder).itemView.setTag(
                        R.id.tag_type, COMMENTS_COMMENT);
                ((CommentViewHolder) holder).itemView.setTag(
                        R.id.tag_content, shortComments.get(position - 3));
            } else {
                if (position >= longComments.size() + 2) {
                    ((CommentViewHolder) holder).bind(shortComments.get(position - longComments.size() - 2));
                    ((CommentViewHolder) holder).itemView.setTag(
                            R.id.tag_type, COMMENTS_COMMENT);
                    ((CommentViewHolder) holder).itemView.setTag(
                            R.id.tag_content, shortComments.get(position - longComments.size() - 2));
                } else {
                    ((CommentViewHolder) holder).bind(longComments.get(position - 1));
                    ((CommentViewHolder) holder).itemView.setTag(
                            R.id.tag_type, COMMENTS_COMMENT);
                    ((CommentViewHolder) holder).itemView.setTag(
                            R.id.tag_content, longComments.get(position - 1));
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        int longCount = longComments.size();
        if (longCount == 0) {
            longCount = 1;
        }
        return longCount + shortComments.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return COMMENTS_COUNT_LONG;
        }

        int longCount = longComments.size();

        if (longCount == 0 && position == 1) {
            return COMMENTS_EMPTY;
        }

        if (position == (longCount == 0 ? 1 : longCount) + 1) {
            return COMMENTS_COUNT_SHORT;
        }

        return COMMENTS_COMMENT;
    }

    public void addLongComments(List<CommentsBean.CommentBean> _longComments, boolean isClear) {
        if (isClear) {
            longComments.clear();
        }
        longComments.addAll(_longComments);
        notifyDataSetChanged();
    }

    public void addShortComments(List<CommentsBean.CommentBean> _shortComments, boolean isClear) {
        if (isClear) {
            shortComments.clear();
        }
        shortComments.addAll(_shortComments);
        notifyDataSetChanged();
    }

    public int getLastShortCommentId() {
        return shortComments.isEmpty() ? 0 : shortComments.get(shortComments.size() - 1).getId();
    }

    public int getLastLongCommentId() {
        return longComments.isEmpty() ? 0 : longComments.get(longComments.size() - 1).getId();
    }

    public int getShortCommentsCount() {
        return shortComments.size();
    }

    public int getLongCommentsCount() {
        return longComments.size() == 0 ? 1 : longComments.size();
    }

    public void clearShortComments() {
        shortComments.clear();
        notifyDataSetChanged();
    }


    public void delete(CommentsBean.CommentBean commentBean) {
        if (longComments.contains(commentBean)) {
            longComments.remove(commentBean);
        }

        if (shortComments.contains(commentBean)) {
            shortComments.remove(commentBean);
        }

        notifyDataSetChanged();
    }

    public void notifySelectedItem(boolean vote, int likes) {
        if (mDefaultClickPosition <= 0) {
            return;
        }
        if (mDefaultClickPosition <= getLongCommentsCount()) {
            longComments.get(mDefaultClickPosition - 1).setVoted(vote);
            longComments.get(mDefaultClickPosition - 1).setLikes(likes);
        } else {
            shortComments.get(mDefaultClickPosition - getLongCommentsCount() - 2).setVoted(vote);
            shortComments.get(mDefaultClickPosition - getLongCommentsCount() - 2).setLikes(likes);
        }

        notifyItemChanged(mDefaultClickPosition);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            int type = (int) v.getTag(R.id.tag_type);
            CommentsBean.CommentBean comment = (CommentsBean.CommentBean) v.getTag(R.id.tag_content);
            mDefaultClickPosition = (int) v.getTag(R.id.tag_position);
            if (COMMENTS_COMMENT == type && comment == null) {
                return;
            }
            onItemClickListener.onClick(v, comment);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<CommentsBean.CommentBean> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    ///////////////////////////////////////////////////////////////////////////
    // CommentViewHolder
    ///////////////////////////////////////////////////////////////////////////
    public static class CommentViewHolder extends BaseRecyclerViewHolder
            <ItemCommentsCommentBinding, CommentsBean.CommentBean> {
        public OnReadMoreHolderListener onReadMoreHolderListener;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(final CommentsBean.CommentBean commentBean) {
            binding.setComment(commentBean);
            binding.setLikes(String.valueOf(commentBean.getLikes()));
            binding.setVoted(commentBean.getVoted());
            binding.expand.setOnReadMoreListener(new OnReadMoreClickListener() {
                @Override
                public void onExpand() {
                    if (onReadMoreHolderListener != null) {
                        onReadMoreHolderListener.onExpand(commentBean);
                    }
                }

                @Override
                public void onFold() {
                    if (onReadMoreHolderListener != null) {
                        onReadMoreHolderListener.onFold(commentBean);
                    }
                }
            });
            super.bind(commentBean);
        }

        ///////////////////////////////////////////////////////////////////////////
        // OnReadMoreHolderListener
        ///////////////////////////////////////////////////////////////////////////
        public interface OnReadMoreHolderListener {
            void onExpand(CommentsBean.CommentBean commentBean);

            void onFold(CommentsBean.CommentBean commentBean);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // EmptyViewHolder
    ///////////////////////////////////////////////////////////////////////////
    public static class EmptyViewHolder extends BaseRecyclerViewHolder
            <ItemCommentEmptyBinding, CommentsBean.CommentBean> {

        public EmptyViewHolder(View itemView) {
            super(itemView);
            if (longCommentsCount == 0) {
                binding.ivCommentEmpty.setVisibility(View.VISIBLE);
                binding.tvCommentEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // CountShortViewHolder
    ///////////////////////////////////////////////////////////////////////////
    public static class CountLongViewHolder extends BaseRecyclerViewHolder
            <ItemCommentsCountBinding, CommentsBean.CommentBean> {

        public CountLongViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(CommentsBean.CommentBean commentBean) {
            binding.setCount(longCommentsCount + CommonUtils.getString(R.string.count_long_comments));
            super.bind(commentBean);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // CountShortViewHolder
    ///////////////////////////////////////////////////////////////////////////
    public static class CountShortViewHolder extends BaseRecyclerViewHolder
            <ItemCommentsCountBinding, CommentsBean.CommentBean> {

        public CountShortViewHolder(View itemView) {
            super(itemView);
            binding.ivCommentExpand.setVisibility(View.VISIBLE);
        }

        @Override
        public void bind(CommentsBean.CommentBean commentBean) {
            binding.setCount(shortCommentsCount + CommonUtils.getString(R.string.count_short_comments));
            super.bind(commentBean);
        }
    }
}

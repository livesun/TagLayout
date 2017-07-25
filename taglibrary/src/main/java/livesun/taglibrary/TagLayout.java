package livesun.taglibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 29028 on 2017/7/25.
 */

public class TagLayout extends ViewGroup{
    private TagAdapter mAdapter;

    public TagLayout(Context context) {
        this(context,null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width=0;
        int height=0;

        //一行的宽度
        int lineWidth=0;

        //一行的高度
        int lineHeight=0;

        //子控件的数目
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            //获取子控件
            View childAt = getChildAt(i);

            //测量
            measureChild(childAt,widthMeasureSpec,heightMeasureSpec);

            //得到margin
            MarginLayoutParams lp= (MarginLayoutParams) childAt.getLayoutParams();

            //子控件的宽度
            int childWidth = childAt.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;

            //子控件的高度
            int childHeight = childAt.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;

            //大于宽度换行
            if(lineWidth+childWidth>widthSize){
                //宽度
                width=Math.max(lineWidth,childWidth);
                lineWidth=childWidth;

                //高度
                height+=lineHeight;
                lineHeight=childHeight;
            }else{
                lineWidth+=childWidth;
                lineHeight=Math.max(lineHeight,childHeight);
            }

            //如果是最后一个
            if(i==childCount-1){
                width=Math.max(width,lineWidth);
                height+=lineHeight;
            }
        }

        //设置进去
        setMeasuredDimension((modeWidth==MeasureSpec.EXACTLY)?widthSize:width
                ,(modeHeight==MeasureSpec.EXACTLY)?heightSize:height
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int lineWidth=0;
        int lineHeight=0;
        int left=0,top=0;
        int measuredWidth = getMeasuredWidth();
        for(int i=0;i<childCount;i++){
            //获取子控件
            View childView = getChildAt(i);
            //如果子控件不可见跳出循环
            if(childView.getVisibility()==GONE){
                continue;
            }
            MarginLayoutParams lp = (MarginLayoutParams) childView
                    .getLayoutParams();
            int childWidth = childView.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            int childHeight = childView.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            if(lineWidth+childWidth> measuredWidth){
                //如果换行，高度叠加，left从左开始
                top += lineHeight;
                left = 0;
                //初始化lineHeight和lineWidth
                lineHeight = childHeight;
                lineWidth = childWidth;
            }else {
                lineHeight = Math.max(lineHeight,childHeight);
                lineWidth += childWidth;
            }
            //算出child的四个点
            int cl=left+lp.leftMargin;
            int ct=top+lp.topMargin;
            int cr=cl+childView.getMeasuredWidth();
            int cb=ct+childView.getMeasuredHeight();

            //摆放child的位置
            childView.layout(cl,ct,cr,cb);

            //更新left的位置,把下一个子控件的位置作为起点
            left+=childWidth;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }


    /**
     * 设置设配器
     * @param adapter
     */
    public void setAdapter(TagAdapter adapter){
        if(adapter==null){
            throw new NullPointerException("TagAdapter不能为空");
        }
        removeAllViews();
        mAdapter = adapter;
        int itemCount = mAdapter.getItemCount();
        for(int i=0;i<itemCount;i++){
            View childView = mAdapter.getView(i, this);
            addView(childView);
        }
    }
}

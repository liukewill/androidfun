package com.kwai.mv.videodetail.widget;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.dreamtobe.kpswitch.util.KPSwitchConflictUtil;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchFSPanelFrameLayout;
import com.kwai.mv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 全屏输入通用控件
 */
public class FullScreenEditLayout extends ConstraintLayout {

    public ViewGroup mEditLayout;
    public TextView mRemainTextLength;
    public EditText mEditText;
    public KPSwitchFSPanelFrameLayout mPanelLayout;
    public ImageView mEditBack;
    public TextView mEditDone;
    public List<OnKeyBoardChangeListener> mOnKeyBoardChangeListeners = new ArrayList<>();

    public FullScreenEditLayout(Context context) {
        super(context);
        init(context);
    }

    public FullScreenEditLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FullScreenEditLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.edit_panel_layout, this);
        mEditText = findViewById(R.id.video_edit);
        mRemainTextLength = findViewById(R.id.video_edit_left_text);
        mPanelLayout = findViewById(R.id.panel_root);
        mEditLayout = findViewById(R.id.video_edit_layout);

        mEditBack = findViewById(R.id.video_edit_back);
        mEditDone = findViewById(R.id.video_edit_done);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mRemainTextLength.setText("" + (500 - s.length()));
            }
        });


        mEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });
    }

    /**
     * Activity setContentView() 后调用
     *
     * @param activity
     */
    public void attachActivity(Activity activity) {
        KeyboardUtil.attach(activity, mPanelLayout, new KeyboardUtil.OnKeyboardShowingListener() {
            @Override
            public void onKeyboardShowing(boolean isShowing) {
                notifyKeyBoardChanged(isShowing);
                if (isShowing) {
                    mEditLayout.setVisibility(View.VISIBLE);
                } else {
                    mEditLayout.setVisibility(View.GONE);
                }
            }
        });

        KPSwitchConflictUtil.attach(mPanelLayout, mEditText);
    }

    public void showKeyBoard() {
        KPSwitchConflictUtil.showKeyboard(mPanelLayout, mEditText);
    }

    public void hideKeyboard() {
        KPSwitchConflictUtil.hidePanelAndKeyboard(mPanelLayout);
    }

    public void setEditMaxLength(int number) {
        mEditText.setMaxLines(number);
    }

    public void setOnDoneClickListener(OnDoneListener listener) {
        mEditDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDone(FullScreenEditLayout.this, mEditText.getText().toString());
            }
        });
    }

    public void addOnKeyboardChangeListener(OnKeyBoardChangeListener listener){
        if(listener !=null){
            mOnKeyBoardChangeListeners.add(listener);
        }
    }

    public void removeOnKeyboardChangeListener(OnKeyBoardChangeListener listener){
        mOnKeyBoardChangeListeners.remove(listener);
    }

    public void notifyKeyBoardChanged(boolean isKeyBoardShowing){
        for (OnKeyBoardChangeListener onKeyBoardChangeListener : mOnKeyBoardChangeListeners) {
            if(onKeyBoardChangeListener!=null){
                onKeyBoardChangeListener.onChanged(isKeyBoardShowing);
            }
        }
    }

    public interface OnDoneListener{
        void onDone(FullScreenEditLayout editLayout, String s);
    }

    public interface OnKeyBoardChangeListener{
        void onChanged(boolean isKeyBoardShowing);
    }
}

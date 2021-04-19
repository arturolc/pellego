package com.gitlab.capstone.pellego.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.axet.androidlibrary.widgets.ThemeUtils;
import com.gitlab.capstone.pellego.R;

/****************************************
 * Eli Hebdon
 *
 * Creates and handles the Toolbar RTL
 * view and interactions
 ***************************************/

@Keep
public class ToolbarRTLView extends ToolbarButtonView {
    public ToolbarRTLView(@NonNull Context context) {
        super(context);
    }

    public ToolbarRTLView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarRTLView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public ToolbarRTLView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void create() {
        super.create();
        image.setClickable(false);
        image.setFocusable(false);
        image.setImageResource(R.drawable.ic_gesture_black_24dp);
        image.setColorFilter(ThemeUtils.getColor(getContext(), R.color.white));
        image.setBackgroundDrawable(null);
        text.setText("RTL");
    }
}

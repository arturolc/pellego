package com.gitlab.capstone.pellego.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gitlab.capstone.pellego.app.Plugin;

/****************************************
 * Eli Hebdon
 *
 * Represents the wall paper layout which
 * is used directly with the library
 ***************************************/

public class WallpaperLayout extends FrameLayout {
    Plugin.View bg = new Plugin.View();

    public WallpaperLayout(@NonNull Context context) {
        super(context);
    }

    public WallpaperLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WallpaperLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public WallpaperLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        bg.drawWallpaper(canvas);
        super.dispatchDraw(canvas);
    }
}

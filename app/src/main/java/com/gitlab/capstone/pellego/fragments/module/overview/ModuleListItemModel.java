package com.gitlab.capstone.pellego.fragments.module.overview;

import android.graphics.drawable.Drawable;

/**********************************************
 Eli Hebdon

 Class representation of a module list item
 **********************************************/
public class ModuleListItemModel {
    private String mTitle;
    private String mSubtitle;
    private String description;
    private Drawable mIcon;

    public ModuleListItemModel(String title, String subtitle, Drawable icon) {
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = icon;
    }

    public ModuleListItemModel(String title, String subtitle, Drawable icon, String description) {
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = icon;
        this.description = description;
    }

    public ModuleListItemModel(String title) {
        mTitle = title;
        mSubtitle = "";
        mIcon = null;
        description = "";
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String mSubtitle) {
        this.mSubtitle = mSubtitle;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable mIcon) {
        this.mIcon = mIcon;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

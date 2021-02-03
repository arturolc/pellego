package com.example.pellego.ui.module.overview;

/**********************************************
 Eli Hebdon

 Class representation of a module list item
 **********************************************/
public class ModuleListItemModel {
    private String mTitle;
    private String mSubtitle;
    private int mIcon;

    public ModuleListItemModel(String title, String subtitle, int icon) {
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = icon;
    }

    public ModuleListItemModel(String title) {
        mTitle = title;
        mSubtitle = "";
        mIcon = 0;
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

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }
}

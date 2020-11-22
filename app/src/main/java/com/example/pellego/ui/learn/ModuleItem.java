package com.example.pellego.ui.learn;

/**********************************************
 Eli Hebdon

 Representation of a single learning module list item
 **********************************************/
public class ModuleItem {
    String mTitle;
    String mSubtitle;
    int mIcon;

    public ModuleItem(String title, String subtitle, int icon) {
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = icon;
    }
}

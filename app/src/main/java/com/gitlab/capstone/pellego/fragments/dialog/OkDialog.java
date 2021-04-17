package com.gitlab.capstone.pellego.fragments.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.gitlab.capstone.pellego.R;

public class OkDialog extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button ok;

    public OkDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        ok = (Button) findViewById(R.id.ok_dialog_button);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
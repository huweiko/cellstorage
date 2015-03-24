package com.cellstorage.custom;
import com.example.cellstorage.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
 
 
/********************************************************************
 * [Summary]
 *       TODO 请在此处简要描述此类所实现的功能。因为这项注释主要是为了在IDE环境中生成tip帮助，务必简明扼要
 * [Remarks]
 *       TODO 请在此处详细描述类的功能、调用方法、注意事项、以及与其它类的关系.
 *******************************************************************/
 
public class CustomProgressDialog extends Dialog {
    private static Context context = null;
    private static CustomProgressDialog customProgressDialog = null;
     
    public CustomProgressDialog(Context x_context){
        super(x_context);
        context = x_context;
    }
     
    public CustomProgressDialog(Context x_context, int theme) {
        super(x_context, theme);
        context = x_context;
    }
     
    public static CustomProgressDialog createDialog(Context x_context){
        customProgressDialog = new CustomProgressDialog(x_context,R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.customprogressdialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
         
        return customProgressDialog;
    }
  
    public void onWindowFocusChanged(boolean hasFocus){
         
        if (customProgressDialog == null){
            return;
        }
         
        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
//        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
//        animationDrawable.start();
        Animation animation = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.animation_dialog);
        animation.setInterpolator(new LinearInterpolator());
        imageView.setAnimation(animation);
        animation.start();
    }
  
    /**
     * 
     * [Summary]
     *       setTitile 标题
     * @param strTitle
     * @return
     *
     */
    public CustomProgressDialog setTitile(String strTitle){
        return customProgressDialog;
    }
     
    /**
     * 
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public CustomProgressDialog setMessage(String strMessage){
        TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
         
        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }
         
        return customProgressDialog;
    }
    

}

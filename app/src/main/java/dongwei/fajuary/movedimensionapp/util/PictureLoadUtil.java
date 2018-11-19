package dongwei.fajuary.movedimensionapp.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import dongwei.fajuary.movedimensionapp.base.MovedimensionApp;

/**
 * 作者：${神游风云（fajuary）} on 2018/1/29 11:45
 * 邮箱：fajuary@foxmail.com
 * 图片加载
 */

public class PictureLoadUtil {
    public static void loadDefaultImg(ImageView imageView, String imgUrl, int defaultImgId){
        Picasso
                .with(MovedimensionApp.getInstance().getApplicationContext())
                .load(imgUrl)
                .error(defaultImgId)
                .placeholder(defaultImgId)
                .into(imageView);

    }

}

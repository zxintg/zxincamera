package com.zxin.camera.utils;

import android.content.Context;
import android.content.Intent;

import com.zxin.camera.activity.AlbumActivity;
import com.zxin.camera.activity.AlbumPreviewActivity;
import com.zxin.camera.activity.CameraActivity;
import com.zxin.camera.callback.IPhotoCall;
import com.zxin.camera.model.PhotoPreviewBean;
import com.zxin.root.app.SystemPersimManage;
import com.zxin.root.util.BaseStringUtils;
import com.zxin.root.util.SystemInfoUtil;
import com.zxin.root.view.dialog.NiceDialog;

/*****
 * 相机操作工具
 *
 * liukui 2018/01/30 14:23
 *
 */
public class CameraAlbumUtils {
    private static CameraAlbumUtils cameraAlbumUtil;
    private static Context mContext;

    private NiceDialog niceDialog;

    private CameraAlbumUtils(Context mContext) {
        this.mContext = mContext;
    }

    public static CameraAlbumUtils getInstance(Context mContext) {
        if (cameraAlbumUtil == null) {
            synchronized (CameraAlbumUtils.class) {
                if (cameraAlbumUtil == null) {
                    cameraAlbumUtil = new CameraAlbumUtils(mContext);
                }
            }
        }

        return cameraAlbumUtil;
    }

    /****
     * 拍照片或重相册中获取
     */
    public CameraAlbumUtils getPhoto() {
        if (niceDialog == null) {
            niceDialog = NiceDialog.init();
        }
        niceDialog.setOnNiceDialogListener(new NiceDialog.NiceDialogListener() {
            @Override
            public void onItemListener(int index, String item) {
                if (item.equals("拍照")) {
                    //相机拍照
                    checkCameraPermission();
                } else {
                    //相册
                    checkAlbumPermission();
                }
            }
        });
        niceDialog.setCommonLayout(new String[]{"从相册选择", "拍照"}, true);
        return cameraAlbumUtil;
    }

    /****
     * 设置裁剪类型
     * @param TailorType
     * @return
     */
    private int TailorType = -1;

    public CameraAlbumUtils setTailorType(int TailorType) {
        this.TailorType = TailorType;
        return cameraAlbumUtil;
    }

    public int getTailorType() {
        return TailorType == -1 ? VanCropType.CROP_TYPE_RECTANGLE : TailorType;
    }

    private int with = -1, height = -1;

    public CameraAlbumUtils setParam(int with, int height) {
        this.with = with;
        this.height = height;
        return cameraAlbumUtil;
    }

    public CameraAlbumUtils setSuperRotate(boolean isRotate) {
        this.isRotate = isRotate;
        return cameraAlbumUtil;
    }

    private boolean isRotate = false;

    public boolean isSuperRotate() {
        return isRotate;
    }

    public int getWith() {
        return with == -1 ? SystemInfoUtil.getInstance(mContext).getScreenWidth() : with;
    }

    public int getHeight() {
        return height == -1 ? SystemInfoUtil.getInstance(mContext).getScreenHeight() : height;
    }

    /******
     * 相机动态权限权限
     * liukui 2017/05/19
     */
    public void checkCameraPermission() {
        new SystemPersimManage(mContext) {
            @Override
            public void resultPerm(boolean isCan, int requestCode) {
                if (isCan)
                    mContext.startActivity(new Intent(mContext, CameraActivity.class));
            }
        }.CheckedCamera();
    }

    /******
     * 存储卡可读写动态权限
     * liukui 2017/05/19
     */
    public void checkAlbumPermission() {
        new SystemPersimManage(mContext) {
            @Override
            public void resultPerm(boolean isCan, int requestCode) {
                if (isCan)
                    mContext.startActivity(new Intent(mContext, AlbumActivity.class));
            }
        }.CheckedAlbum();
    }


    public void startAlbumPreviewActivity(PhotoPreviewBean previewBean) {
        mContext.startActivity(new Intent(mContext, AlbumPreviewActivity.class).putExtra(BaseStringUtils.ACTIVITY_DATA, previewBean));
    }

    private IPhotoCall callI;

    public CameraAlbumUtils setIPhotoCall(IPhotoCall callI) {
        this.callI = callI;
        return cameraAlbumUtil;
    }

    public IPhotoCall getIPhotoCall() {
        return callI;
    }

}

package com.baidu.lbs.services.robust;

import android.text.TextUtils;
import android.util.Log;

import com.baidu.lbs.commercialism.app.AppEnv;
import com.baidu.lbs.commercialism.app.DuApp;
import com.baidu.lbs.commercialism.login.LoginManager;
import com.baidu.lbs.comwmlib.DeviceInfo;
import com.baidu.lbs.comwmlib.net.callback.JsonFullCallback;
import com.baidu.lbs.manager.SharedPrefManager;
import com.baidu.lbs.net.http.NetInterface;
import com.baidu.lbs.net.type.CheckNewVersionBug;
import com.baidu.lbs.net.type.CheckNewVersionResponse;
import com.baidu.lbs.util.MD5Utils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.meituan.robust.Patch;
import com.meituan.robust.PatchExecutor;
import com.meituan.robust.RobustCallBack;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by iwm on 17/3/14.
 */

public class BugUpgradeManager {

    //liuke2

    private static BugUpgradeManager mInstance;
    public static final String PATCH_MD5_KEY="robust_patch_md5";
    public static String mFilePath = AppEnv.getBugfixDir() + AppEnv.FILE_NAME_PATCH;
    private CheckNewVersionBug mContent;
    private boolean mIsDownloading;

    private BugUpgradeManager() {

    }

    public static BugUpgradeManager getInstance() {
        if (mInstance == null) {
            mInstance = new BugUpgradeManager();
        }
        return mInstance;
    }

    public void checkNewVersionRequest() {
        DeviceInfo deviceInfo = DeviceInfo.getInstance(DuApp.getAppContext());
        String version = deviceInfo.getAppVersionName();
        NetInterface.checkNewVersionBug(version, mNetCallback);
    }

    public void startPatch() {
        String localMd5 = getLocalFileVersion();
        Log.i("on-patch","localMD5--"+localMd5);
        if (localMd5 != null &&localMd5.equalsIgnoreCase(getPatchMd5InSp())) {
            runRobust();
        }
    }

    private void startDownload() {
        if (mIsDownloading) {
            return;
        }

        if (mContent == null || TextUtils.isEmpty(mContent.url) || TextUtils.isEmpty(mContent.filemd5)) {
            stopService();
            return;
        }


        if (mContent.filemd5.equalsIgnoreCase(getLocalFileVersion())) {
            Log.i("on-patch","S-MD5==L-MD5-----startpatch()");
            stopService();
            startPatch();
        } else {
            FileDownloader.getImpl().create(mContent.url)
                    .setPath(mFilePath)
                    .setListener(mFileDownloadListener)
                    .start();
            mIsDownloading = true;
        }
    }



    private String getLocalFileVersion() {
        String localMd5 = "";
        try {
            File file = new File(mFilePath);
            localMd5 = MD5Utils.getFileMD5String(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localMd5;
    }



    // data callback
    private FileDownloadListener mFileDownloadListener = new FileDownloadListener() {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void completed(BaseDownloadTask task) {
            mIsDownloading = false;
            Log.i("on-patch","DOWNLOAD-----startpatch()");
            stopService();
            startPatch();
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            mIsDownloading = false;
        }

        @Override
        protected void warn(BaseDownloadTask task) {

        }
    };

    private JsonFullCallback<CheckNewVersionResponse> mNetCallback = new JsonFullCallback<CheckNewVersionResponse>() {


        @Override
        public void onRequestComplete(CheckNewVersionResponse data) {
            if (data != null && data.data != null) {
                mContent = data.data.bug_upgrade;
                savePatchMd5InSp();
                startDownload();
            }
        }

        @Override
        public void onCallCancel(Call call) {

        }

        @Override
        public void onCallFailure(Call call, IOException e) {

        }
    };


    class Callback implements RobustCallBack {

        @Override
        public void onPatchListFetched(boolean result, boolean isNet, List<Patch> patches) {
            System.out.println(" robust arrived in onPatchListFetched");
        }

        @Override
        public void onPatchFetched(boolean result, boolean isNet, Patch patch) {
            System.out.println(" robust arrived in onPatchFetched");
        }

        @Override
        public void onPatchApplied(boolean result, Patch patch) {
            System.out.println(" robust arrived in onPatchApplied ");

        }

        @Override
        public void logNotify(String log, String where) {
            System.out.println(" robust arrived in logNotify " + where);
        }

        @Override
        public void exceptionNotify(Throwable throwable, String where) {
            throwable.printStackTrace();
            System.out.println(" robust arrived in exceptionNotify " + where);
        }
    }

    private void runRobust(){
        new PatchExecutor(DuApp.getAppContext(),new PatchManipulateImp(),new Callback()).start();
    }

    private void savePatchMd5InSp(){
        if(mContent!=null) {
            SharedPrefManager.saveString(PATCH_MD5_KEY,mContent.filemd5);
        }
    }

    private String getPatchMd5InSp(){
        String s=SharedPrefManager.getString(PATCH_MD5_KEY,"");
        Log.i("on-patch","SP----MD5()"+s);
        return s;
    }

    private void stopService(){
        LoginManager.getInstance().stopIndependentService();
    }

}

package com.baidu.lbs.services.robust;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.baidu.lbs.commercialism.app.AppEnv;
import com.meituan.robust.Patch;
import com.meituan.robust.PatchManipulate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenan on 17/9/6.
 */

public class PatchManipulateImp extends PatchManipulate{
    public static final String patchPath= AppEnv.getBugfixDir() + AppEnv.FILE_NAME_PATCH_NO_FORM;

    @Override
        protected List<Patch> fetchPatchList(Context context) {

            Patch patch=new Patch();
            patch.setName("robust");
            patch.setLocalPath(patchPath);

            Log.i("lk","imp"+patch.getLocalPath());
            patch.setPatchesInfoImplClassFullName("com.baidu.lbs.commercialism.patch.PatchesInfoImpl");

            List<Patch> patches=new ArrayList<>();
            patches.add(patch);

            return patches;
        }

    @Override
    protected boolean verifyPatch(Context context, Patch patch) {
        //放到app的私有目录
        patch.setTempPath(context.getCacheDir()+ File.separator+"robust"+File.separator + "patch");
        try {
            copy(patch.getLocalPath(), patch.getTempPath());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("copy source patch to local patch error, no patch execute in path "+patch.getTempPath());
        }

        return true;
    }

    @Override
    protected boolean ensurePatchExist(Patch patch) {
        return true;
    }


    public void copy(String srcPath,String dstPath) throws IOException {
        File src=new File(srcPath);
        if(!src.exists()){
            throw new RuntimeException("source patch does not exist ");
        }
        File dst=new File(dstPath);
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs();
        }
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

}

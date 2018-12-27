package com.example.aes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

  /**
   * wuxinrong added 2018/07/05
   * AES加密、解密工具类
   * 用于加密文件、字符串.
   */
    // 秘钥长度
   static final int SECRET_KEY_LENGTH = 16;

    // 补全秘钥时使用的默认字符串
    static final String DEFAULT_FILLING_STR = "0";

    static final int BUF_SIZE = 1024;

    // 加密算法名称
    static final String ALGORITHM_AES = "AES";

    // 用户生成文件加密、解密秘钥的字符串
    static final String KEY_PART = "466766766676597E15856FC9";

    public static final String FILE_KEY_PART_YEAR = "1988";

    /**
     * 获取默认秘钥.
     *
     * @return 返回默认秘钥
     */
    public static String obtainDefaultSecretKey() {
      StringBuilder keyBuilder = new StringBuilder();

      final int len = KEY_PART.length();
      for (int i = 0; i < (len >> 1); i ++) {
        String sb = String.valueOf(KEY_PART.charAt(i)) +
            KEY_PART.charAt(len - 1 - i);
        char ch = (char) Integer.parseInt(sb, SECRET_KEY_LENGTH);
        keyBuilder.append(ch);
      }

      keyBuilder.append(FILE_KEY_PART_YEAR);
      return keyBuilder.toString();
    }

    /**
     * 密钥key，默认补的数字，补全16位数，以保证安全补全至少16位长度.
     *
     * @param key        秘钥字符串
     * @param required   秘钥最短长度
     * @param defaultStr 补全时采用的默认字符串
     * @return 补全长度后的秘钥字符串
     */
    private static String ensureKeyLength(String key, final int required, String defaultStr) {

      int len = key.length();
      if (len < required) {
        while (len < required) {
          StringBuffer sb = new StringBuffer();
          sb.append(key).append(defaultStr);
          key = sb.toString();
          len = key.length();
        }
      }
      return key;
    }

    /**
     * 获取 Cipher.
     *
     * @param key  秘钥，可以为空，为空时使用默认秘钥
     * @param mode 模式：解密 或 加密
     * @return Cipher对象
     */
    private static Cipher obtainCipher(final String key, int mode) {
      Cipher cipher = null;
      try {
        // 秘钥为空时采用默认秘钥
        String realKey = TextUtils.isEmpty(key) ? obtainDefaultSecretKey() : key;
        // 保证16字节秘钥
        ensureKeyLength(realKey, SECRET_KEY_LENGTH, DEFAULT_FILLING_STR);
        // 字符串转为字节数组
        byte[] keyArray = realKey.getBytes("UTF-8");

        SecretKeySpec spec = new SecretKeySpec(keyArray, ALGORITHM_AES);

        cipher = Cipher.getInstance(ALGORITHM_AES);
        cipher.init(mode, spec, new IvParameterSpec((new byte[cipher.getBlockSize()])));
      } catch (UnsupportedEncodingException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
          | InvalidAlgorithmParameterException e) {
        e.printStackTrace();
      }
      return cipher;
    }

    /**
     * 加密字符串.
     *
     * @param src 源字符串
     * @param key 秘钥
     * @return 加密后的字符串
     */
    public static String encrypt(final String src, final String key) {
      if (TextUtils.isEmpty(src)) {
        return null;
      }

      String result;
      String realKey = TextUtils.isEmpty(key) ? obtainDefaultSecretKey() : key;
      Cipher cipher = obtainCipher(realKey, Cipher.ENCRYPT_MODE);
      byte[] encrypted = new byte[0];
      byte[] content;
      try {
        // 字符串转为字节数组
        content = src.getBytes("UTF-8");
        encrypted = cipher.doFinal(content);
      } catch (UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
        e.printStackTrace();
      }

      result = byte2hex(encrypted);
      return result;
    }

    /**
     * 解密字符串.
     *
     * @param src 源字符串
     * @param key 秘钥
     * @return 解密后的字符串
     */
    public static String decrypt(final String src, final String key) {

      if (TextUtils.isEmpty(src)) {
        return null;
      }

      String result = null;
      String realKey = TextUtils.isEmpty(key) ? obtainDefaultSecretKey() : key;
      Cipher cipher = obtainCipher(realKey, Cipher.DECRYPT_MODE);

      byte[] decrypted;
      byte[] content = hex2byte(src);
      try {
        decrypted = cipher.doFinal(content);
        result = new String(decrypted, "UTF-8");
      } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      return result;
    }

    /**
     * 加密文件.
     *
     * @param srcFile    源文件
     * @param toFileName 加密后的文件名
     * @param dir        加密后的文件的存储路径
     * @param key        秘钥，可以为空，为空时采用默认秘钥
     * @return 成功-返回加密后的文件路径 失败-返回原文件
     */
    public static File encrypt(final File srcFile, String toFileName, final String dir, final String key) {
      File encryptFile = srcFile;
      FileInputStream fis = null;
      FileOutputStream fos = null;

      System.out.println("ModuleManager-"+"准备加密...");
      System.out.println("ModuleManager-"+"加密前的文件名 > " + srcFile.toString());
      System.out.println("ModuleManager-"+"加密后的文件名 > " + toFileName);
      System.out.println("ModuleManager-"+"加密后的文件存储目录 > " + dir);

      try {
        if (srcFile.exists()) {
          fis = new FileInputStream(srcFile);
          encryptFile = new File(dir + "/" + toFileName);
          System.out.println("ModuleManager-" +"加密后的文件存储路径 > " + encryptFile.getAbsolutePath());
          fos = new FileOutputStream(encryptFile);

          // 获取Cipher对象
          Cipher cipher = obtainCipher(key, Cipher.ENCRYPT_MODE);

          // 以加密数据流方式写入文件
          CipherInputStream cis = new CipherInputStream(fis, cipher);
          byte[] cache = new byte[BUF_SIZE];

          int read;
          while ((read = cis.read(cache)) != -1) {
            fos.write(cache, 0, read);
            fos.flush();
          }
          cis.close();
        }

      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          if (null != fis) {
            fis.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }

        try {
          if (null != fos) {
            fos.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return encryptFile;
    }

    /**
     * 解密文件.
     *
     * @param srcFile    需要加密的源文件
     * @param toFileName 加密后的文件名
     * @param dir        加密文件的存储路径
     * @param key        秘钥，可以为空，为空时采用默认秘钥
     * @return 成功-返回解密后的文件路径 失败-返回null
     */
    public static File decrypt(final File srcFile, final String toFileName, final String dir, final String key) {
      File decryptFile;
      FileInputStream fis = null;
      FileOutputStream fos = null;
      try {
        fis = new FileInputStream(srcFile);

        // 保证文件路径正确
        String pathName;
        if (!srcFile.getPath().endsWith("/")) {
          pathName = dir + "/" + toFileName;
        } else {
          pathName = dir + toFileName;
        }

        decryptFile = new File(pathName);

        fos = new FileOutputStream(decryptFile);

        // 获取Cipher，模式为解密
        Cipher cipher = obtainCipher(key, Cipher.DECRYPT_MODE);

        // 以解密数据流方式写入文件
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);
        byte[] buffer = new byte[BUF_SIZE];
        int read;
        while ((read = fis.read(buffer)) >= 0) {
          cos.write(buffer, 0, read);
        }
        cos.close();
      } catch (IOException e) {
        e.printStackTrace();
        decryptFile = null;
      } finally {
        try {
          if (null != fis) {
            fis.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }

        try {
          if (null != fos) {
            fos.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return decryptFile;
    }

    /**
     * 字节数组转化为16进制字符串.
     *
     * @param b
     * @return 16进制字符串
     */
    private static String byte2hex(byte[] b) { // 一个字节的数，
      StringBuilder sb = new StringBuilder(b.length * 2);
      String tmp = "";
      for (byte aB : b) {
        // 整数转成十六进制表示
        tmp = (Integer.toHexString(aB & 0XFF));
        if (tmp.length() == 1) {
          sb.append("0");
        }
        sb.append(tmp);
      }
      return sb.toString().toUpperCase(); // 转成大写
    }

    /**
     * 将hex字符串转换成字节数组.
     *
     * @param input 16进制字符串
     * @return 字节数组
     */
    private static byte[] hex2byte(String input) {
      if (input == null || input.length() < 2) {
        return new byte[0];
      }
      input = input.toLowerCase();
      int l = input.length() / 2;
      byte[] result = new byte[l];
      for (int i = 0; i < l; ++i) {
        String tmp = input.substring(2 * i, 2 * i + 2);
        result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
      }
      return result;
    }
}

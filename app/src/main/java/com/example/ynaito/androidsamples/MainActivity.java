package com.example.ynaito.androidsamples;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private static final int MAX_BYTEBUFFER_LENGTH = 1024;
    private static final String TAG_KEY = "#EXT-X-KEY";
    private boolean isEncrypted = false;
    private String encryptionKeyUri;

    private static final String METHOD_NONE = "NONE";
    private static final String METHOD_AES128 = "AES-128";

    private static final Pattern REGEX_METHOD = Pattern.compile("METHOD=(" + METHOD_NONE + "|"
            + METHOD_AES128 + ")");
    private static final Pattern REGEX_URI = Pattern.compile("URI=\"(.+?)\"");
    private static final Pattern REGEX_IV = Pattern.compile("IV=([^,.*]+)");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File playlistFile = getPlaylistFile();
        byte[] playlistData = loadPlaylistFile(playlistFile);
        if (playlistData == null) {
            return;
        }
        try {
            String result = new String(playlistData, "UTF-8");
            Log.d(TAG, result);
        } catch (UnsupportedEncodingException e) {
        }
        List<String> urls = parseMediaPlaylist(new ByteArrayInputStream(playlistData));
        saveLocalFile(urls);
    }

    private File getPlaylistFile() {
        File moviesDir = this.getExternalFilesDir(android.os.Environment.DIRECTORY_MOVIES);
        Log.d(TAG, moviesDir.getPath());
        File playlistFile = new File(moviesDir, "media.m3u8");
        return playlistFile;
    }

    private byte[] loadPlaylistFile(File file) {
        Log.d(TAG, "loadPlaylistFile");
        final int size = (int) file.length();
        if (size == 0 || size > MAX_BYTEBUFFER_LENGTH) {
            Log.d(TAG, "loadPlaylistFile: File size is incorrect: " + size);
            return null;
        }
        FileInputStream fis = null;
        byte[] data = new byte[size];
        try {
            fis = new FileInputStream(file);
            if (size != fis.read(data, 0, size)) {
                Log.d(TAG, "loadPlaylistFile: Could not load data");
                return null;
            } else {
                Log.d(TAG, "loadPlaylistFile: Load keySetId successfully");
                return data;
            }
        } catch (IOException e) {
            Log.d(TAG, "loadPlaylistFile: Unable to load keySetId", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
                fis = null;
            }
        }
        return null;
    }

    private List<String> parseMediaPlaylist(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<String> extraUrls = new ArrayList<String>();
//        List<String> extraLines = new ArrayList<String>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith(TAG_KEY)) {
                    String keyUri = parseKeyUri(line);
                    String newKeyUrl = changeLocalFilePath(keyUri);
                    String newLine = line.replace(keyUri, newKeyUrl);
                    extraUrls.add(newLine);
                    Log.d(TAG, "key " + line);
                    Log.d(TAG, "key " + newLine);
                    Log.d(TAG, "key " + newKeyUrl);
                } else if (line.startsWith("#")) {
                    extraUrls.add(line);
                    Log.d(TAG, "tag " + line);
                } else {
                    String localUrl = changeLocalFilePath(line);
                    extraUrls.add(localUrl);
                    Log.d(TAG, "url " + line);
                    Log.d(TAG, "url " + localUrl);
                }
            }
        } catch (IOException e) {
            Log.d(TAG, "parseMediaPlaylist " + e);
        }
        return extraUrls;
    }

    private static String parseStringAttr(String line, Pattern pattern) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find() && matcher.groupCount() == 1) {
            return matcher.group(1);
        }
        return null;
    }

    private String parseKeyUri(String line) {
        String method = parseStringAttr(line, REGEX_METHOD);
        isEncrypted = METHOD_AES128.equals(method);
        if (isEncrypted) {
            encryptionKeyUri = parseStringAttr(line, REGEX_URI);
//            encryptionIV = parseOptionalStringAttr(line, REGEX_IV);
        } else {
            encryptionKeyUri = null;
//            encryptionIV = null;
        }
        return encryptionKeyUri;
    }

    private String changeLocalFilePath(String url) {
        String savePath = getSavePath();
        String segment = Uri.parse(url).getLastPathSegment();
        return savePath + File.separator + segment;
    }

    private boolean saveLocalFile(List<String> lines) {
        boolean status = true;
        String savePath = getSavePath();
        File playlistFile = new File(new File(savePath), "media_360.m3u8");
        BufferedWriter bw = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(playlistFile, false);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (FileNotFoundException e) {
            status = false;
            Log.d(TAG, "exception " + e);
        } catch (IOException e) {
            status = false;
            Log.d(TAG, "exception " + e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
                bw = null;
            }
        }
        return status;
    }

    private String getSavePath() {
        File moviesDir = this.getExternalFilesDir(android.os.Environment.DIRECTORY_MOVIES);
        return moviesDir.getPath();
    }

    private String getPrivateSavePath() {
        File moviesDir = this.getFilesDir();
        return moviesDir.getPath();
    }
}

//localのパスにして書き出す。

//HlsPlaylistParser.parseMedeiPlaylistを参考にする
//特定のタグではなく、#から始まっていない場合、urlとする


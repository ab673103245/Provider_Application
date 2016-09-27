package qianfeng.provider_application;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class MyContentProvider extends ContentProvider{
    private static final String AUTHORITIES = "qianfeng.provider_application.mycontentprovider";

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static SQLiteDatabase db;

    static { // 静态代码块内，初始化UriMatcher的匹配条件
        uriMatcher.addURI(AUTHORITIES,"user",1); // 如果路径是qianfeng.provider_application.mycontentprovider/user的话，那就返回匹配结果1

    }

    //当 ContentProvider创建成功时调用，返回true表示ContentProvider创建成功
    @Override
    public boolean onCreate() {  // 这个方法什么时候被调用？只要这个应用被安装的时候，这个onCreate就会被调用。

        // 什么时候执行这个方法？
        // 当被调用 getContentResolver()时
        db = new DBHelper(getContext()).getReadableDatabase();

        Log.d("google-my:", "onCreate: MyContentProvider的onCreate()方法什么时候被执行？看一下怎么打印..." );
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code = uriMatcher.match(uri);

        Uri uri1 = null;
        switch (code) {
            case 1:
                 db.insert(DBHelper.USERTABLE, null, values);
                 uri1 = Uri.withAppendedPath(uri, "user");
                Log.d("google-my:", "insert: uri1------->" + uri1);
                break;
        }

            return uri1;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    } // 4大组件都需要在Manifest里面注册 , 4个组件之一，provider
}

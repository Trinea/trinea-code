package cn.trinea.android.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.trinea.android.common.entity.HttpRequest;
import cn.trinea.android.common.entity.HttpResponse;
import cn.trinea.android.common.service.HttpCache;
import cn.trinea.android.common.service.HttpCache.HttpCacheListener;
import cn.trinea.android.common.util.CacheManager;
import cn.trinea.android.common.util.StringUtils;

/**
 * HttpCacheDemo
 * 
 * @author <a href="http://www.trinea.cn/android/android-http-cache/" target="_blank">Trinea</a> 2013-11-18
 */
public class HttpCacheDemo extends BaseActivity {

    public static final String TAG_CACHE = "http_cache";

    private EditText           httpUrlET;
    private Button             httpGetBT;
    private TextView           httpGetContentTV;
    private TextView           httpCacheInfoTV;

    private HttpCache          httpCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.http_cache_demo);

        // get the singleton instance of HttpCache
        httpCache = CacheManager.getHttpCache(context);
        // or create a new HttpCache, like this:
        // httpCache = new HttpCache(context);
        httpUrlET = (EditText)findViewById(R.id.http_cache_url);
        httpGetBT = (Button)findViewById(R.id.http_cache_get);
        httpGetContentTV = (TextView)findViewById(R.id.http_cache_content);
        httpCacheInfoTV = (TextView)findViewById(R.id.http_cache_info);
        httpGetBT.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = httpUrlET.getText().toString();
                url = StringUtils.isEmpty(url) ? httpUrlET.getHint().toString() : url;
                final String url2 = url;
                new Thread(new Runnable() {
                    
                    @Override
                    public void run() {
                        httpGet(url2);
                        httpGet2(url2);
                    }
                }).start();
//                httpCache.httpGet(url, new HttpCacheListener() {
//
//                    protected void onPreGet() {
//                        httpCacheInfoTV.setText("");
//                        httpGetContentTV.setText("wating…");
//                        httpGetBT.setEnabled(false);
//                    }
//
//                    protected void onPostGet(HttpResponse httpResponse, boolean isInCache) {
//                        if (httpResponse != null) {
//                            StringBuilder sb = new StringBuilder(256);
//                            sb.append("is in cache: ").append(isInCache).append("\r\n");
//                            if (isInCache) {
//                                sb.append("expires: ").append(new Date(httpResponse.getExpiredTime()).toGMTString())
//                                        .append("\r\n");
//                            }
//                            httpCacheInfoTV.setText(sb.toString());
//                            httpGetContentTV.setText(httpResponse.getResponseBody());
//                        } else {
//                            httpCacheInfoTV.setText("");
//                            httpGetContentTV.setText("response is null.");
//                        }
//                        httpGetBT.setEnabled(true);
//                    }
//                });
            }
        });
    }

    public static String httpGet(String url) {
        try {
            // 创建DefaultHttpClient对象
            HttpClient httpclient = new DefaultHttpClient();
            // 创建一个HttpGet对象
            HttpGet get = new HttpGet(url);
            get.setHeader(new BasicHeader("Accept-Encoding1", "gzip"));
            get.setHeader(new BasicHeader("Accept-Encoding", "gzip"));
            // 获取HttpResponse对象
            org.apache.http.HttpResponse response = httpclient.execute(get);
            // 判断是否链接成功
            if (response.getStatusLine().getStatusCode() == 200) {
                // 实体转换为字符串
                String content = EntityUtils.toString(response.getEntity(), "utf-8");
            } else {
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
    
    
    public static HttpResponse httpGet2(String urls) {
        if (urls == null) {
            return null;
        }

        BufferedReader input = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL(urls);
            try {
                // default gzip encode
                con = (HttpURLConnection)url.openConnection();
                con.setRequestProperty("Accept-Encoding", "false");
                con.setRequestProperty("Accept-Encoding", "false");
                input = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = input.readLine()) != null) {
                    sb.append(s).append("\n");
                }
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } finally {
            // close buffered
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // disconnecting releases the resources held by a connection so they may be closed or reused
            if (con != null) {
                con.disconnect();
            }
        }

        return null;
    }

}

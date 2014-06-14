package cn.trinea.android.test.httpcompare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Test HttpURLConnection and apache HttpClient
 * 
 * @author <a href="http://www.trinea.cn/" target="_blank">Trinea</a>
 * 
 */
public class MainActivity extends Activity {

    public static final String URL = "http://www.baidu.com/";

    private Button             urlConnectionBtn;
    private TextView           urlConnectionTV;

    private Button             httpClientBtn;
    private TextView           httpClientTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlConnectionBtn = (Button)findViewById(R.id.btn_use_url_connection);
        urlConnectionTV = (TextView)findViewById(R.id.tv_use_url_connection_response);
        httpClientBtn = (Button)findViewById(R.id.btn_use_http_client);
        httpClientTV = (TextView)findViewById(R.id.tv_http_client_response);

        urlConnectionBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AsyncTask<String, Void, String>() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        urlConnectionBtn.setClickable(false);
                        urlConnectionBtn.setText(R.string.wating);
                        urlConnectionTV.setText("");
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        urlConnectionBtn.setClickable(true);
                        urlConnectionBtn.setText(R.string.use_url_connection);
                        urlConnectionTV.setText(result);
                    }

                    @Override
                    protected String doInBackground(String... params) {
                        return httpGetUseHttpURLConnection(params[0]);
                    }
                }.execute(URL);
            }
        });
        httpClientBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AsyncTask<String, Void, String>() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        httpClientBtn.setClickable(false);
                        httpClientBtn.setText(R.string.wating);
                        httpClientTV.setText("");
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        httpClientBtn.setClickable(true);
                        httpClientBtn.setText(R.string.use_http_client);
                        httpClientTV.setText(result);
                    }

                    @Override
                    protected String doInBackground(String... params) {
                        return httpGetUseHttpClient(params[0]);
                    }
                }.execute(URL);;
            }
        });
    }

    /**
     * return response headers
     * 
     * @param urls
     * @return
     */
    public static String httpGetUseHttpURLConnection(String urls) {
        BufferedReader input = null;
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection)new URL(urls).openConnection();
            // default gzip encode, you can use below to close it
            // con.setRequestProperty("Accept-Encoding", "false");
            input = new BufferedReader(new InputStreamReader(con.getInputStream()));
            return con.getHeaderFields().toString();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

    /**
     * return response headers
     * 
     * @param url
     * @return
     */
    public static String httpGetUseHttpClient(String url) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            // no gzip encode default, you can use below to open it
            // get.setHeader(new BasicHeader("Accept-Encoding", "gzip"));
            HttpResponse response = httpclient.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                Header[] headers = response.getAllHeaders();
                StringBuilder sb = new StringBuilder();
                for (Header header : headers) {
                    sb.append(header.getName() + ":" + header.getValue() + ",");
                }
                return sb.toString();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

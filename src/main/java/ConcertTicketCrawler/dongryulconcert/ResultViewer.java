package ConcertTicketCrawler.dongryulconcert;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ResultViewer extends AppCompatActivity {

    ArrayList<Dictionary> mArrayList;
    CustomAdapter mAdapter;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_viewer);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();
        mRecyclerView.setAdapter(mAdapter);

        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('html')[0].innerHTML);");
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavascriptInterface(), "Android");
        webView.loadUrl("http://ticket.interpark.com/search/ticket.asp?search=%uBBF8%uC2A4%uD2B8%uB86F");

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                mArrayList = (ArrayList<Dictionary>) msg.obj;
                mAdapter = new CustomAdapter(mArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }
        };
    }

    public class MyJavascriptInterface{   // worked when javascript is detected(=webview)
        @JavascriptInterface
        public void getHtml(String html){
            Document doc = Jsoup.parse(html);
            System.out.println(doc.toString());
            Elements lines = doc.select("div[name=ticketplay_result]").select("td[class=btnArea] > a");
            ArrayList<String> urls = new ArrayList<>();
            for(Element l : lines){
                String url = l.attr("href");
                if(!urls.contains(url)){
                    urls.add(url);
                }
            }
            Message msg = handler.obtainMessage();   // create msg
            msg.obj = urls;   // add urls in msg
            handler.sendMessage(msg);   // send msg to handler
        }
    }
}

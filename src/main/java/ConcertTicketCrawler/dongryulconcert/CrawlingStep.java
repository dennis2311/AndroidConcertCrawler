package ConcertTicketCrawler.dongryulconcert;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CrawlingStep extends AppCompatActivity {

    private TextView source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crawling_step);

//        final Intent intent = new Intent(this, ResultViewer.class);
//        intent.putExtra("result_text","");

        source = (TextView)findViewById(R.id.source);
        new Crawling().execute();
    }

    private class Crawling extends AsyncTask<Void, Void, Void>{
        private ProgressDialog progressDialog;
//        @Override
//        protected void onPreExecute(){
//            super.onPreExecute();
//
//            progressDialog = new ProgressDialog(CrawlingStep.this);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setMessage("탐색 중입니다");
//            progressDialog.show();
//        }

        @Override
        protected Void doInBackground(Void... params) {
            try{
                Document doc = Jsoup.connect("http://ticket.interpark.com/search/ticket.asp?search=%uBBF8%uC2A4%uD2B8%uB86F").get();
                source.setText(doc.toString());

            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){

        }

    }


}



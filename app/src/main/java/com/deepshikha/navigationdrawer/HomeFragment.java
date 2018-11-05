package com.deepshikha.navigationdrawer;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.deepshikha.navigationdrawer.R.raw.soho;


public class HomeFragment extends Fragment {

    View view;
    TextView  tv_dishname, tv_description;
    ImageView iv_image;
    String str_name, str_disname, str_des, str_imagename, wv_title;
    WebView wv1;
    ProgressBar spinner;
//    SwipeRefreshLayout mySwipeRefreshLayout;
    String newurl;
    String[] separated;
    String newurl1;
    String host;
    String cre;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        view = inflater.inflate(R.layout.fragment_home, container, false);





        /* Call Activity for Voice Input */
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
//
//        try {
//            startActivityForResult(intent, 1);
//        } catch (ActivityNotFoundException a) {
//            Toast.makeText(getContext(), "Oops! Your device doesn't support Speech to Text",Toast.LENGTH_SHORT).show();
//        }
//








        init();
        return view;
    }

    private void init() {

        str_name = getArguments().getString("name");
        str_disname = getArguments().getString("dish");
        str_des = getArguments().getString("des");
        str_imagename = getArguments().getString("image");

        tv_dishname = (TextView) view.findViewById(R.id.tv_dishname);

        tv_description = (TextView) view.findViewById(R.id.tv_description);
        iv_image = (ImageView) view.findViewById(R.id.iv_image);
        wv1 = (WebView) view.findViewById(R.id.wv1) ;

        spinner = (ProgressBar)view.findViewById(R.id.progressBar1);
//        wv1.setWebViewClient(new CustomWebViewClient());

        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setDomStorageEnabled(true);
        wv1.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
//        wv1.loadUrl("https://www.google.com");


        wv1.getSettings().setJavaScriptEnabled(true);


        wv1.setWebViewClient(new MyBrowser());


        wv1.loadUrl(str_imagename);














//        mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
//
//        mySwipeRefreshLayout.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//
//                        wv1.reload();
//                        mySwipeRefreshLayout.setRefreshing(false);
//                    }
//
//                }
//
//        );



        newurl =  wv1.getUrl();
        String[] separated = newurl.split("/");
        newurl1 = separated[0]+'/'+separated[1]+'/'+separated[2]+'/'+separated[3]+'/';
        host = separated[0]+'/'+separated[1]+'/'+separated[2]+'/';
        cre = separated[4]+'/'+separated[5];




        final FloatingActionButton edit = (FloatingActionButton) view.findViewById(R.id.edit);

//        final FloatingActionButton home = (FloatingActionButton) view.findViewById(R.id.home);
//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                wv1.reload();
//
//
//
//
//            }
//        });


//        final FloatingActionButton refresh = (FloatingActionButton) view.findViewById(R.id.refresh);
//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//
//            }
//        });


        final FloatingActionButton add = (FloatingActionButton) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.soho);
                mp.start();
                wv1.reload();
//                Toast.makeText(getActivity(), newurl,
//                        Toast.LENGTH_LONG).show();
if(newurl1.equals(host+"viewaccountmaster/")){wv1.loadUrl(host+"accountsmaster/"+cre); edit.show();}
else if(newurl1.equals(host+"viewitemmaster/")){wv1.loadUrl(host+"itemmaster/"+cre); edit.show();}
else if(newurl1.equals(host+"viewtruck/")){wv1.loadUrl(host+"truck/"+cre); edit.show();}
else if(newurl1.equals(host+"viewusercreation/")){wv1.loadUrl(host+"usercreation/"+cre); edit.show();}

else if(newurl1.equals(host+"viewlslip/")){wv1.loadUrl(host+"lslip/"+cre); edit.show();}
else if(newurl1.equals(host+"viewbilltyentry/")){wv1.loadUrl(host+"billtyentry/"+cre); edit.show();}

else{
    add.hide();
    edit.hide();
//    home.hide();
//    refresh.hide();


}

                newurl =  wv1.getUrl();
                String[] separated = newurl.split("/");
                newurl1 = separated[0]+'/'+separated[1]+'/'+separated[2]+'/'+separated[3]+'/';
                host = separated[0]+'/'+separated[1]+'/'+separated[2]+'/';
                cre = separated[4]+'/'+separated[5];

//                Toast.makeText(getActivity(), newurl,
//                        Toast.LENGTH_LONG).show();


            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.soho);
                mp.start();
                wv1.reload();


                if(newurl1.equals("http://truckmaster.herokuapp.com/accountsmaster/")){wv1.loadUrl(host+"viewaccountmaster/"+cre);edit.hide();}
                else if(newurl1.equals("http://truckmaster.herokuapp.com/itemmaster/")){wv1.loadUrl(host+"viewitemmaster/"+cre); edit.hide();}
                else if(newurl1.equals("http://truckmaster.herokuapp.com/truck/")){wv1.loadUrl(host+"viewtruck/"+cre); edit.hide();}
                else if(newurl1.equals("http://truckmaster.herokuapp.com/usercreation/")){wv1.loadUrl(host+"viewusercreation/"+cre); edit.hide();}
                else if(newurl1.equals("http://truckmaster.herokuapp.com/lslip/")){wv1.loadUrl(host+"viewlslip/"+cre); edit.hide();}
                else if(newurl1.equals("http://truckmaster.herokuapp.com/billtyentry/")){wv1.loadUrl(host+"viewbilltyentry/"+cre); edit.hide();}

                else{

                    add.hide();
                    edit.hide();
//                    home.hide();
//                    refresh.hide();


                }

                newurl =  wv1.getUrl();
                String[] separated = newurl.split("/");
                newurl1 = separated[0]+'/'+separated[1]+'/'+separated[2]+'/'+separated[3]+'/';
                host = separated[0]+'/'+separated[1]+'/'+separated[2]+'/';
                cre = separated[4]+'/'+separated[5];

            }
        });


        // Click action
//                Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
//                startActivity(intent);
//        mySwipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
//                new ViewTreeObserver.OnScrollChangedListener() {
//                    @Override
//                    public void onScrollChanged() {
//                        if (wv1.getScrollY() == 0)
//                            mySwipeRefreshLayout.setEnabled(true);
//                        else
//                            mySwipeRefreshLayout.setEnabled(false);
//
//                    }
//                });


//        Toast.makeText(getActivity(), wv_title,
//                Toast.LENGTH_LONG).show();







        tv_dishname.setText(str_disname);
        tv_description.setText(str_des);






        int resourceImage = getActivity().getResources().getIdentifier(str_imagename, "drawable", getActivity().getPackageName());
        iv_image.setImageResource(resourceImage);


    }

    private class MyBrowser extends WebViewClient {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            spinner.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            spinner.setVisibility(View.GONE);
        }

    }
    /* When Mic activity close */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1: {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    String yourResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
                }
                break;
            }
        }
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv1.canGoBack()) {
//            wv1.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }



}

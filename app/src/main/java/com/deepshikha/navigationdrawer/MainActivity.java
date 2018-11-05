package com.deepshikha.navigationdrawer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Model_country> al_main = new ArrayList<>();
    ExpandableListView ev_list;
    CountryAdapter obj_adapter;
    String TAG = "MainActivity";
    String webtitle;
    private DrawerLayout mDrawerLayout;
    HomeFragment fragment;
    TextView tv_name;
    RelativeLayout rl_menu;
    String value;
    String value1;
    JSONObject jsonout;
    String user,pass,inventoryreports,driverreports,dashboardreports,adminpriviliges,maindriver,mainaccounts,maininventory,mainsitemanagement,maintrafficagent,maindashboard,dashboard,tm,ta,ls,be,pod,podp,r,lr,pur,job,mat,mati,scrap,mrr,mir,rip,pri,rec,rece,pay,con,jou,deb,cre,acc,dcr,mo,tb,bs,st,a,e,f,t,fre,site,tpassword;
    String demo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isNetworkConnectionAvailable();




        Intent intent = getIntent();
        value = intent.getStringExtra("key");
        value1 = intent.getStringExtra("key1");


        try {
            jsonout = new JSONObject(getIntent().getStringExtra("jsonin"));
            user = jsonout.getString(String.valueOf("name"));
            pass = jsonout.getString(String.valueOf("password"));

             dashboard = jsonout.getString(String.valueOf("dashboard"));
             tm = jsonout.getString(String.valueOf("tm"));
            ta = jsonout.getString(String.valueOf("ta"));
            ls = jsonout.getString(String.valueOf("ls"));
            be = jsonout.getString(String.valueOf("be"));
            pod = jsonout.getString(String.valueOf("pod"));
            podp = jsonout.getString(String.valueOf("podp"));
            r = jsonout.getString(String.valueOf("r"));
            lr = jsonout.getString(String.valueOf("lr"));
            pur = jsonout.getString(String.valueOf("pur"));
            job = jsonout.getString(String.valueOf("job"));
            mat = jsonout.getString(String.valueOf("mat"));
            scrap = jsonout.getString(String.valueOf("mati"));
            mir = jsonout.getString(String.valueOf("mrr"));
            pri = jsonout.getString(String.valueOf("rip"));
            rece = jsonout.getString(String.valueOf("rec"));
            con = jsonout.getString(String.valueOf("pay"));
            jou = jsonout.getString(String.valueOf("jou"));
            deb = jsonout.getString(String.valueOf("deb"));
            cre = jsonout.getString(String.valueOf("cre"));
            acc = jsonout.getString(String.valueOf("acc"));
            dcr = jsonout.getString(String.valueOf("dcr"));
            mo = jsonout.getString(String.valueOf("mo"));


            tb = jsonout.getString(String.valueOf("tb"));
            bs = jsonout.getString(String.valueOf("bs"));
            st = jsonout.getString(String.valueOf("st"));
            a = jsonout.getString(String.valueOf("a"));
            e = jsonout.getString(String.valueOf("e"));
            f = jsonout.getString(String.valueOf("f"));
            t = jsonout.getString(String.valueOf("t"));
            fre = jsonout.getString(String.valueOf("fre"));
            site = jsonout.getString(String.valueOf("site"));
            tpassword = jsonout.getString(String.valueOf("tpassword"));


            maindriver = jsonout.getString(String.valueOf("maindriver"));
            mainaccounts = jsonout.getString(String.valueOf("mainaccounts"));
            maininventory = jsonout.getString(String.valueOf("maininventory"));
            mainsitemanagement = jsonout.getString(String.valueOf("mainsitemanagement"));
            maintrafficagent = jsonout.getString(String.valueOf("maintrafficagent"));
            maindashboard = jsonout.getString(String.valueOf("maindashboard"));



            adminpriviliges = jsonout.getString(String.valueOf("adminpriviliges"));
            dashboardreports = jsonout.getString(String.valueOf("dashboardreports"));



            driverreports = jsonout.getString(String.valueOf("driverreports"));
            inventoryreports = jsonout.getString(String.valueOf("inventoryreports"));






            Toast.makeText(getApplicationContext(), dashboardreports.toString(),
                    Toast.LENGTH_LONG).show();


        } catch (JSONException e) {
//            e.printStackTrace();
        }




        fn_data();
        init();





    }

    private void init() {

        getSupportActionBar().hide();
        ev_list = (ExpandableListView) findViewById(R.id.ev_menu);
        tv_name = (TextView) findViewById(R.id.tv_name);
        rl_menu = (RelativeLayout) findViewById(R.id.rl_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        obj_adapter = new CountryAdapter(MainActivity.this, al_main);
        ev_list.setAdapter(obj_adapter);
        ev_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

        setExpandableListViewHeightBasedOnChildren(ev_list);

        fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", al_main.get(0).getStr_country());
        bundle.putString("des", al_main.get(0).getAl_state().get(0).getStr_description());
        bundle.putString("dish", al_main.get(0).getAl_state().get(0).getStr_name());
        bundle.putString("image", al_main.get(0).getAl_state().get(0).getStr_image());
        tv_name.setText(al_main.get(0).getStr_country());

        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, "HomeFragment").addToBackStack("null").commit();

        rl_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
       /* if (height < 10)
            height = 200;*/
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private void fn_data() {

        String str_data = loadJSONFromAsset();

        try {
            JSONObject jsonObject_country = new JSONObject(str_data);
            JSONArray jsonArray_country = jsonObject_country.getJSONArray("country");
            al_main = new ArrayList<>();
            for (int i = 0; i < jsonArray_country.length(); i++) {


                Model_country obj_country = new Model_country();
                JSONObject jsonObject = jsonArray_country.getJSONObject(i);
                JSONArray jsonArray_dishes = jsonObject.getJSONArray("dishes");
                ArrayList<Model_Dish> al_dishes = new ArrayList<>();
                for (int j = 0; j < jsonArray_dishes.length(); j++) {

                    JSONObject jsonObject_dishes = jsonArray_dishes.getJSONObject(j);
                    Model_Dish obj_dish = new Model_Dish();
                    obj_dish.setStr_name(jsonObject_dishes.getString("dishname"));
                    obj_dish.setStr_description(jsonObject_dishes.getString("description"));


                       obj_dish.setStr_image(jsonObject_dishes.getString("image")+'/'+user+'/'+pass);




//
//dashboard
if(maindashboard.equals("checked")&& i==1 && j==0 && dashboardreports.equals("checked"))
{
    al_dishes.add(obj_dish);
}
//accounts
                    else if(mainaccounts.equals("checked")&& i==5 && j==0 && pri.equals("checked"))
                    {
                        al_dishes.add(obj_dish);
                    }
//                  else  if(mainaccounts.equals("checked")&& i==5 && j==1 && rec.equals("checked"))
//                    {
//                        al_dishes.add(obj_dish);
//                    }
                  else  if(mainaccounts.equals("checked")&& i==5 && j==2 && rece.equals("checked"))
                    {
                        al_dishes.add(obj_dish);
                    }
// else if(mainaccounts.equals("checked")&& i==5 && j==3 && pay.equals("checked"))
//                    {
//                        al_dishes.add(obj_dish);
//                    }

 else if(mainaccounts.equals("checked")&& i==5 && j==4 && con.equals("checked"))
                    {
                        al_dishes.add(obj_dish);
                    }

 else if(mainaccounts.equals("checked")&& i==5 && j==5 && jou.equals("checked"))
                    {
                        al_dishes.add(obj_dish);
                    }
 else if(mainaccounts.equals("checked")&& i==5 && j==6 && deb.equals("checked"))
                    {
                        al_dishes.add(obj_dish);
                    }
else if(mainaccounts.equals("checked")&& i==5 && j==7 && cre.equals("checked"))
{
    al_dishes.add(obj_dish);
}
else if(mainaccounts.equals("checked")&& i==5 && j==8 && acc.equals("checked"))
{
    al_dishes.add(obj_dish);
}


//drivers


else if(maindriver.equals("checked")&& i==6 && j==0 && st.equals("checked"))
{
    al_dishes.add(obj_dish);
}

else if(maindriver.equals("checked")&& i==6 && j==1 && a.equals("checked"))
{
    al_dishes.add(obj_dish);
}

else if(maindriver.equals("checked")&& i==6 && j==2 && e.equals("checked"))
{
    al_dishes.add(obj_dish);
}

else if(maindriver.equals("checked")&& i==6 && j==3 && f.equals("checked"))
{
    al_dishes.add(obj_dish);
}
else if(maindriver.equals("checked")&& i==6 && j==4 && t.equals("checked"))
{
    al_dishes.add(obj_dish);
}
else if(maindriver.equals("checked")&& i==6 && j==5 && fre.equals("checked"))
{
    al_dishes.add(obj_dish);
}

else if(maindriver.equals("checked")&& i==6 && j==6 && driverreports.equals("checked"))
{
    al_dishes.add(obj_dish);
}


//inventory
else if(maininventory.equals("checked")&& i==4 && j==0 && pur.equals("checked"))
{
    al_dishes.add(obj_dish);
}
else if(maininventory.equals("checked")&& i==4 && j==1 && job.equals("checked"))
{
    al_dishes.add(obj_dish);
}
else if(maininventory.equals("checked")&& i==4 && j==2 && mat.equals("checked"))
{
    al_dishes.add(obj_dish);
}
//else if(maininventory.equals("checked")&& i==4 && j==3 && mati.equals("checked"))
//{
//    al_dishes.add(obj_dish);
//}
else if(maininventory.equals("checked")&& i==4 && j==4 && scrap.equals("checked"))
{
    al_dishes.add(obj_dish);
}
//else if(maininventory.equals("checked")&& i==4 && j==5 && mrr.equals("checked"))
//{
//    al_dishes.add(obj_dish);
//}
else if(maininventory.equals("checked")&& i==4 && j==6 && mir.equals("checked"))
{
    al_dishes.add(obj_dish);
}
else if(maininventory.equals("checked")&& i==4 && j==7 && inventoryreports.equals("checked"))
{
    al_dishes.add(obj_dish);
}

//sitemanagement

else if(mainsitemanagement.equals("checked")&& i==2 && j==0 && ls.equals("checked"))
{
    al_dishes.add(obj_dish);
}
else if(mainsitemanagement.equals("checked")&& i==2 && j==1 && be.equals("checked"))
{
    al_dishes.add(obj_dish);
}
else if(mainsitemanagement.equals("checked")&& i==2 && j==2 && pod.equals("checked"))
{
    al_dishes.add(obj_dish);
}
else if(mainsitemanagement.equals("checked")&& i==2 && j==3 && podp.equals("checked"))
{
    al_dishes.add(obj_dish);
}
else if(mainsitemanagement.equals("checked")&& i==2 && j==4 && r.equals("checked"))
{
    al_dishes.add(obj_dish);
}
else if(mainsitemanagement.equals("checked")&& i==2 && j==5 && lr.equals("checked"))
{
    al_dishes.add(obj_dish);
}
//admin priviliges

else if(adminpriviliges.equals("checked")&& i==8 && j==0)
{
    al_dishes.add(obj_dish);
}


else if(adminpriviliges.equals("checked")&& i==8 && j==1)
{
    al_dishes.add(obj_dish);
}
else if(adminpriviliges.equals("checked")&& i==8 && j==2)
{
    al_dishes.add(obj_dish);
}
else if(adminpriviliges.equals("checked")&& i==8 && j==3)
{
    al_dishes.add(obj_dish);
}
else if(adminpriviliges.equals("checked")&& i==8 && j==4)
{
    al_dishes.add(obj_dish);
}
else if(adminpriviliges.equals("checked")&& i==8 && j==5)
{
    al_dishes.add(obj_dish);
}
else if(adminpriviliges.equals("checked")&& i==8 && j==6)
{
    al_dishes.add(obj_dish);
}

//settings






//                    al_dishes.add(obj_dish);


                }

                obj_country.setAl_state(al_dishes);



                obj_country.setStr_country(jsonObject.getString("name"));

if(maindashboard.equals("checked")) {
    if (i == 1) {
        al_main.add(obj_country);
    }
}


                 if(mainaccounts.equals("checked")) {
                    if (i == 5) {
                        al_main.add(obj_country);
                    }
                }

                 if(maindriver.equals("checked")) {
                    if (i == 6) {
                        al_main.add(obj_country);
                    }
                }
                if(maininventory.equals("checked")) {
                    if (i == 4) {
                        al_main.add(obj_country);
                    }
                }

                if(mainsitemanagement.equals("checked")) {
                    if (i == 2) {
                        al_main.add(obj_country);
                    }
                }

                if(maintrafficagent.equals("checked")) {
                    if (i == 3) {
                        al_main.add(obj_country);
                    }
                }


                if(adminpriviliges.equals("checked")) {
                    if (i == 8) {
                        al_main.add(obj_country);
                    }
                }
                if (i == 9) {
                    al_main.add(obj_country);
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void setExpandableListViewHeightBasedOnChildren(ExpandableListView expandableListView) {
        CountryAdapter adapter = (CountryAdapter) expandableListView.getExpandableListAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = expandableListView.getPaddingTop() + expandableListView.getPaddingBottom();
        for (int i = 0; i < adapter.getGroupCount(); i++) {



            View groupItem = adapter.getGroupView(i, false, null, expandableListView);
            groupItem.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            totalHeight += groupItem.getMeasuredHeight();

            if (expandableListView.isGroupExpanded(i)) {
                for (int j = 0; j < adapter.getChildrenCount(i); j++) {


                    View listItem = adapter.getChildView(i, j, false, null, expandableListView);
                    listItem.setLayoutParams(new ViewGroup.LayoutParams(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED));
                    listItem.measure(View.MeasureSpec.makeMeasureSpec(0,
                            View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                            .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = expandableListView.getLayoutParams();
        int height = totalHeight + expandableListView.getDividerHeight() * (adapter.getGroupCount() - 1);

        if (height < 10)
            height = 100;
        params.height = height;
        expandableListView.setLayoutParams(params);
        expandableListView.requestLayout();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {




            InputStream is = getAssets().open("dishes.json");




            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        Log.e(TAG, "Json response " + json);
        return json;

    }

    public void fn_selectedPosition(int group, int child) {

        fragment = new HomeFragment();
        Bundle bundle = new Bundle();



        bundle.putString("name", al_main.get(group).getAl_state().get(child).getStr_name());


        bundle.putString("des", al_main.get(group).getAl_state().get(child).getStr_description());
        bundle.putString("dish", al_main.get(group).getAl_state().get(child).getStr_name());
        bundle.putString("image", al_main.get(group).getAl_state().get(child).getStr_image());
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, "HomeFragment").addToBackStack("null").commit();
        mDrawerLayout.closeDrawer(Gravity.LEFT);



        tv_name.setText(al_main.get(group).getAl_state().get(child).getStr_name());




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            //add the function to perform here
            return(true);
        case R.id.reset:
            //add the function to perform here
            return(true);
        case R.id.about:
            //add the function to perform here
            return(true);
        case R.id.exit:
            //add the function to perform here
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }



    public static class AppWebViewClients extends WebViewClient {
        private ProgressBar progressBar;

        public AppWebViewClients(ProgressBar progressBar) {
            this.progressBar=progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }



//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            switch (keyCode) {
//                case KeyEvent.KEYCODE_BACK:
//                    if (wv1.canGoBack()) {
//                        wv1.goBack();
//                    } else {
//
////                        finish();
//                    }
//                    return true;
//            }
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (fragment.wv1.canGoBack()) {
                                fragment.wv1.goBack();
                            } else {


                                new AlertDialog.Builder(this)
                                        .setMessage("Are you sure you want to exit?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                MainActivity.this.finish();
                                            }
                                        })
                                        .setNegativeButton("No", null)
                                        .show();


//                                finish();
                            }
                            return true;
                    }

                }
                return super.onKeyDown(keyCode, event);
            }


    public void checkNetworkConnection(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }
//    @Override
//    public void onBackPressed() {
//
//        super.onBackPressed();
//        finish();
//    }
}

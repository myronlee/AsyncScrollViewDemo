package com.example.myronlg.asyncscrollviewdemo;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TextView testTextView = (TextView) findViewById(R.id.tv_test);
//        testTextView.setOnClickListener(this);
//        ScrollView scrollView = (ScrollView) findViewById(R.id.sv);
//        button = (Button) findViewById(R.id.button11);
//        scrollView.requestChildFocus(button, button);
//        AsyncScrollView asyncScrollView = (AsyncScrollView) findViewById(R.id.asv);
//        View copyAnchorView = findViewById(R.id.tv_anchor_copy);
//        asyncScrollView.setCopyAnchorView(copyAnchorView);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp);

        viewPager.setAdapter(new MyPagerAdapter());


        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tv_anchor);
        tabs.setShouldExpand(true);
        tabs.setViewPager(viewPager);

        findViewById(R.id.potrait).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_LONG).show();
            }
        });
    }

    class MyPagerAdapter extends PagerAdapter {

        private CharSequence[] titles = {"tab a", "tab b", "tab c"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View page = inflater.inflate(R.layout.page, container, false);
            page.setTag(position);
            container.addView(page);
            return page;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Rect rect = new Rect();
            button.getDrawingRect(rect);
            Log.e("", rect.toString());
            button.getHitRect(rect);
            Log.e("", rect.toString());
            Log.e("", button.getTop() + "");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, R.string.app_name, Toast.LENGTH_LONG).show();
    }
}

package tentrep.karinderya;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class userstar extends stars {

    TextView tv, star1tv, star2tv, star3tv, starave,tv1,tv2,tv3,tv4,tv5, taste;
    String s1="", s2="", s3="", save="";
    Button b;
    RatingBar rb1,rb2,rb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userstar);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Quicksand-Regular.otf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "Quicksand-Bold.otf");

        star1tv = (TextView)findViewById(R.id.star1tv);
        star2tv = (TextView)findViewById(R.id.star2tv);
        star3tv = (TextView)findViewById(R.id.star3tv);
        tv1 = (TextView)findViewById(R.id.textView);
        tv2 = (TextView)findViewById(R.id.textView2);
        tv3 = (TextView)findViewById(R.id.textView3);
        tv4 = (TextView)findViewById(R.id.textView4);
        tv5 = (TextView)findViewById(R.id.textView5);
        starave = (TextView)findViewById(R.id.starave);
        rb1 = (RatingBar)findViewById(R.id.tastebar);
        rb2 = (RatingBar)findViewById(R.id.sanitationbar);
        rb3 = (RatingBar)findViewById(R.id.menubar);

        taste = (TextView)findViewById(R.id.taste);

        star1tv.setTypeface(tf);
        star2tv.setTypeface(tf);
        star3tv.setTypeface(tf);

        tv1.setTypeface(tf);
        tv2.setTypeface(tf);
        tv3.setTypeface(tf);
        tv4.setTypeface(tf2);
        tv5.setTypeface(tf2);

        Bundle bundle2 = getIntent().getExtras();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.7), (int)(height*0.53));

        if(bundle2!=null){
            s1 = bundle2.getString("star1");
            s2 = bundle2.getString("star2");
            s3 = bundle2.getString("star3");
            save = bundle2.getString("avestar");
        }
        starave.setText(save);
        star1tv.setText(s1);
        star2tv.setText(s2);
        star3tv.setText(s3);
    }
    public void getrating(View view){
        taste.setText(String.valueOf(rb1.getRating() ));

    }
}

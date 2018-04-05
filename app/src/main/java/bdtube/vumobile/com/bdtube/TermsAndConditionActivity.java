package bdtube.vumobile.com.bdtube;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import bdtube.vumobile.com.bdtube.Model.SharedPref;

public class TermsAndConditionActivity extends AppCompatActivity {

    private TextView txtTermsAndCondition;
    private ImageView imgGif;
    private WebView webView;
    private TextView txtFooterVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_and_condition_layout);

        imgGif = findViewById(R.id.imgGif);


        webView = findViewById(R.id.webview);
        // set web view client
// string url which you have to load into a web view
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://vumobile.biz/apps/bdtubeterms.html");

        Glide.with(this).load(R.mipmap.splash).into(imgGif);

        txtTermsAndCondition = findViewById(R.id.txtTermsAndCondition);
//        txtTermsAndCondition.setText(Html.fromHtml(getString(R.string.terms)));
//        txtTermsAndCondition.setMovementMethod(LinkMovementMethod.getInstance());
//        txtTermsAndCondition.setLinkTextColor(Color.RED);

        txtTermsAndCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browser= new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1KFBE5lqi3TK0FDNzZWnsSSC17FgzfeE0QS9Qis9v9tA/edit?usp=sharing"));
                startActivity(browser);
            }
        });
    }

    public void btnAccept(View view) {

        new SharedPref(this).acceptTermsAndCond(true);

        startActivity(new Intent(TermsAndConditionActivity.this, MainActivity.class));
        finish();

    }
}

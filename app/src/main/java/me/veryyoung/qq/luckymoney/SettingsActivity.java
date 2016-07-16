package me.veryyoung.qq.luckymoney;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static android.preference.Preference.*;

public class SettingsActivity extends AppCompatActivity {

    private SettingsFragment mSettingsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (savedInstanceState == null) {
            mSettingsFragment = new SettingsFragment();
            replaceFragment(R.id.settings_container, mSettingsFragment);
        }

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void replaceFragment(int viewId, android.app.Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }


    /**
     * A placeholder fragment containing a settings view.
     */
    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
            addPreferencesFromResource(R.xml.pref_setting);

            Preference donateAlipay = findPreference("donate_alipay");
            donateAlipay.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference pref) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    String payUrl = "https://qr.alipay.com/apbvye346u4wqkcr9b";
                    intent.setData(Uri.parse("alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + payUrl));
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        intent.setData(Uri.parse(payUrl));
                        startActivity(intent);
                    }
                    return true;
                }
            });

            Preference donateWechat = findPreference("donate_wechat");

            donateWechat.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference pref) {
                    Intent intent = new Intent();
                    intent.setClassName("com.tencent.mm", "com.tencent.mm.plugin.remittance.ui.RemittanceUI");
                    intent.putExtra("fee", 10.0d);
                    intent.putExtra("scan_remittance_id", "000035581875571458005685");
                    intent.putExtra("receiver_true_name", "************");
                    intent.putExtra("scene", 1);
                    intent.putExtra("pay_channel", 12);
                    intent.putExtra("pay_scene", 32);
                    intent.putExtra("receiver_name", "wxid_8296782967722");
                    startActivity(intent);
//                    Bitmap payImage = BitmapFactory.decodeResource(getResources(), R.drawable.wechat_pay);
//
//                    Dialog builder = new Dialog(getActivity());
//                    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    builder.getWindow().setBackgroundDrawable(
//                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                    ImageView imageView = new ImageView(getActivity());
//                    imageView.setImageBitmap(payImage);
//                    builder.addContentView(imageView, new RelativeLayout.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT));
//                    builder.show();
//
                    return true;
                }
            });

        }
    }
}

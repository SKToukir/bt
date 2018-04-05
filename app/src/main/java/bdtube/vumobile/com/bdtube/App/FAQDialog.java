package bdtube.vumobile.com.bdtube.App;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import bdtube.vumobile.com.bdtube.R;

/**
 * Created by toukirul on 16/10/2017.
 */

public class FAQDialog {
    TextView txtFaq;

    public void FUCK(final Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_faq);


        // Toast.makeText(context,  allCommentLists.get(0).getValue(), Toast.LENGTH_LONG).show();

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);

        Button btnColse = (Button) dialog.findViewById(R.id.btnColse);
        btnColse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        lp.x = ViewGroup.LayoutParams.MATCH_PARENT; // The new position of the X coordinates
        lp.y = 0; // The new position of the Y coordinates
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT; // Width
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT; // Height
        lp.alpha = 0.9f; // Transparency

        dialogWindow.setAttributes(lp);

        dialog.show();

    }

    public void FUCK(final Context context, String operatorType) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_faq);
        txtFaq = (TextView) dialog.findViewById(R.id.txtFaq);

        // 1 is for ROBI
        if (operatorType.equals("1")) {
            txtFaq.setText("BDTUBE একটি ওয়াপ এবং App ভিত্তিক সেবা যার মাধ্যমে গ্রাহক বাংলা চলচ্চিত্র, স্বল্পদৈর্ঘ্য চলচ্চিত্র, মুভি ক্লিপ্স, মিউজিক, ফান ভিডিও, নাটক, টেলিফিল্ম, ভিডিও গানের স্ট্রিমিং দেখতে পারবেন। সাবস্ক্রিপশনের ভিত্তিতে এই সেবা পাওয়া যাবে। \n" +
                    "● সাবস্ক্রিপশনের ভিত্তিতে গ্রাহক দৈনিক সকল কনটেন্ট  ফ্রি দেখতে পারবেন। \n" +
                    "● সাবস্ক্রিপশন ফি হবে দৈনিক ২ টাকা (+ভ্যাট+এসডি+এসসি)। \n" +
                    "● BDTUBE এ নিবন্ধন করার পরে গ্রাহককে তার ড্যাটা সংযোগ সক্রিয় রাখতে হবে, যাতে তার কাঙ্খিত কনটেন্ট দেখতে পারেন। \n" +
                    "\n" +
                    "কিভাবে সেবাটি চালু করবেন: \n" +
                    "● APP : যে কোনো কনটেন্ট এ ক্লিক করুন, এবং নিবন্ধন বাটনটি ক্লিক করুন। \n" +
                    "\n" +
                    "কিভাবে সেবাটি বন্ধ করবেন: \n" +
                    "●এসএমএস: STOP লিখে 2128304 নম্বরে পাঠিয়ে দিন\n" +
                    " \n" +
                    "\n" +
                    "সংযোগের জন্যে কিছু নির্দেশনা দেওয়া হলো: \n" +
                    "●আপনার মোবাইলে রবি ইন্টারনেট (ওয়াপ/ইন্টারনেট) সেটআপ থাকতে হবে।\n" +
                    "●রবি প্রিপেইড/পোস্টপেইড সংযোগের ক্ষেত্রে আপনার এ্যাকাউন্টে যথেষ্ট পরিমাণ ক্রেডিট থাকতে হবে। \n" +
                    "দ্রষ্টব্য:  ভিডিও স্ট্রিমিং এর জন্য ড্যাটা চার্জ প্রযোজ্য হবে | \n" +
                    "\n" +
                    "গ্রাহক সেবা:\n" +
                    "কাস্টমার কেয়ার: 8801814426426 সকাল ৮ টা থেকে সন্ধ্যা ৭ টা পর্যন্ত)।\n" +
                    "●ইমেইল : support@vumobile.biz\n");
            // 2 is for BL
        } else if (operatorType.equals("2")) {
            txtFaq.setText("BDTUBE\n" +
                    "BDTUBE একটি ওয়াপ এবং App  ভিত্তিক সেবা যেখান থেকে গ্রাহক বাংলা চলচ্চিত্র, স্বল্পদৈর্ঘ্য চলচ্চিত্র,মুভি ক্লিপ্স, মিউজিক, ফান ভিডি, নাটক, টেলিফিল্ম, ভিডিও গানের স্ট্রিমিং দেখতে পারবেন । সাবস্ক্রিপশনের ভিত্তিতে অনুযায়ী এই সেবা পাওয়া যাবে। \n" +
                    "● সাবস্ক্রিপশনের ভিত্তিতে গ্রাহক দৈনিক ৫টি কনটেন্ট ফ্রি দেখতে পারবেন । \n" +
                    "● সাবস্ক্রিপশন ফি হবে দৈনিক ২ টাকা (+ ভ্যাট+এসডি ও এস সি )। \n" +
                    "● BDTUBE এ নিবন্ধন করার পরে গ্রাহককে তার ড্যাটা সংযোগ সক্রিয় রাখতে হবে, যাতে তার কাঙ্খিত কনটেন্ট দেখতে পারেন। \n" +
                    "● দৈনিক ৫টি ফ্রি কনটেন্ট দেখার পর গ্রাহক আবার ২ টাকা (+ ভ্যাট+এসডি ও এস সি )চার্জ্ দিয়ে অতিরিক্ত ৫টি কনটেন্ট দেখতে পারবেন | \n" +
                    "● ফুল মুভির জন্যে ২০ টাকা (+এসডি + ভ্যাট + সারচার্জ )/ কনটেন্ট । \n" +
                    "\n" +
                    "কিভাবে সেবাটি চালু করবেন: \n" +
                    "● এসএমএস: START BDTUBE লিখে 6624 নম্বরে পাঠান। \n" +
                    "●App: যে কোনো কনটেন্ট এ ক্লিক করুন, এবং যোগ দিন বাটনটি ক্লিক করুন। \n" +
                    "\n" +
                    "কিভাবে সেবাটি বন্ধ করবেন: \n" +
                    "●এসএমএস: STOP BDTUBE লিখে 6624 নম্বরে পাঠিয়ে দিন।\n" +
                    "\n" +
                    "সংযোগের জন্যে কিছু নির্দেশনা দেওয়া হলো: \n" +
                    "●আপনার মোবাইলে বাংলালিংক ইন্টারনেট (ওয়াপ/ইন্টারনেট) সেটআপ থাকতে হবে।\n" +
                    "●বাংলালিংক প্রিপেইড/পোস্টপেইড সংযোগের ক্ষেত্রে আপনার এ্যাকাউন্টে যথেষ্ট পরিমাণ ক্রেডিট থাকতে হবে। \n" +
                    "\n" +
                    "দ্রষ্টব্য: ভিডিও স্ট্রিমিং এর জন্য ড্যাটা চার্জ প্রযোজ্য হবে | \n" +
                    "\n" +
                    "গ্রাহক সেবা:\n" +
                    "কাস্টমার কেয়ার: 8801992303765 সকাল ৮ টা থেকে সন্ধ্যা ৭ টা পর্যন্ত)।\n" +
                    "●ইমেইল : support@vumobile.biz\n");
            // 3 is for TT
        } else if (operatorType.equals("3")) {
            txtFaq.setText("BDTUBE\n" +
                    "BDTUBE একটি ওয়াপ এবং App  ভিত্তিক সেবা  যেখান  থেকে গ্রাহক বাংলা চলচ্চিত্র, স্বল্পদৈর্ঘ্য চলচ্চিত্র,মুভি ক্লিপ্স, মিউজিক,  ফানি ভিডি, নাটক, টেলিফিল্ম, ভিডিও গানের স্ট্রিমিং দেখতে পারবেন । সাবস্ক্রিপশনের ভিত্তিতে অনুযায়ী এই সেবা পাওয়া যাবে। \n" +
                    "● সাবস্ক্রিপশনের ভিত্তিতে গ্রাহক দৈনিক ৫টি কনটেন্ট ফ্রি দেখতে পারবেন । \n" +
                    "● সাবস্ক্রিপশন ফি হবে  দৈনিক ২ টাকা (+ ভ্যাট+এসডি ও এস সি )। \n" +
                    "● BDTUBE এ নিবন্ধন করার পরে গ্রাহককে তার ড্যাটা সংযোগ সক্রিয় রাখতে হবে, যাতে তার কাঙ্খিত কনটেন্ট দেখতে পারেন। \n" +
                    "● দৈনিক ৫টি ফ্রি কনটেন্ট দেখার পর গ্রাহক আবার ২ টাকা (+ ভ্যাট+এসডি ও এস সি )চার্জ্ দিয়ে অতিরিক্ত ৫টি কনটেন্ট দেখতে পারবেন | \n" +
                    "● মুভি ভিডিও’র জন্যে ১০ টাকা (+এসডি + ভ্যাট + সারচার্জ )। \n" +
                    "\n" +
                    "কিভাবে সেবাটি চালু করবেন: \n" +
                    "● এসএমএস:  START BDTUBE লিখে 6624 নম্বরে পাঠান। \n" +
                    "● App:  যে কোনো কনটেন্ট এ ক্লিক করুন , এবং যোগ দিন বাটনটি ক্লিক করুন। \n" +
                    "\n" +
                    "কিভাবে সেবাটি বন্ধ করবেন: \n" +
                    "●এসএমএস: STOP BDTUBE লিখে 6624 নম্বরে পাঠিয়ে দিন।\n" +
                    "\n" +
                    "সংযোগের জন্যে কিছু নির্দেশনা দেওয়া হলো: \n" +
                    "●আপনার মোবাইলে টেলিটক ইন্টারনেট (ওয়াপ/ইন্টারনেট) সেটআপ থাকতে হবে।\n" +
                    "●টেলিটক প্রিপেইড/পোস্টপেইড সংযোগের ক্ষেত্রে আপনার এ্যাকাউন্টে যথেষ্ট পরিমাণ ক্রেডিট থাকতে হবে। \n" +
                    "\n" +
                    "দ্রষ্টব্য:  ভিডিও স্ট্রিমিং এর জন্য ড্যাটা চার্জ প্রযোজ্য হবে | \n" +
                    "\n" +
                    "গ্রাহক সেবা:\n" +
                    "কাস্টমার কেয়ার: 8801534524714 সকাল ৮ টা থেকে সন্ধ্যা ৭ টা পর্যন্ত)।\n" +
                    "● ইমেইল : support@vumobile.biz\n");
        } else if (operatorType.equals("4")){
            txtFaq.setText("BDTUBE\n" +
                    "BDTUBE একটি ওয়াপ এবং App  ভিত্তিক সেবা  যেখান  থেকে গ্রাহক বাংলা চলচ্চিত্র, স্বল্পদৈর্ঘ্য চলচ্চিত্র,মুভি ক্লিপ্স, মিউজিক,  ফানি ভিডি, নাটক, টেলিফিল্ম, ভিডিও গানের স্ট্রিমিং দেখতে পারবেন । সাবস্ক্রিপশনের ভিত্তিতে অনুযায়ী এই সেবা পাওয়া যাবে। \n" +
                    "● সাবস্ক্রিপশনের ভিত্তিতে গ্রাহক দৈনিক ৫টি কনটেন্ট ফ্রি দেখতে পারবেন । \n" +
                    "● সাবস্ক্রিপশন ফি হবে  দৈনিক ২ টাকা (+ ভ্যাট+এসডি ও এস সি )। \n" +
                    "● BDTUBE এ নিবন্ধন করার পরে গ্রাহককে তার ড্যাটা সংযোগ সক্রিয় রাখতে হবে, যাতে তার কাঙ্খিত কনটেন্ট দেখতে পারেন। \n" +
                    "● দৈনিক ৫টি ফ্রি কনটেন্ট দেখার পর গ্রাহক আবার ২ টাকা (+ ভ্যাট+এসডি ও এস সি )চার্জ্ দিয়ে অতিরিক্ত ৫টি কনটেন্ট দেখতে পারবেন | \n" +
                    "● মুভি ভিডিও’র জন্যে ১০ টাকা (+এসডি + ভ্যাট + সারচার্জ )। \n" +
                    "\n" +
                    "কিভাবে সেবাটি চালু করবেন: \n" +
                    "● এসএমএস:  START BDTUBE লিখে 6624 নম্বরে পাঠান। \n" +
                    "● App:  যে কোনো কনটেন্ট এ ক্লিক করুন , এবং যোগ দিন বাটনটি ক্লিক করুন। \n" +
                    "\n" +
                    "কিভাবে সেবাটি বন্ধ করবেন: \n" +
                    "●এসএমএস: STOP BDTUBE লিখে 6624 নম্বরে পাঠিয়ে দিন।\n" +
                    "\n" +
                    "সংযোগের জন্যে কিছু নির্দেশনা দেওয়া হলো:\n" +
                    "●আপনার মোবাইলে এয়ারটেল ইন্টারনেট (ওয়াপ/ইন্টারনেট) সেটআপ থাকতে হবে।\n" +
                    "●• এয়ারটেল প্রিপেইড/পোস্টপেইড সংযোগের ক্ষেত্রে আপনার এ্যাকাউন্টে যথেষ্ট পরিমাণ ক্রেডিট থাকতে হবে।\n" +
                    "\n" +
                    "দ্রষ্টব্য: পোর্টাল ব্রাউজিং ও ভিডিও স্ট্রিমিং এর জন্য ড্যাটা চার্জ প্রযোজ্য হবে |\n" +
                    "\n" +
                    "গ্রাহক সেবা:\n" +
                    "কাস্টমার কেয়ার: 8801674985965 সকাল ৮ টা থেকে সন্ধ্যা ৭ টা পর্যন্ত।\n" +
                    "●ইমেইল : support@vumobile.biz\n");
        }else {
            txtFaq.setText(R.string.dialog_faq);
        }


        // Toast.makeText(context,  allCommentLists.get(0).getValue(), Toast.LENGTH_LONG).show();

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);

        Button btnColse = (Button) dialog.findViewById(R.id.btnColse);
        btnColse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        lp.x = ViewGroup.LayoutParams.MATCH_PARENT; // The new position of the X coordinates
        lp.y = 0; // The new position of the Y coordinates
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT; // Width
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT; // Height
        lp.alpha = 0.9f; // Transparency

        dialogWindow.setAttributes(lp);

        dialog.show();

    }
}
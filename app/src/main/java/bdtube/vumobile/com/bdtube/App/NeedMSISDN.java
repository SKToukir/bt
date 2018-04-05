package bdtube.vumobile.com.bdtube.App;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import bdtube.vumobile.com.bdtube.R;

/**
 * Created by toukirul on 16/10/2017.
 */

public class NeedMSISDN {

    public void NeedMsisdnDialog(Context context){
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.msisdn_wirless_form);

        dialog.setCanceledOnTouchOutside(true);
        Button dialogButton = (Button) dialog.findViewById(R.id.btnOk);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}


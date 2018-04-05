package bdtube.vumobile.com.bdtube.App;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import bdtube.vumobile.com.bdtube.R;

/**
 * Created by toukirul on 16/10/2017.
 */

public class HelpDialog {

    public void Help(final Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_help);

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
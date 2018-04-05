package bdtube.vumobile.com.bdtube.offline;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import bdtube.vumobile.com.bdtube.db.DBHandler;
import bdtube.vumobile.com.bdtube.db.DbModelClass;


/**
 * Created by toukir on 3/23/18.
 */

public class DownloadTask {

    private DBHandler dbHandler;
    private DbModelClass dbModelClass;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    //initialize our progress dialog/bar
    private ProgressDialog pDialog;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    //initialize root directory
    File rootDir = Environment.getExternalStorageDirectory();
    //defining file name and url
    public String videoFileName, physicalFileName;
    private Context context;
    private String downloadUrl, imageDownloadPath, videoDownloadPath, imageFileName;

    public DownloadTask(Context context, String downloadUrl, String imageDownloadPath, String imageFileName, String physicalFileName) {
        this.context = context;
        this.downloadUrl = downloadUrl;
        this.imageDownloadPath = imageDownloadPath;
        this.imageFileName = imageFileName;
        this.physicalFileName = physicalFileName;

        videoFileName = physicalFileName+".mp4";
        //making sure the download directory exists
        checkAndCreateDirectory("/my_downloads");

        if (fileExists(imageDownloadPath+"/"+imageFileName)){
            Log.d("Response","Image exists");
            //Start Downloading Task
            new DownloadingTask().execute();
        }else {
            Toast.makeText(context,"Try again!", Toast.LENGTH_LONG).show();
        }
    }


    private class DownloadingTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context,"Started", Toast.LENGTH_LONG).show();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected void onPostExecute(String result) {
           Toast.makeText(context,"Download finished", Toast.LENGTH_LONG).show();
            pDialog.dismiss();
            if (fileExists(videoDownloadPath)){
                dbHandler = new DBHandler(context);
                dbModelClass = new DbModelClass();

                dbModelClass.setImageFileName(imageFileName);
                dbModelClass.setVideoFileName(videoFileName);
                dbModelClass.setVideoFilePath(videoDownloadPath);
                dbModelClass.setImageFilePath(imageDownloadPath);

                dbHandler.addItem(dbModelClass);

                Log.d("Response","Video File exists \n "+imageDownloadPath+"/"+imageFileName+"\n"+videoDownloadPath+"\n"+imageFileName+"\n"+videoFileName);
            }else {
                Log.d("Response","Try again");
            }
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
                //connecting to url
                URL u = new URL(downloadUrl);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();

                //lenghtOfFile is used for calculating download progress
                int lenghtOfFile = c.getContentLength();

                //this is where the file will be seen after the download
                FileOutputStream f = new FileOutputStream(new File(rootDir + "/my_downloads/", videoFileName));
                //file input is from the url
                InputStream in = c.getInputStream();

                //here’s the download code
                byte[] buffer = new byte[1024];
                int len1 = 0;
                long total = 0;

                while ((len1 = in.read(buffer)) > 0) {
                    total += len1; //total = total + len1
                    pDialog.setProgress((int)((total*100)/lenghtOfFile));
                    publishProgress(String.valueOf((int)((total*100)/lenghtOfFile)));
                    Log.d("Resopnse", String.valueOf((int)((total*100)/lenghtOfFile)));
                    f.write(buffer, 0, len1);
                }
                f.close();

            } catch (Exception e) {
                Log.d("Response", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            pDialog.setProgress(Integer.parseInt(values[0]));
        }
    }

    private void showDialog(int dialogDownloadProgress) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Downloading file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(false);
        pDialog.show();
        Log.d("Response", String.valueOf(dialogDownloadProgress));
    }

    //function to verify if directory exists
    public void checkAndCreateDirectory(String dirName){
        File new_dir = new File(rootDir + dirName );
        if( !new_dir.exists() ){
            new_dir.mkdirs();
        }
        videoDownloadPath = new_dir.getPath()+"/"+ videoFileName;
        Log.d("Response",new_dir.getPath()+"/"+ videoFileName);
    }

    private boolean fileExists(String filePath){
        File file = new File(filePath);
        if (file.exists()){
            return true;
        }else {
            return false;
        }
    }
}

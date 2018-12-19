package tcpdemo.apple.example.com.htmlpage;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;


public class MainActivity extends Activity {
    Bitmap bitmap;
    boolean boolean_save;

    WebView wv;
    Button btn;
    LinearLayout ll_pdflayout;

    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    ProgressDialog progressDialog;
    String k;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fn_permission();

        LinearLayout ll = (LinearLayout) findViewById(R.id.rl);
        wv = (WebView) findViewById(R.id.wv);
        btn = (Button) findViewById(R.id.btn);
        ll_pdflayout = (LinearLayout) findViewById(R.id.ll_pdflayout);


        wv.setWebViewClient(new WebViewClient());

        WebSettings webSetting = wv.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);

        exportpdf();
        preparePDF();

        // Set a click listener for Button widget
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boolean_permission) {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Please wait");
                    bitmap = loadBitmapFromView(ll_pdflayout, ll_pdflayout.getWidth(), ll_pdflayout.getHeight());
                    createPdf();

                } else {

                }
                createPdf();


            }
        });
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {

        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    public void exportpdf() {
        try {
            // String image="file:///android_asset/", "<img src='file.jpg' />", "text/html", "utf-8", null;
            String sHtmlTemplate = "<html><head></head><body><img src=\"file:///android_asset/ic_launcher.png\"><p>Hello Webview.</p></body></html>";

            k = "<html>\n" +
                    "<head>\n" +
                    "    <title>SpectroChip</title>\n" +
                    "</head>\n" +
                    "<body style=\"font-family: arial;height: 100%;width: 100%;padding: 0;margin: 0\">\n" +
                    "<table id=\"customers\" border=\"0\" cellspacing=\"0\" cellpadding=\"0px\" width=\"100%\"\n" +
                    "       style=\"font-family: &quot;Trebuchet MS&quot;, Arial, Helvetica, sans-serif;display: inline-table;border-collapse: collapse;width: 100%;margin-left: 0;margin-top: 0px;margin-right: 0px;margin-bottom: 15px\">\n" +
                    "    <thead style=\"font-family: arial\">\n" +
                    "    <tr bgcolor=\"#0074d9\" style=\"font-family: arial;background-color: #f2f2f2\">\n" +
                    "        <th colspan=\"0\" cellpadding=\"0\"\n" +
                    "            style=\"padding: 45px;border: none;color: white;font-size: 25px;font-family: arial;padding-top: 22px;padding-bottom: 22px;text-align: center;background-color: #3F51B5\">\n" +
                    "            SpectroCare Test Report\n" +
                    "        </th>\n" +
                    "    </tr>\n" +
                    "    </thead>\n" +
                    "</table>\n" +
                    "<div class=\"mainWrapper\" style=\"font-family: arial;width: 800px;margin: 0 auto\">\n" +
                    "    <div class=\"part1\" style=\"font-family: arial;width: 800px;margin: 0 auto;height: 150px\">\n" +
                    "        <div id=\"subDiv1\" style=\"font-family: arial;width: 300px;float: left;height: 150px\">\n" +
                    "            <figure style=\"font-family: arial\">\n" +
                    "                <img src=\"http://18.204.210.99/files/ri_13.png\" alt=\"\" style=\"font-family: arial\"/>\n" +
                    "            </figure>\n" +
                    "        </div>\n" +
                    "        <div id=\"subDiv2\"\n" +
                    "             style=\"font-family: arial;width: 350px;float: right;text-align: right;line-height: 1.8;height: 150px\">\n" +
                    "            <span style=\"font-family: arial\"><b\n" +
                    "                    style=\"font-family: arial\"> SpectroChip, Inc</b></span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "            <span style=\"font-family: arial\">Phone: 03-552-0892</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "            <span style=\"font-family: arial\">No.951, Fuxing Rd., Zhubei City,</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "            <span style=\"font-family: arial\">Hsinchu County 302, Taiwan (R.O.C.)</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "            <span style=\"font-family: arial\">www.spectrochips.com</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "        </div>\n" +
                    "    </div> <!-- Part one Ends here -->\n" +
                    "    <div class=\"clear\" style=\"font-family: arial\">\n" +
                    "        <hr style=\"font-family: arial\"/>\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div class=\"part2\" style=\"font-family: arial;width: 960px;margin-top: 20px\">\n" +
                    "        <div id=\"p2sub1\"\n" +
                    "             style=\"font-family: arial;line-height: 1.5;margin-left: 20px;width: 31%;height: 150px;float: left\">\n" +
                    "            <span style=\"font-family: arial\"><b\n" +
                    "                    style=\"font-family: arial\">Date:</b> 2018/11/08</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "            <span style=\"font-family: arial\"><b style=\"font-family: arial\">Name:</b> %m </span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "            <br style=\"font-family: arial\"/>\n" +
                    "            <span style=\"font-family: arial\"><b style=\"font-family: arial\">Height:</b> 170 cm</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "            <span style=\"font-family: arial\"><b style=\"font-family: arial\">Fasting:</b> No</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "        </div>\n" +
                    "        <div id=\"p2sub2\"\n" +
                    "             style=\"font-family: arial;line-height: 1.5;margin-left: 20px;width: 31%;height: 150px;float: left\">\n" +
                    "            <span style=\"font-family: arial\"><b\n" +
                    "                    style=\"font-family: arial\">Time:</b> 14:30pm</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "            <span style=\"font-family: arial\"><b\n" +
                    "                    style=\"font-family: arial\">Gender:</b> Male</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "            <br style=\"font-family: arial\"/>\n" +
                    "            <span style=\"font-family: arial\"><b\n" +
                    "                    style=\"font-family: arial\">Weight:</b> 80 kg</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "        </div>\n" +
                    "        <div id=\"p2sub3\"\n" +
                    "             style=\"font-family: arial;line-height: 1.5;margin-left: 20px;width: 31%;float: right;height: 150px\">\n" +
                    "            <span style=\"font-family: arial\"><b\n" +
                    "                    style=\"font-family: arial\">Blood Type:</b> A</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "            <span style=\"font-family: arial\"><b style=\"font-family: arial\">Age:</b> 50</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "            <br style=\"font-family: arial\"/>\n" +
                    "            <span style=\"font-family: arial\"><b style=\"font-family: arial\">BMI:</b> 27.7(over weight)</span><br\n" +
                    "                style=\"font-family: arial\"/>\n" +
                    "        </div>\n" +
                    "        <div class=\"picGrp\" style=\"font-family: arial;width: 800px;text-align: right\">\n" +
                    "            <span style=\"font-family: arial\"><img src=\"http://18.204.210.99/files/images/happy.png\"\n" +
                    "                                                  alt=\"\"\n" +
                    "                                                  style=\"font-family: arial;margin-left: 60px;position: relative;height: 30px;width: 30px;top: 6px;left: 2px\"/>:Normal </span>\n" +
                    "            <span style=\"font-family: arial\"><img src=\"http://18.204.210.99/files/images/sad.png\"\n" +
                    "                                                  alt=\"\"\n" +
                    "                                                  style=\"font-family: arial;margin-left: 60px;position: relative;height: 30px;width: 30px;top: 6px;left: 2px\"/> :Abnormal</span>\n" +
                    "        </div>\n" +
                    "    </div> <!-- Part 2 ends here -->\n" +
                    "\n" +
                    "    <div class=\"part3\" style=\"font-family: arial\">\n" +
                    "        <table border=\"1\" cellspacing=\"0\" cellpadding=\"10px\" width=\"800px\"\n" +
                    "               style=\"font-family: arial;margin-top: 20px;margin-left: 40px;float: right;text-align: center;font-weight: 600\">\n" +
                    "            <thead style=\"font-family: arial\">\n" +
                    "            <tr bgcolor=\"#0074d9\" style=\"font-family: arial\">\n" +
                    "                <th colspan=\"5\" cellpadding=\"10\"\n" +
                    "                    style=\"padding: 15px;border: none;color: white;font-size: 25px;font-family: arial\">\n" +
                    "                    Urine Test Report\n" +
                    "                </th>\n" +
                    "            </tr>\n" +
                    "            </thead>\n" +
                    "            <tbody style=\"font-family: arial\">\n" +
                    "            <tr style=\"font-family: arial\">\n" +
                    "                <th style=\"font-family: arial\">Test Item</th>\n" +
                    "                <th style=\"font-family: arial\">Value</th>\n" +
                    "                <th style=\"font-family: arial\">Result</th>\n" +
                    "                <th style=\"font-family: arial\">Flag</th>\n" +
                    "                <th style=\"font-family: arial\">Health Reference Ranges</th>\n" +
                    "            </tr>\n" +
                    "            <tr style=\"font-family: arial\">\n" +
                    "                <td style=\"font-family: arial\">Glucose</td>\n" +
                    "                <td style=\"font-family: arial\">%g mg/dL</td>\n" +
                    "                <td style=\"font-family: arial\">++</td>\n" +
                    "                <td style=\"font-family: arial\"><img src=\"file:///android_asset/sad.png\"\n" +
                    "                                                    alt=\"\"\n" +
                    "                                                    style=\"font-family: arial;height: 40px;width: 40px\"/>\n" +
                    "                </td>\n" +
                    "                <td style=\"font-family: arial\"> &lt;30 mg/dL</td>\n" +
                    "            </tr>\n" +
                    "            <tr style=\"font-family: arial\">\n" +
                    "                <td style=\"font-family: arial\">Protein</td>\n" +
                    "                <td style=\"font-family: arial\">%e mg/dL</td>\n" +
                    "                <td style=\"font-family: arial\">+/-</td>\n" +
                    "                <td style=\"font-family: arial\"><img\n" +
                    "                        src=\"http://18.204.210.99/files/images/happy.png\" alt=\"\"\n" +
                    "                        style=\"font-family: arial;height: 40px;width: 40px\"/></td>\n" +
                    "                <td style=\"font-family: arial\">&lt;10 mg/dL</td>\n" +
                    "            </tr>\n" +
                    "            <tr style=\"font-family: arial\">\n" +
                    "                <td style=\"font-family: arial\">Bilirubin</td>\n" +
                    "                <td style=\"font-family: arial\">%b mg/dL</td>\n" +
                    "                <td style=\"font-family: arial\">-</td>\n" +
                    "                <td style=\"font-family: arial\"><img\n" +
                    "                        src=\"http://18.204.210.99/files/images/happy.png\" alt=\"\"\n" +
                    "                        style=\"font-family: arial;height: 40px;width: 40px\"/></td>\n" +
                    "                <td style=\"font-family: arial\">&lt;0.5 mg/dL</td>\n" +
                    "            </tr>\n" +
                    "            <tr style=\"font-family: arial\">\n" +
                    "                <td style=\"font-family: arial\">Urobilinogen</td>\n" +
                    "                <td style=\"font-family: arial\">%c mg/dL</td>\n" +
                    "                <td style=\"font-family: arial\">-</td>\n" +
                    "                <td style=\"font-family: arial\"><img\n" +
                    "                        src=\"http://18.204.210.99/files/images/happy.png\" alt=\"\"\n" +
                    "                        style=\"font-family: arial;height: 40px;width: 40px\"/></td>\n" +
                    "                <td style=\"font-family: arial\">&lt;1.5 mg/dL</td>\n" +
                    "            </tr>\n" +
                    "            <tr style=\"font-family: arial\">\n" +
                    "                <td style=\"font-family: arial\">PH</td>\n" +
                    "                <td style=\"font-family: arial\">%h</td>\n" +
                    "                <td style=\"font-family: arial\">6.0</td>\n" +
                    "                <td style=\"font-family: arial\"><img\n" +
                    "                        src=\"http://18.204.210.99/files/images/happy.png\" alt=\"\"\n" +
                    "                        style=\"font-family: arial;height: 40px;width: 40px\"/></td>\n" +
                    "                <td style=\"font-family: arial\">5.0~7.5</td>\n" +
                    "            </tr>\n" +
                    "            <tr style=\"font-family: arial\">\n" +
                    "                <td style=\"font-family: arial\">Specific gravity</td>\n" +
                    "                <td style=\"font-family: arial\">%i</td>\n" +
                    "                <td style=\"font-family: arial\">1.017</td>\n" +
                    "                <td style=\"font-family: arial\"><img\n" +
                    "                        src=\"http://18.204.210.99/files/images/happy.png\" alt=\"\"\n" +
                    "                        style=\"font-family: arial;height: 40px;width: 40px\"/></td>\n" +
                    "                <td style=\"font-family: arial\">5.0~7.5</td>\n" +
                    "            </tr>\n" +
                    "            <tr style=\"font-family: arial\">\n" +
                    "                <td style=\"font-family: arial\">Occult Blood</td>\n" +
                    "                <td style=\"font-family: arial\">%a mg/dL</td>\n" +
                    "                <td style=\"font-family: arial\">-</td>\n" +
                    "                <td style=\"font-family: arial\"><img\n" +
                    "                        src=\"http://18.204.210.99/files/images/happy.png\" alt=\"\"\n" +
                    "                        style=\"font-family: arial;height: 40px;width: 40px\"/></td>\n" +
                    "                <td style=\"font-family: arial\"> &lt;0.03 mg/dL</td>\n" +
                    "            </tr>\n" +
                    "            <tr style=\"font-family: arial\">\n" +
                    "                <td style=\"font-family: arial\">Ketones</td>\n" +
                    "                <td style=\"font-family: arial\">%d mg/dL</td>\n" +
                    "                <td style=\"font-family: arial\">-</td>\n" +
                    "                <td style=\"font-family: arial\"><img\n" +
                    "                        src=\"http://18.204.210.99/files/images/happy.png\" alt=\"\"\n" +
                    "                        style=\"font-family: arial;height: 40px;width: 40px\"/></td>\n" +
                    "                <td style=\"font-family: arial\">0~4 mg/dL</td>\n" +
                    "            </tr>\n" +
                    "            <tr style=\"font-family: arial\">\n" +
                    "                <td style=\"font-family: arial\">Nitrite</td>\n" +
                    "                <td style=\"font-family: arial\">%f</td>\n" +
                    "                <td style=\"font-family: arial\">0.04</td>\n" +
                    "                <td style=\"font-family: arial\"><img\n" +
                    "                        src=\"http://18.204.210.99/files/images/happy.png\" alt=\"\"\n" +
                    "                        style=\"font-family: arial;height: 40px;width: 40px\"/></td>\n" +
                    "                <td style=\"font-family: arial\">0~0.04</td>\n" +
                    "            </tr>\n" +
                    "            <tr style=\"font-family: arial\">\n" +
                    "                <td style=\"font-family: arial\">Leukocyte</td>\n" +
                    "                <td style=\"font-family: arial\">%j WBC/ul</td>\n" +
                    "                <td style=\"font-family: arial\">-</td>\n" +
                    "                <td style=\"font-family: arial\"><img\n" +
                    "                        src=\"http://18.204.210.99/files/images/happy.png\" alt=\"\"\n" +
                    "                        style=\"font-family: arial;height: 40px;width: 40px\"/></td>\n" +
                    "                <td style=\"font-family: arial\">0~17 WBC/ul</td>\n" +
                    "            </tr>\n" +
                    "            </tbody>\n" +
                    "        </table>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";
            BufferedWriter write = new BufferedWriter(new FileWriter("/sdcard/myfup.html"));
            write.write(k);
            write.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Custom method to get assets folder image as bitmap
    private Bitmap getBitmapFromAssets(String fileName) {

        AssetManager am = getAssets();
        InputStream is = null;
        try {
            is = am.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }


    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        canvas.drawPaint(paint);
        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 100, 100, paint);
        document.finishPage(page);
        // write the document content
        String targetPdf = "/sdcard/test.pdf";
        final File filePath = new File(targetPdf);


        try {
            document.writeTo(new FileOutputStream(filePath));
            btn.setText("Export");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                    File fileWithinMyDir = new File(String.valueOf(filePath));

                    if (fileWithinMyDir.exists()) {
                        intentShareFile.setType("application/pdf");
                        intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));

                        intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                                "Sharing File...");
                        intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

                        startActivity(Intent.createChooser(intentShareFile, "Share File"));
                    }

/*
                    Uri uri = Uri.fromFile(filePath);
                    Intent share = new Intent();
                    share.setAction(Intent.ACTION_SEND);
                    share.setType("application/pdf");
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    share.setPackage("com.whatsapp");
                    startActivity(share);*/
                }
            });

            boolean_save = true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }


    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;


            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }


    public String renderTestReport(String htmlPath, String patientName, String date, String time, String gender, String age) {
        String HTML = null;
        try {
            // Load the invoice HTML template code into a String variable.
            File myhtml = new File(htmlPath);
            Log.e("filena", "" + myhtml.getName());
            InputStream is = new FileInputStream(htmlPath);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            Log.e("line", "" + line);
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                Log.e("sb...", "" + sb.toString());
                line = buf.readLine();
            }
            String fileAsString = sb.toString();
            Log.e("Contents", "" + fileAsString);
            //String HTMLContent = FileUtils.readFileToString("my text");
            String HTMLContent = fileAsString;
            Log.e("filec", "" + HTMLContent);
            // Replace all the placeholders with real values except for the items.
            // Patient Name
            HTMLContent = HTMLContent.replace("%m", patientName);
            Log.e("patientname", "" + HTMLContent);

            // Date
            HTMLContent = HTMLContent.replace("28/11/08", date);

            // Time
            HTMLContent = HTMLContent.replace("14:30pm", time);

            // Gender
            HTMLContent = HTMLContent.replace("Male", gender);

            // AGE
            HTMLContent = HTMLContent.replace("50", age);
            Log.e("texthtml", "" + HTMLContent);
            //  createPDF(HTMLContent);
            HTML = HTMLContent;
            BufferedWriter write = new BufferedWriter(new FileWriter("/sdcard/myfup.html"));
            write.write(HTMLContent);
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HTML;
    }


    public void preparePDF() {

        String pathToReportHTMLTemplate = "/sdcard/myfup.html";
        Log.e("text", "" + pathToReportHTMLTemplate);
        String htmlString = renderTestReport(pathToReportHTMLTemplate, "Viswanath Reddy P", "2018.16.11", "10:36 AM", "Male", "32");
        Log.e("text", "" + htmlString);
        wv.loadData(htmlString, "text/html", "utf-8");

    }


}


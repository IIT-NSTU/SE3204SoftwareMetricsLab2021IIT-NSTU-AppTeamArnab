package com.whatEver.iitnstu;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.storage.FirebaseStorage;


public class PdfViewer extends AppCompatActivity {

    private PDFView pdfView;
    private LoadingDialog loadingDialog;
    private String folder;
    private String pdfName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        loadingDialog=new LoadingDialog(PdfViewer.this);
        pdfName=getIntent().getExtras().getString("pdfName");
        folder=getIntent().getExtras().getString("folder");
        pdfView = (PDFView) findViewById(R.id.Pdf);

        fetchingData();
    }


    private void fetchingData() {
        loadingDialog.startLoadingDialog();

        FirebaseStorage.getInstance().getReference().child(folder).child(pdfName)
                .getBytes(1024 * 1024).addOnSuccessListener(bytes -> {
            loadingDialog.dismissDialog();
            Toast.makeText(getApplicationContext(),"Zoom out !!",Toast.LENGTH_LONG).show();
            pdfView.fromBytes(bytes).load();
        }).addOnFailureListener(e -> {
            Toast.makeText(getApplicationContext(),"download unsuccessful",Toast.LENGTH_LONG).show();
        });
    }
}

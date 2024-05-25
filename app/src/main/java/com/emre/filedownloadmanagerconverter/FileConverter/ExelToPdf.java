package com.emre.filedownloadmanagerconverter.FileConverter;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static org.apache.poi.ss.usermodel.DataValidationConstraint.ValidationType.FORMULA;
import static org.apache.poi.xslf.usermodel.SlideLayout.BLANK;

import static java.sql.Types.NUMERIC;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.annotation.SuppressLint;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import android.widget.ImageButton;

import android.widget.TextView;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import java.util.UUID;
import com.emre.filedownloadmanagerconverter.R;

public class ExelToPdf extends AppCompatActivity {

    ImageButton backhomeBtn;

    TextView exeltopdfText;
    ImageButton exeltopdfuploadBtn;

    private final String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator;
    String outputdoc;
    String outputPdfFile;
    UUID uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exel_to_pdf);
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        backhomeBtn=findViewById(R.id.backhomebutton);
        exeltopdfText=findViewById(R.id.exeltopdftext);
        exeltopdfuploadBtn=findViewById(R.id.exeltopdfuploadbutton);


        backhomeBtn.setOnClickListener(v -> {
            startActivity(new Intent(ExelToPdf.this, FileConverterActivity.class));
            finish();
        });

        exeltopdfuploadBtn.setOnClickListener(v -> {
            Intent myfileopener = new Intent(Intent.ACTION_GET_CONTENT);
            myfileopener.setType("*/*");
            startActivityForResult(Intent.createChooser(myfileopener,"Choose File"),10);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            uuid = UUID.randomUUID();
            outputPdfFile = directory + uuid.toString() + ".pdf";

            try (InputStream inputStream = getContentResolver().openInputStream(data.getData())) {
                Workbook workbook = new XSSFWorkbook(inputStream);
                Sheet sheet = workbook.getSheetAt(0);

                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(outputPdfFile));
                document.open();

                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();

                    PdfPTable table = new PdfPTable(row.getLastCellNum());
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        table.addCell(cell.toString());
                    }
                    document.add(table);
                }
                document.close();

                exeltopdfText.setText("File saved in downloads folder. You can convert more files by pressing upload button.");
            } catch (Exception e) {
                exeltopdfText.setText("Error! Check storage permissions and file type." + e.getMessage());
            }
        }

    }

}
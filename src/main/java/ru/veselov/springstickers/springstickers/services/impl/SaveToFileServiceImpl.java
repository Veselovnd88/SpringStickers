package ru.veselov.springstickers.springstickers.services.impl;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.veselov.springstickers.springstickers.exception.PdfCreationException;
import ru.veselov.springstickers.springstickers.services.SaveToFileService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaveToFileServiceImpl implements SaveToFileService {

    @Override
    public InputStream saveToPdf(ByteArrayOutputStream imageStream) {
        ByteArrayOutputStream pdfBaos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, pdfBaos);
            pdfWriter.open();
            document.open();
            Image image = transformImage(imageStream, document);
            document.add(image);
            document.close();
            pdfWriter.close();
            return new ByteArrayInputStream(pdfBaos.toByteArray());
        } catch (IOException | DocumentException e) {
            throw new PdfCreationException(e.getMessage());
        }

    }

    private Image transformImage(ByteArrayOutputStream imageStream, Document document) throws BadElementException, IOException {
        Image image = Image.getInstance(imageStream.toByteArray());
        image.setAbsolutePosition(0,0);
        image.scaleAbsolute(document.getPageSize());
        return image;
    }
}

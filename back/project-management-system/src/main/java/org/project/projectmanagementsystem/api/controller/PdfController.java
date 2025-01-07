package org.project.projectmanagementsystem.api.controller;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.services.PdfGeneratorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.UUID;

@RestController
@RequestMapping("/project/pdf-generate")
@RequiredArgsConstructor
public class PdfController {
    private final PdfGeneratorService pdfGeneratorService;

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdfForProject(@RequestParam("projectId") UUID projectId){

        byte[] document = pdfGeneratorService.generatePdfSummaryForProject(projectId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=example.pdf");
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return new ResponseEntity<>(document, httpHeaders, HttpStatus.OK);
    }
}

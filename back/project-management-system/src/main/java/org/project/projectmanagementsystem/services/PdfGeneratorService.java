package org.project.projectmanagementsystem.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.ProjectSummary;
import org.project.projectmanagementsystem.domain.UserTasks;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PdfGeneratorService {
    private final ProjectSummaryService projectSummaryService;
    private static final BaseFont baseFont;

    static {
        try {
            baseFont = BaseFont.createFont("assets/fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generatePdfSummaryForProject(UUID projectId){
        ProjectSummary projectSummary = projectSummaryService.generateSummaryForProject(projectId);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            Font font = new Font(baseFont, 16, Font.BOLD, BaseColor.DARK_GRAY);

            Document document = new Document();
            PdfWriter.getInstance(document,byteArrayOutputStream);
            document.open();

            document.addTitle("Podsumowanie Projektu");

            Paragraph header = new Paragraph("PROJEKT: %s".formatted(projectSummary.getProject().getName()));
            header.setFont(font);
            header.setSpacingAfter(20);

            PdfDiv pdfDiv = buildProjectDescription(projectSummary.getProject());

            PdfPTable pdfPTable = buildUserTable(projectSummary);

            document.add(header);
            document.add(pdfDiv);

            Paragraph usersHeader = new Paragraph("Uzytkownicy w projekcie");
            document.add(usersHeader);
            document.add(pdfPTable);

            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private PdfPTable buildUserTable(ProjectSummary usersInProject) {
        PdfPTable pdfPTable = new PdfPTable(2);
        pdfPTable.addCell("Nazwa uzytkownika");
        pdfPTable.addCell("Wykonane zadania");

        List<UserTasks> usersCountFinishedTasks = usersInProject.getUsersCountFinishedTasks();

        usersCountFinishedTasks.forEach(userTasks -> {
            pdfPTable.addCell(userTasks.getUsername());
            pdfPTable.addCell(userTasks.getRealizedTasks().toString());
        });

        return pdfPTable;
    }

    private PdfDiv buildProjectDescription(Project project) {
        PdfDiv pdfDiv = new PdfDiv();
        pdfDiv.setKeepTogether(true);

        Paragraph projectIdContent = new Paragraph("ID: %s".formatted(project.getProjectId()));
        projectIdContent.setSpacingAfter(10);

        Paragraph description = new Paragraph("Opis:%n%s".formatted(project.getDescription()));
        description.setSpacingAfter(10);

        PdfDiv dateContainer = buildDateContainer(project);

        pdfDiv.addElement(projectIdContent);
        pdfDiv.addElement(description);
        pdfDiv.addElement(dateContainer);
        return pdfDiv;
    }

    private PdfDiv buildDateContainer(Project project) {
        PdfDiv dateContainer = new PdfDiv();
        PdfDiv startDateContainer = new PdfDiv();
        startDateContainer.setPercentageWidth(50f);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        startDateContainer.addElement(new Paragraph("Data rozpoczęcia: %s".formatted(project.getStartDate()
                .format(dateTimeFormatter))));

        PdfDiv finishDateContainer = new PdfDiv();
        finishDateContainer.setPercentageWidth(50f);
        finishDateContainer.addElement(new Paragraph("Data zakończenia %s".formatted(project.getFinishDate()
                .format(dateTimeFormatter))));

        dateContainer.setKeepTogether(true);
        dateContainer.addElement(startDateContainer);
        dateContainer.addElement(finishDateContainer);
        dateContainer.setBorderTopStyle(PdfDiv.BorderTopStyle.INSET);
        return dateContainer;
    }

}

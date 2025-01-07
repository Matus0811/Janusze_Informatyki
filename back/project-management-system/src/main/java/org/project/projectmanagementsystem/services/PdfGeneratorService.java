package org.project.projectmanagementsystem.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.api.dto.ProjectTaskStatusCount;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.ProjectSummary;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.UserTasks;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PdfGeneratorService {
    public static final float CHARACTER_SPACING = 1.1f;
    private final ProjectSummaryService projectSummaryService;
    private final Font ARIAL_NORMAL_FONT = new Font(ARIAL_NORMAL, 16, Font.NORMAL, BaseColor.DARK_GRAY);
    private final Font ARIAL_NARROW_FONT = new Font(ARIAL_NARROW, 16, Font.NORMAL, BaseColor.DARK_GRAY);
    private static final PdfDiv border = new PdfDiv();
    private static final BaseFont ARIAL_NORMAL;
    private static final BaseFont ARIAL_NARROW;

    static {
        border.setPosition(PdfDiv.PositionType.ABSOLUTE);
        border.setPercentageWidth(50f);
        border.setHeight(2f);
        border.setBackgroundColor(new BaseColor(31, 31, 31));
        border.setSpacingBefore(5);
        border.setSpacingAfter(5);
    }

    static {
        try {
            ARIAL_NORMAL = BaseFont.createFont("assets/fonts/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            ARIAL_NARROW = BaseFont.createFont("assets/fonts/arialnarrow.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public byte[] generatePdfSummaryForProject(UUID projectId){
        ProjectSummary projectSummary = projectSummaryService.generateSummaryForProject(projectId);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            Document document = new Document();
            PdfWriter.getInstance(document,byteArrayOutputStream);
            document.open();
            document.addTitle("Podsumowanie Projektu %s".formatted(projectSummary.getProject().getProjectId()));

            PdfDiv projectOwnerDataInfo = generateProjectOwnerInfoDiv(projectSummary.getProjectOwner());
            document.add(projectOwnerDataInfo);

            Paragraph headerWithProjectName = new Paragraph(getProjectHeaderChunk(projectSummary));
            headerWithProjectName.setSpacingAfter(20);

            PdfDiv pdfDiv = buildProjectDescription(projectSummary.getProject());
            PdfPTable pdfPTable =null;
            if(!projectSummary.getUsersCountFinishedTasks().isEmpty()){
                pdfPTable = buildUserTable(projectSummary);
            }

            document.add(headerWithProjectName);
            document.add(border);
            document.add(pdfDiv);


            if(Objects.nonNull(pdfPTable)){
                Chunk chunk = new Chunk("UŻYTKOWNICY W PROJEKCIE");
                chunk.setFont(new Font(ARIAL_NARROW,18,Font.BOLD,BaseColor.DARK_GRAY));
                chunk.setCharacterSpacing(CHARACTER_SPACING);
                document.add(new Paragraph(chunk));
                document.add(pdfPTable);
            }

            if(!projectSummary.getProjectTaskStatusCounts().isEmpty()){

                Chunk chunk = new Chunk("ZREALIZOWANE ZADANIA");
                chunk.setFont(new Font(ARIAL_NARROW,18,Font.BOLD,BaseColor.DARK_GRAY));
                chunk.setCharacterSpacing(CHARACTER_SPACING);
                Paragraph groupedTasksHeader = new Paragraph(chunk);
                groupedTasksHeader.setSpacingBefore(20);
                groupedTasksHeader.setSpacingAfter(10);
                groupedTasksHeader.setAlignment(Element.ALIGN_LEFT);
                PdfPTable groupedTasksCountTable = buildGroupedTasksTable(projectSummary.getProjectTaskStatusCounts());

                document.add(groupedTasksHeader);
                document.add(groupedTasksCountTable);
            }
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private PdfDiv generateProjectOwnerInfoDiv(User projectOwner) {
        PdfDiv div = new PdfDiv();
        div.setContentWidth(300);
        div.setPaddingBottom(10);
        div.setPaddingTop(5);
        div.setPaddingRight(10);
        div.setPaddingLeft(10);
        div.setFloatType(PdfDiv.FloatType.RIGHT);
        Font fontHeader = new Font(ARIAL_NORMAL,10,Font.NORMAL,BaseColor.GRAY);

        Chunk owner = new Chunk("Właściciel",fontHeader);
        Chunk fullNameChunk = new Chunk("%s %s".formatted(projectOwner.getName(),projectOwner.getSurname()),fontHeader);
        Chunk phoneChunk = new Chunk(projectOwner.getPhone(),fontHeader);
        Chunk emailChunk = new Chunk(projectOwner.getEmail(),fontHeader);
        div.addElement(owner);
        div.addElement(fullNameChunk);
        div.addElement(phoneChunk);
        div.addElement(emailChunk);

        return div;
    }

    private Chunk getProjectHeaderChunk(ProjectSummary projectSummary) {
        Chunk projectHeaderChunk = new Chunk("PROJEKT: %s".formatted(projectSummary.getProject().getName()));
        ARIAL_NORMAL_FONT.setSize(22);
        ARIAL_NORMAL_FONT.setStyle(Font.BOLD);
        projectHeaderChunk.setFont(ARIAL_NORMAL_FONT);
        return projectHeaderChunk;
    }

    private PdfPTable buildGroupedTasksTable(List<ProjectTaskStatusCount> projectTaskStatusCounts) {
        PdfPTable pdfPTable = new PdfPTable(2);
        pdfPTable.setWidthPercentage(100);

        Font arialNormalFont = new Font(ARIAL_NORMAL,14,Font.NORMAL,new BaseColor(240,240,240));

        Chunk typeOfExerciseChunkColumHeader = new Chunk(
                "Rodzaj zadania",
                arialNormalFont
        );

        PdfPCell columnBugTypeHeader = new PdfPCell(
                new Phrase(typeOfExerciseChunkColumHeader)
        );
        columnBugTypeHeader.setPadding(5);
        columnBugTypeHeader.setBackgroundColor(new BaseColor(36,36,36));


        Chunk columnCountChunkColumHeader = new Chunk(
                "Liczba",
                arialNormalFont
        );
        PdfPCell columnCountHeader = new PdfPCell(
                new Phrase(columnCountChunkColumHeader)
        );
        columnCountHeader.setPadding(5);
        columnCountHeader.setBackgroundColor(new BaseColor(36,36,36));


        pdfPTable.addCell(columnBugTypeHeader);
        pdfPTable.addCell(columnCountHeader);
        Font cellFont = new Font(ARIAL_NORMAL,12,Font.NORMAL,new BaseColor(35,35,35));
        BaseColor backgroundColor = new BaseColor(240,240,240);
        for (ProjectTaskStatusCount projectTaskStatusCount : projectTaskStatusCounts) {
            Chunk taskTypeChunk = new Chunk(
                    projectTaskStatusCount.status().toString(),
                    cellFont
            );


            PdfPCell chunkTypeCell = new PdfPCell(
                    new Phrase(taskTypeChunk)
            );
            chunkTypeCell.setPadding(5);
            chunkTypeCell.setBackgroundColor(backgroundColor);

            Chunk taskCountChunk = new Chunk(
                    projectTaskStatusCount.count().toString(),
                    cellFont
            );

            PdfPCell taskCountCell = new PdfPCell(
                    new Phrase(taskCountChunk)
            );
            taskCountCell.setPadding(5);
            taskCountCell.setBackgroundColor(backgroundColor);

            pdfPTable.addCell(chunkTypeCell);
            pdfPTable.addCell(taskCountCell);
        }

        return pdfPTable;
    }

    private PdfPTable buildUserTable(ProjectSummary usersInProject) {
        PdfPTable pdfPTable = new PdfPTable(2);
        pdfPTable.setSpacingBefore(10);
        pdfPTable.setWidthPercentage(100);
        Font arialNormalFont = new Font(ARIAL_NORMAL,14,Font.BOLD,new BaseColor(240,240,240));


        Chunk usernameColumnHeaderChunk = new Chunk("Nazwa użytkownika", arialNormalFont);
        PdfPCell usernameColumnHeaderCell = new PdfPCell(new Phrase(usernameColumnHeaderChunk));
        usernameColumnHeaderCell.setBackgroundColor(new BaseColor(36,36,36));
        usernameColumnHeaderCell.setPadding(5);

        Chunk numberOfRealisedTasksHeaderChunk = new Chunk("Wykonane zadania",arialNormalFont);
        PdfPCell numberOfRealisedTasksHeaderCell = new PdfPCell(new Phrase(numberOfRealisedTasksHeaderChunk));
        numberOfRealisedTasksHeaderCell.setBackgroundColor(new BaseColor(36,36,36));
        numberOfRealisedTasksHeaderCell.setPadding(5);

        pdfPTable.addCell(usernameColumnHeaderCell);
        pdfPTable.addCell(numberOfRealisedTasksHeaderCell);
        List<UserTasks> usersCountFinishedTasks = usersInProject.getUsersCountFinishedTasks();

        Font cellFont = new Font(ARIAL_NORMAL,12,Font.NORMAL,new BaseColor(36,36,36));
        usersCountFinishedTasks.forEach(userTasks -> {

            Chunk usernameChunk = new Chunk(userTasks.getUsername(),cellFont);
            PdfPCell usernameCell = new PdfPCell(new Phrase(usernameChunk));
            usernameCell.setBackgroundColor(new BaseColor(240,240,240));

            Chunk realizedTasksChunk = new Chunk(userTasks.getNumberOfRealizedTasks().toString(),cellFont);
            PdfPCell realizedTasksCell = new PdfPCell(new Phrase(realizedTasksChunk));
            realizedTasksCell.setBackgroundColor(new BaseColor(240,240,240));

            pdfPTable.addCell(usernameCell);
            pdfPTable.addCell(realizedTasksCell);
        });

        return pdfPTable;
    }

    private PdfDiv buildProjectDescription(Project project) {
        PdfDiv pdfDiv = new PdfDiv();
        pdfDiv.setKeepTogether(true);
        pdfDiv.setSpacingBefore(10);

        Chunk projectIdChunk = new Chunk("ID\n",new Font(ARIAL_NORMAL,12,Font.BOLD,BaseColor.DARK_GRAY));
        projectIdChunk.setCharacterSpacing(CHARACTER_SPACING);
        Chunk projectIdInfoChunk = new Chunk(project.getProjectId().toString(),new Font(ARIAL_NORMAL,12,Font.NORMAL,BaseColor.DARK_GRAY));
        Paragraph projectIdContent = new Paragraph(projectIdChunk);
        projectIdContent.add(new Paragraph(projectIdInfoChunk));
        projectIdContent.setSpacingAfter(10);

        Chunk projectDescriptionHeader = new Chunk("OPIS\n",new Font(ARIAL_NORMAL,12,Font.BOLD,BaseColor.DARK_GRAY));
        projectDescriptionHeader.setCharacterSpacing(CHARACTER_SPACING);
        Chunk projectDescription = new Chunk(project.getDescription(),new Font(ARIAL_NORMAL,12,Font.NORMAL,BaseColor.DARK_GRAY));
        Paragraph description = new Paragraph(projectDescriptionHeader);
        description.add(new Paragraph(projectDescription));
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
        Font font = new Font(ARIAL_NORMAL,12,Font.BOLD,BaseColor.DARK_GRAY);

        startDateContainer.setContentWidth(300f);
        startDateContainer.setHeight(50f);
        startDateContainer.setFloatType(PdfDiv.FloatType.LEFT);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Chunk startDateChunk = new Chunk(
                "Data rozpoczęcia: %s".formatted(project.getStartDate().format(dateTimeFormatter)),
                font
        );
        startDateContainer.addElement(new Paragraph(startDateChunk));

        PdfDiv finishDateContainer = new PdfDiv();
        finishDateContainer.setContentWidth(300f);
        finishDateContainer.setContentHeight(50f);
        finishDateContainer.setTextAlignment(Element.ALIGN_MIDDLE);
        finishDateContainer.setFloatType(PdfDiv.FloatType.RIGHT);
        Chunk finishDateChunk = new Chunk(
                "Data zakończenia %s".formatted(project.getFinishDate().format(dateTimeFormatter)),
                font
        );
        finishDateContainer.addElement(new Paragraph(finishDateChunk));

        dateContainer.setKeepTogether(true);
        dateContainer.addElement(startDateContainer);
        dateContainer.addElement(finishDateContainer);
        dateContainer.setBorderTopStyle(PdfDiv.BorderTopStyle.INSET);
        return dateContainer;
    }

}

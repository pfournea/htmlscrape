package be.fourneau.htmlscrape.htmlscrape.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleSheetServiceTest {
    private static final String SPREADSHEET_ID = "1nlCvsrdD5T8bA1JFlB4PRIz6BPkV5gfGO_KTSKcJjpI";
    private final Sheets sheetService;

    public GoogleSheetServiceTest(Sheets sheetService) {
        this.sheetService = sheetService;
    }

    public String getSomeThing() throws IOException {
        // Prints the names and majors of students in a sample spreadsheet:
        // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
        String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
        String range = "Class Data!A2:E";
        ValueRange response = sheetService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.size() == 0) {
            return "No data found.";
        } else {
            System.out.println("Name, Major");
            return values.stream()
                    .map(row -> row.get(0) + ", " + row.get(4))
                    .collect(Collectors.joining("\n"));
        }
    }

    public void WriteExample() throws IOException {
        List<Request> requests = new ArrayList<>();

        List<CellData> values = new ArrayList<>();


        values.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue("Hello World!")));
        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(0)
                                .setRowIndex(0)
                                .setColumnIndex(0))
                        .setRows(Arrays.asList(
                                new RowData().setValues(values)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));

        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        sheetService.spreadsheets().batchUpdate(SPREADSHEET_ID, batchUpdateRequest)
                .execute();
    }


    public void addExtraRow() throws IOException {
        List<List<Object>> values = Arrays.asList(
                Arrays.asList(
                        LocalDate.now().format(DateTimeFormatter.ISO_DATE), new Double(20.15), new Double(21.15)
                ),
                Arrays.asList(LocalDate.now().format(DateTimeFormatter.ISO_DATE), new Double(22.15), new Double(23.15))
                // Additional rows ...
        );
        ValueRange body = new ValueRange()
                .setValues(values);

        sheetService.spreadsheets().values()
                .append(SPREADSHEET_ID, "Rebel U Wing Fighter!A1:D1", body)
                .setValueInputOption("RAW").execute();

    }
}

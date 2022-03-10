package samoiloff90.github.io;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import com.opencsv.CSVReader;
import java.util.List;
import static samoiloff90.github.io.parametrs.ForZipFiles.*;

public class TestZipFile {

    @Test
    public void fromFile() throws Exception {
        String pathFile = "src/test/resources/test.zip";
        ZipFile zipFile = new ZipFile(pathFile);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.getName().contains("pdf")) {
                assertThat(entry.getName()).isEqualTo(pdfFile);
                parsePdfTest(zipFile.getInputStream(entry));
            } else if (entry.getName().contains("xlsx")) {
                assertThat(entry.getName()).isEqualTo(xlsFile);
                parseXlsTest(zipFile.getInputStream(entry));
            } else if (entry.getName().contains("csv")) {
                assertThat(entry.getName()).isEqualTo(csvFile);
                parseCsvTest(zipFile.getInputStream(entry));
            }
        }
    }

    void parsePdfTest(InputStream file) throws Exception {
        PDF pdf = new PDF(file);
        assertThat(pdf.text).contains(
                "Тест"
        );
    }

    void parseXlsTest(InputStream file) throws Exception {
        XLS xls = new XLS(file);
        assertThat(xls.excel
                .getSheetAt(0)
                .getRow(2)
                .getCell(2)
                .getStringCellValue()).contains("Бухгалтер");
    }

    void parseCsvTest(InputStream file) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file))) {
            List<String[]> content = reader.readAll();
            assertThat(content.get(0)).contains(
                    "ID",
                    "Название ЖК",
                    "Кол-во кв. от застройщика",
                    "Застройщик",
                    "Ссылка"
            );
        }
    }
}

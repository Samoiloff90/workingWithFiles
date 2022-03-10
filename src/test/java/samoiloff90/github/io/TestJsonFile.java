package samoiloff90.github.io;

import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import samoiloff90.github.io.parametrs.User;
import java.nio.file.Paths;
import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import static samoiloff90.github.io.parametrs.TestData.pathFileJson;

public class TestJsonFile {

    @Test
    void jsonTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        User student = mapper.readValue(Paths.get(pathFileJson).toFile(), User.class);
        assertThat(student.name).isEqualTo("Roman");
        assertThat(student.lastname).isEqualTo("Kovalsky");
        assertThat(student.skills).contains("Python", "JS");
    }
}
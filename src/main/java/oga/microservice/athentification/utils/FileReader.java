import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
    private static final String SAMPLE_CSV_FILE_PATH = "./users.csv";

    public static void main(String[] args) throws IOException {
        MultipartFile file =null ;
        try (
                InputStream is = file.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                Reader reader = Files.newBufferedReader(Paths.get("C:\\demo\\users-with-header.csv"));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by Header names
                String firstname = csvRecord.get("firstname");
                String lastname = csvRecord.get("lastname");
                String password = csvRecord.get("password");
                String email = csvRecord.get("email");
                String service = csvRecord.get("service");

                System.out.println("Record No - " + csvRecord.getRecordNumber());
                System.out.println("---------------");
                System.out.println("fname : " + firstname);
                System.out.println("lname : " + lastname);
                System.out.println("password : " + password);
                System.out.println("email : " + email);
                System.out.println("service : " + service);
                System.out.println("---------------\n\n");
            }
        }
    }
}
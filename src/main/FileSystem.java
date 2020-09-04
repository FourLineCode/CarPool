package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class FileSystem {
    public static void main(String[] args) {

        // get relative path
        String filePath = new File("").getAbsolutePath();
        String path = filePath.concat("/src/db/data.csv");

        // read from file
        try{
            String row;
            BufferedReader csvReader = new BufferedReader(new FileReader(path));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(","); // my,name,is,akmal
                // do something with the data
                for(String s : data){
                    System.out.print(s + "\t");
                }
                System.out.println();
            }
            csvReader.close();
        } catch(Exception e){
            e.printStackTrace();
        }

        // append to file
        try{
            List<List<String>> rows = Arrays.asList(
                    Arrays.asList("Jean", "author", "Java"),
                    Arrays.asList("David", "editor", "Python"),
                    Arrays.asList("Scott", "editor", "Node.js")
            );

            FileWriter csvWriter = new FileWriter(path, true);
            csvWriter.append("Name");
            csvWriter.append(",");
            csvWriter.append("Role");
            csvWriter.append(",");
            csvWriter.append("Topic");
            csvWriter.append("\n");

            for (List<String> rowData : rows) {
                csvWriter.append(String.join(",",rowData));
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}

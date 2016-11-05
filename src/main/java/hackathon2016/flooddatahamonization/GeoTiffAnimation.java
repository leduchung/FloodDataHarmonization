/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackathon2016.flooddatahamonization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author hungld
 */
@ManagedBean(name = "TiffAnimation")
public class GeoTiffAnimation {

    // the list of tiff, separated with comma
    public String getListOfTiff() {
        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("/conf/spartacus.list").getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append(",");
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return removeLastChar(result.toString()).trim();
    }

    public int getSpactacusDataNumber() {
        String txt = getListOfTiff();
        System.out.println("Get data number from String: " + txt);
        return txt.split(System.getProperty("line.separator")).length;
    }

    public String getEndpoint() {
        return getGenericParameter("endpoint", "http://localhost:8080/FloodDataHarmonization-1.0/");
    }

    public String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length() - 1);
    }

    // FLOOD
    String selectedGRDH;

    public Map<String, String> getListOfGRDH() {
        StringBuilder result = new StringBuilder("");

        Map<String, String> menuList = new HashMap<>();
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("/conf/GRDH.list").getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append(",");
                menuList.put(line, line);
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return menuList;
    }

    public Map<String, String> getListWaterBody() {
        StringBuilder result = new StringBuilder("");

        Map<String, String> menuList = new HashMap<>();
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("/conf/waterbody.list").getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append(",");
                menuList.put(line, line);
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return menuList;
    }

    public String getSelectedGRDH() {
        return selectedGRDH;
    }

    public void setSelectedGRDH(String selectedGRDH) {
        this.selectedGRDH = selectedGRDH;
    }

    // UTILS ======================================
    private static final String CURRENT_DIR = System.getProperty("user.dir");
    private static final String CONFIG_FILE = CURRENT_DIR + "/fdh.conf";

    private static String getGenericParameter(String key, String theDefault) {
        Properties prop = new Properties();
        InputStream input;
        File myFile = new File(CONFIG_FILE);

        try {
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            input = new FileInputStream(CONFIG_FILE);
            // load a properties file
            prop.load(input);
            String param = prop.getProperty(key);
            if (param != null) {   // just return default MQTT
                return param;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return theDefault;
    }

    //    public String getListOfTiff_FromFolder() {
//        String folderName = "data/spartacus";
//        File folder = new File(folderName);
//        File[] listOfFiles = folder.listFiles();
//        StringBuilder builder = new StringBuilder();
//
//        for (File listOfFile : listOfFiles) {
//            if (listOfFile.isFile()) {
//                builder.append(listOfFile.getName()).append(",");
//            } else if (listOfFile.isDirectory()) {
//                System.out.println("This is directory, do not use: " + listOfFile.getName());
//            }
//        }
//        return removeLastChar(builder.toString());
//    }
}

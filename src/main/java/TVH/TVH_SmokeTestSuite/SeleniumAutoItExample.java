package TVH.TVH_SmokeTestSuite;
import java.io.IOException;

 

public class SeleniumAutoItExample {
    public static void main(String[] args) {
        try {
            // Replace with the path to your compiled AutoIt script
            String autoItScriptPath = "C:\\Users\\rudra.prasad\\Downloads\\fileupload.au3";

           
            // Execute the AutoIt script to handle the file upload dialog
            Runtime.getRuntime().exec(autoItScriptPath);

 

            // Continue with your Selenium automation after the file upload
            // ...

 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//necessary itext imports
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.forms.PdfAcroForm;

//other necessary import statements
import java.io.File;
import java.awt.Desktop;


/**
 * Filler class to fill out a specified PDF form.
 *
 * @author Dawson
 * @version 0.5
 */
public class Filler {
    /**
     * Constructor for a filler to fill out a PDF form.
     *
     * @param dest      File location and name for the created output
     * @param src       File location and name for the PDF form to fill out
     * @param message   Message object to send debug/failure messages back to the main program
     * @param values    2D string array in format of { [fieldName1, fieldValue1], [fieldName2, fieldValue2]... }
     */
    public Filler(String dest, String src, Message message, String[][] values) {
        //grabbing the files
        File destination = new File(dest); //destination file
        destination.getParentFile().mkdirs(); //creating file

        //verifying values given
        if (values == null) {
            //showing that the given values array is null
            message.setMessage("Form error: Given values 2D array is Null.");
            return;
        }
        //checking each value in the values 2D array
        for (int a = 0; a < values.length; a++) {
            //checking to make sure the a'th array isn't null
            if (values[a] == null) {
                message.setMessage("Form Error: Given value array " + a + " is null.");
                return;
            }
            //looping through the a'th array
            for (int b = 0; b < values[a].length; b++) {
                //checking if each String in the a'th array is null or blank
                if (values[a][b] == null || values[a][b].trim().isEmpty()) {
                    message.setMessage("Form Error: Given value at " + a + ", " + b + "is Null or empty.");
                    return;
                }
                b++;
            }
        }

        File file2 = new File(src); //grabbing source file
        if (!file2.exists()) {
            //if the PDF form doesn't exist, say so
            message.setMessage("Given PDF form was not found.");
        } else {
            //if the PDF form exists, execute the program
            if (message.debugging()) {
                //if debugging is enabled, show file path
                message.addMessage("PDF form file path: " + file2.getAbsolutePath());
            }

            //filling out the PDF form
            try {
                //-actually filling out the PDF form
                //grabbing the source file's PDF form
                PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

                //looping through the changes 2D array to set the values of the PDF form
                for (String[] field : values) {
                    if (form.getField(field[0]) == null) {
                        message.addMessage("Writing error: field " + field[0] + " was not found on PDF form.");
                        break;
                    }
                    form.getField(field[0]).setValue(field[1]);
                }

                //closing the PDF form file after completion
                pdfDoc.close();

                Desktop.getDesktop().open(new File(dest));

                //if debugging is enabled, show that the PDF was closed successfully
                if (message.debugging()) {
                    message.addMessage("PDF form closed successfully.");
                }
            } catch (Exception e) {
                //if there is an error during filling the PDF form, say so
                message.setMessage("Writing error: " + e.getMessage());
            }
        }
    }
}
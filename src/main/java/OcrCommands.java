import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class OcrCommands {

    private final ITesseract tesseractInstance;

    /**
     * Sets up Tesseract object with training data
     */
    public OcrCommands() {
        ImageIO.scanForPlugins();
        tesseractInstance = new Tesseract();
        tesseractInstance.setDatapath(
                LoadLibs.extractTessResources("tessdata").getPath()
        );
    }

    /**
     * Determine if a string is present in OCR extracted text.
     *
     * @param searchQuery  String to locate in the extracted OCR text
     * @param fileToSearch The File object to run OCR query on
     * @return boolean - searchQuery exists in the specified File
     */
    private boolean ocrQueryCheck(final String searchQuery, final File fileToSearch) {
        try {
            final String result = tesseractInstance.doOCR(fileToSearch);
            System.out.println("Text from file: " + fileToSearch.getName());
            System.out.println(result);
            return result.contains(searchQuery);
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        final OcrCommands ocrCommands = new OcrCommands();

        // Run OCR tests on multiple images/pdfs
        final List<File> filesToRunOcrAgainst = Arrays.asList(
                new File("TestData/ABCDE.pdf"),
                new File("TestData/Lorem1.pdf"),
                new File("TestData/fonts_used_in_pdf.png")
        );

        // Iterate through each file and run OCR query check, output results
        for(File file : filesToRunOcrAgainst) {
            boolean isStringPresent = ocrCommands.ocrQueryCheck("Helvetica", file);
            System.out.println("Query string present for file " + file.getName() + ": " + isStringPresent);
        }
    }
}

package Screenshot;

import java.io.IOException;

/**
 * Created by om on 2/3/2015.
 */
public class ScreenshotGallery {
    public static void main(String[] args) throws IOException {
        String Path = "TestingFiles/ScreenShots/HCL/HCL_LiveStack_DevStack";

        ScreenshotFunctions func = new ScreenshotFunctions();
        func.RenameFilesAfterScreenshotComparision(Path);
    }
}

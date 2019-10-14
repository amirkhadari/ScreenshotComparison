package Screenshot;

import java.io.IOException;

/**
 * Created by om on 11/7/2014.
 */
public class ScreenshotFoldersMerge {
    public static void main(String[] args) throws IOException {
        // Give sitelevel without stack or driver
        String SiteLevel1 = "Live";
        String SiteLevel2 = "Dev";
        String ScreenshotThrough = "Stack";
        String Site = "HCL";
        String Path = "/TestingFiles/ScreenShots/" + Site + "/";

        ScreenshotFunctions func = new ScreenshotFunctions();
        func.MergeFolders(SiteLevel1, SiteLevel2, ScreenshotThrough, Site, Path);
        func.ChangeWidth(SiteLevel1, SiteLevel2, ScreenshotThrough, Site, Path);

        /**
         * After merge the screenshots of two sitelevel, we will do screenshot comparision.
         * 1. First specify the folder name with path in config file that contain screenshots.
         * 2. Open console and move to folder that contains screenshots.
         * 3. Run command "wraith crop_images configFileName" to cut the images at same height.
         * 4. Run command "wraith compare_images configFileName" to generate diff image.
         */
    }
}

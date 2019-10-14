package Screenshot;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.lang.StringUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by om on 1/7/2015.
 */
public class ScreenshotFunctions {
    private String Password = "myfriends";
    private String Username = "mukesh.agarwal@innoraft.com";
    public String BasePath = "/home/innoraft/Downloads/";
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private int timeoutOfOneElement = 120;
    private int timeoutOFAllElement = 20;
    private String ChromeDriverPath = "/usr/lib/chromium-browser/chromedriver";
    private String IEDriverPath = "C:\\Users\\om\\Downloads\\Programs\\Selenium\\IEDriverServer.exe";

    // To run test on Chrome browser
    public void TestOnChrome() throws IOException {
        // Disable the "unsupported flag" prompt, it affect the window size.
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("test-type");
        System.setProperty("webdriver.chrome.driver", ChromeDriverPath);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(timeoutOFAllElement, TimeUnit.SECONDS);
    }

    // To run test on Firefox browser
    public void TestOnFirefox() throws IOException {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(timeoutOFAllElement, TimeUnit.SECONDS);
    }

    // To run test on Internet Explorer browser
    public void TestOnInternetExplorer() throws IOException {
        System.setProperty("webdriver.ie.driver", IEDriverPath);
        driver = new InternetExplorerDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(timeoutOFAllElement, TimeUnit.SECONDS);
    }

    // To take screenshots through webdriver
    public void OnBrowsersByWebDriver(String[][] FilenameUrl, String SiteLevel, String path, String Browser) throws IOException, InterruptedException {
        String Filename = null;
        String Url = null;

        String GetFilenameUrl[] = new String[2];
        for(int i=1; i<FilenameUrl.length; i++) {
            for(int j=0; j<2; j++) {
                GetFilenameUrl[j]  = FilenameUrl[i][j];
            }
            Filename = GetFilenameUrl[0].replace(" ", "").replace("&", "and").replace("_","-");
            Url = GetFilenameUrl[1];

            driver.get(Url);
            // Wait for page to be fully loaded
            Thread.sleep(10000);
            // Take Screenshot
            File Screen = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);
            // To get the width of image.
            BufferedImage readImage = ImageIO.read(Screen);
            int Width = readImage.getWidth();
            // Create filename according to screenshot comparision criteria
            String NewFilename = Width + "_" + Filename + "_" + Browser + SiteLevel;
            // Save screenshot
            FileUtils.copyFile(Screen, new File(BasePath + path + "\\" + Filename + "\\" + NewFilename + ".png"));
        }
    }

    // To login the BrowserStack site.
    public void LoginBrowserStack(String[][] Selections) throws IOException {
        TestOnChrome();
        // Wait for particular element to load.
        wait = new WebDriverWait(driver, timeoutOfOneElement);

        // Reach at snapshot page.
        driver.get("http://www.browserstack.com/users/sign_in");
        driver.findElement(By.id("user_email_login")).clear();
        driver.findElement(By.id("user_email_login")).sendKeys(Username);
        driver.findElement(By.id("user_password")).clear();
        driver.findElement(By.id("user_password")).sendKeys(Password);
        driver.findElement(By.xpath("//a[@id='accept-cookie-notification']")).click();
        driver.findElement(By.id("user_submit")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("account-menu-toggle")));
        driver.findElement(By.className("dropdown-toggle")).click();
        driver.findElement(By.linkText("Screenshots")).click();

        // Select browsers
        SetSelectionsOnBrowserStack(Selections);
    }

    // Select the browsers on that screenshots should be taken.
    private void SetSelectionsOnBrowserStack(String[][] Selections) throws IOException {
        // Deselect these browsers, which are in selected state by default.
        List<WebElement> DeselectBrowser = driver.findElements(By.xpath("//span[contains(@class, 'version selected')]"));
        System.out.println(DeselectBrowser.size());
        for (int k = 0; k < DeselectBrowser.size(); k++) {
            DeselectBrowser.get(k).click();
        }
        List<WebElement> DeselectBrowser2 = driver.findElements(By.xpath("//li[@class='browser-version version selected']"));
        System.out.println(DeselectBrowser2.size());
        for (int k = 0; k < DeselectBrowser2.size(); k++) {
            DeselectBrowser2.get(k).click();
        }


        String OS = null;
        String Browser = null;
        String OS_Version = null;
        String Browser_Version = null;

        // Browsers on that screenshot should be taken.
        String GetSelection[] = new String[4];
        for(int i=0; i<Selections.length; i++) {
            for(int j=0; j<4; j++) {
                GetSelection[j]  = Selections[i][j];
            }
            OS = GetSelection[0];
            OS_Version = GetSelection[1];
            Browser = GetSelection[2];
            Browser_Version = GetSelection[3];
            WebElement selectBrowser = driver.findElement(By.xpath("//div[@class='browser-vendor-box "+ OS +"-"+ OS_Version +"']/child::ul[@class='browser-vendor-list-box']/child::li[@class='browser-versions "+ Browser +"-versions']/child::div[@class='browser-versions-box']/child::ul/child::li[text()='"+ Browser_Version +"']"));
//            wait = new WebDriverWait(driver, timeoutOfOneElement);
//            wait.until(ExpectedConditions.visibilityOf(selectBrowser));
//            selectBrowser.click();
            js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", selectBrowser);
        }
    }

    // To take screenshots through BrowserStack
    public void OnBrowsersByBrowserStack(String[][] FilenameUrl, String SiteLevel, String Path, int NumberOFSelection) throws IOException {
        String Filename = null;
        String Url = null;

        String GetFilenameUrl[] = new String[2];
        for(int i=1; i<FilenameUrl.length; i++) {
            for(int j=0; j<2; j++) {
                GetFilenameUrl[j]  = FilenameUrl[i][j];
            }
            Filename = GetFilenameUrl[0].replace(" ", "").replace("&", "and").replace("_","-");
            Url = GetFilenameUrl[1];

            // Take snapshot.
            WebElement enterUrl = driver.findElement(By.id("screenshots"));
            wait.until(ExpectedConditions.elementToBeClickable(enterUrl)).clear();
            driver.findElement(By.id("screenshots")).sendKeys(Url);
            try{
                driver.findElement(By.id("btn-snapshot")).click();
            }
            catch(org.openqa.selenium.NoSuchElementException ex){
                WebElement arrow = driver.findElement(By.xpath("//span[contains(@class, 'browser-count__text')]"));
                js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", arrow);
            }

            int CountTimeout = 0;
            for (; ;) {
                try {
                    // Driver will wait until text 'ZIPPING' not present at element by id 'zipper'.
                    wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("zipper"), "ZIPPING"));
                    break;
                } catch (Exception e) {
                    CountTimeout++;
                    System.out.println("Timeout" + CountTimeout);
                    // Reload page
                    driver.navigate().refresh();
                }
            }

            // Click download link to save file.
            List<WebElement> DownloadLink = driver.findElements(By.xpath("//a[contains(@class, 'icon icon--download')]"));
            try{
                for (int k = 0; k < DownloadLink.size(); k++) {
                    DownloadLink.get(k).click();
                }
            } catch(org.openqa.selenium.StaleElementReferenceException ex){
//                for (int k = 0; k < DownloadLink.size(); k++) {
//                    DownloadLink.get(k).click();
//                }
            }
            // Check download process finish.
            File[] Files = CheckDownloadFinish(NumberOFSelection);

            // Rename image files.
            RenameFiles(Filename, Files, SiteLevel);

            // Cut all files and paste into new folder.
            CutPasteFiles(Filename, Path);

            // Click arrow symbol ^ to click screenshot button .
//            wait = new WebDriverWait(driver, 60);
//            WebElement arrow = driver.findElement(By.xpath("//div[contains(@class, 'browser-list--hidden::after')]"));
//            wait.until(ExpectedConditions.visibilityOf(arrow)).click();
            WebElement arrow = driver.findElement(By.xpath("//span[contains(@class, 'browser-count__text')]"));
            js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", arrow);
        }
    }

    // Wait for download completion of screenshots.
    private File[] CheckDownloadFinish(int NumberOfSelection) throws IOException {
        File[] ListOfFiles;
        // Loop will continue till the download process of files complete.
        for (; ; ) {
            // List only those files that have extension “.png” and “.jpg”.
            FilenameFilter fileNameFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.lastIndexOf('.') > 0) {
                        // get last index for '.' char
                        int lastIndex = name.lastIndexOf('.');
                        // get extension
                        String str = name.substring(lastIndex);
                        // match path name extension
                        if (str.equals(".jpg")) {
                            return true;
                        }
                        if (str.equals(".png")) {
                            return true;
                        }
                    }
                    return false;
                }
            };
            // File object representing the directory on the disk.
            File Folder = new File(BasePath);
            // Returns the array of files of particular extensions.
            ListOfFiles = Folder.listFiles(fileNameFilter);

            /*
            TODO: If snapshots are taking time to downloaded than again try to download them.
            */
            // If any file have "crdownload" text in name, it means download process not complete.
            if (ListOfFiles.length == NumberOfSelection) {
                int Flag = 1;
                for (int i = 0; i < ListOfFiles.length; i++) {
                    if (ListOfFiles[i].getName().contains("crdownload"))
                        Flag = 0;
                }
                if (Flag == 1) {
                    break;
                }
            }
            System.out.println("Waiting");
        }
        return ListOfFiles;
    }

    // Change name of screenshots according to screenshot comparison criteria.
    private void RenameFiles(String Filename, File[] Files, String SiteLevel) throws IOException {
        for (int i=0 ; i < Files.length ; i++){
            String FileWithoutExtension = Files[i].getName().substring(0, Files[i].getName().indexOf("."));
            String[] SplitFilename = FileWithoutExtension.split("_");
            System.out.println(SplitFilename.length);
            String[] WindowBrowserVersion = new String[3];
            for (int j = 0; j < SplitFilename.length ; j++) {
                WindowBrowserVersion[j] = SplitFilename[j];
            }

            // To get the width of image.
            BufferedImage readImage = ImageIO.read(Files[i]);
            int Width = readImage.getWidth();

            Files[i].renameTo(new File(BasePath+ Width + "_" + Filename + "_" + WindowBrowserVersion[0] + "-" + WindowBrowserVersion[1] + "-" + WindowBrowserVersion[2] + "-" + SiteLevel + ".png"));
        }
    }

    // Cut the screenshots files and paste into newly created directory or into existing directory and replace the existing screenshots.
    private void CutPasteFiles(String Filename, String Path) throws IOException {
        // List only those renamed files that have extension “.png” and “.jpg”.
        FilenameFilter fileNameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.lastIndexOf('.')>0) {
                    // get last index for '.' char
                    int lastIndex = name.lastIndexOf('.');
                    // get extension
                    String str = name.substring(lastIndex);
                    // match path name extension
                    if(str.equals(".jpg")) {
                        return true;
                    }
                    if(str.equals(".png")) {
                        return true;
                    }
                }
                return false;
            }
        };
        // File object representing the directory on the disk.
        File Folder = new File(BasePath);
        // Returns the array of files of particular extensions.
        File[] ListOfFiles = Folder.listFiles(fileNameFilter);

        // To create the directory.
        File Dir = new File(BasePath + Path + "/" + Filename);
        if (!Dir.exists()) {
            Dir.mkdir();
        }
//        System.out.println("*******check1**********");
//        System.out.println("ListOfFiles: "+ListOfFiles);
//        System.out.println("ListOfFiles[0]: "+ListOfFiles[0]);
//        System.out.println("ListOfFiles[0] Name: "+ListOfFiles[0].getName());
//        System.out.println("Length of Files: " +ListOfFiles.length);
        for (int i=0 ; i < ListOfFiles.length ; i++){
//            System.out.println("********check11**********");

            // To construct the path of file.
            Path Original = Paths.get(BasePath + ListOfFiles[i].getName());
//            System.out.println("Original="+Original);
            Path Destination = Paths.get(BasePath + Path + "/" + Filename + "/" + ListOfFiles[i].getName());
//            System.out.println("Destination="+Destination);
            // To move file from one directory to another and if same name file exist in directory than it will replace.
            Files.move(Original, Destination, StandardCopyOption.REPLACE_EXISTING);
        }
//        System.out.println("**********Check2**********");
    }

    // To logout from the BrowserStack site.
    public void LogoutBrowserStack() throws IOException {
        driver.findElement(By.id("account-menu-toggle")).click();
        driver.findElement(By.linkText("Sign out")).click();
    }

    // To merge two folder into new folder, both folder should have same number of folders with same name.
    public void MergeFolders(String SiteLevel1, String SiteLevel2, String ScreenshotThrough, String Site, String Path) throws IOException {
        // Create folder for both site level
        File ScreenshotComparisionDirectory = new File(BasePath + Path + Site + "_" + SiteLevel1 + ScreenshotThrough + "_" + SiteLevel2 + ScreenshotThrough);
        ScreenshotComparisionDirectory.mkdir();

        File SiteLevel1Folder = new File(BasePath + Path + SiteLevel1 + ScreenshotThrough);
        File SiteLevel2Folder = new File(BasePath + Path + SiteLevel2 + ScreenshotThrough);
        // List folders of Sitelevel1 folder
        File[] SiteLevel1FolderFolders = SiteLevel1Folder.listFiles();
        // List folders of Sitelevel2 folder
        File[] SiteLevel2FolderFolders = SiteLevel2Folder.listFiles();

        File[] LoopForSiteLevel = null;
        if (SiteLevel1FolderFolders.length <= SiteLevel2FolderFolders.length) {
            LoopForSiteLevel = SiteLevel1FolderFolders;
        } else {
            LoopForSiteLevel = SiteLevel2FolderFolders;
        }

        for (int i=0 ; i < LoopForSiteLevel.length ; i++){
            // Create folder in folder of both site level
            File ScreenshotDirectory = new File(BasePath + Path + Site + "_" + SiteLevel1 + ScreenshotThrough + "_" + SiteLevel2 + ScreenshotThrough + "/" + LoopForSiteLevel[i].getName());
            ScreenshotDirectory.mkdir();

            // List files of folder in Sitelevel1 folder
            File[] SiteLevel1FolderFolderFiles = SiteLevel1FolderFolders[i].listFiles();
            // List files of folder in Sitelevel2 folder
            File[] SiteLevel2FolderFolderFiles = SiteLevel2FolderFolders[i].listFiles();

            for (int j=0 ; j < SiteLevel1FolderFolderFiles.length ; j++){
                // To construct the path of file.
                Path Original = Paths.get(BasePath + Path + SiteLevel1 + ScreenshotThrough + "/" + SiteLevel1FolderFolders[i].getName() + "/" + SiteLevel1FolderFolderFiles[j].getName());
                Path Destination = Paths.get(BasePath + Path + Site + "_" + SiteLevel1 + ScreenshotThrough + "_" + SiteLevel2 + ScreenshotThrough + "/" + SiteLevel1FolderFolders[i].getName() + "/" + SiteLevel1FolderFolderFiles[j].getName());
                // To move file from one directory to another and if same name file exist in directory than it will replace.
                Files.copy(Original, Destination, StandardCopyOption.REPLACE_EXISTING);
            }
            for (int k=0 ; k < SiteLevel2FolderFolderFiles.length ; k++){
                // To construct the path of file.
                Path Original = Paths.get(BasePath + Path + SiteLevel2 + ScreenshotThrough + "/" + SiteLevel2FolderFolders[i].getName() + "/" + SiteLevel2FolderFolderFiles[k].getName());
                Path Destination = Paths.get(BasePath + Path + Site + "_" + SiteLevel1 + ScreenshotThrough + "_" + SiteLevel2 + ScreenshotThrough + "/" + SiteLevel2FolderFolders[i].getName() + "/" + SiteLevel2FolderFolderFiles[k].getName());
                // To move file from one directory to another and if same name file exist in directory than it will replace.
                Files.copy(Original, Destination, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    /**
     * In gallery, images are display in group of two-two according to width in image name. If four or more images have equal width
     * in their name than they will not display properly. So, it required to change the width in image name.
     */
    public void ChangeWidth(String SiteLevel1, String SiteLevel2, String ScreenshotThrough, String Site, String path) throws IOException {
        File Folder = new File(BasePath + path + Site + "_" + SiteLevel1 + ScreenshotThrough + "_" + SiteLevel2 + ScreenshotThrough);
        File[] FolderFolders = Folder.listFiles();

        for (int i=0 ; i < FolderFolders.length ; i++) {
            File FolderFolder = new File(BasePath + path + Site + "_" + SiteLevel1 + ScreenshotThrough + "_" + SiteLevel2 + ScreenshotThrough + "/" + FolderFolders[i].getName());
            File[] FolderFolderFiles = FolderFolder.listFiles();

            // Store unique width
            TreeSet<String> UniqueWidth = new TreeSet<String>();
            for (int j=0 ; j < FolderFolderFiles.length ; j++) {
                UniqueWidth.add(FolderFolderFiles[j].getName().substring(0, FolderFolderFiles[j].getName().indexOf("_")));
            }

            // Iterate unique width one by one
            List<String> RepeatedWidth = new ArrayList<String>();
            Iterator<String> IterateWidth = UniqueWidth.iterator();
            while (IterateWidth.hasNext()) {
                int Count = 0;
                String Width = IterateWidth.next();
                for (int k=0 ; k < FolderFolderFiles.length ; k++) {
                    if (FolderFolderFiles[k].getName().contains(Width)) {
                        Count++;
                    }
                }
                // Store repeated width
                if (Count > 2) {
                    RepeatedWidth.add(Width);
                }
            }

            // Iterate repeated width one by one
            TreeSet<String> UniqueBrowserOfUniqueWidth = new TreeSet<String>();
            for (int k = 0; k < RepeatedWidth.size(); k++) {
                // Store unique browser
                for (int l=0 ; l < FolderFolderFiles.length ; l++) {
                    if (FolderFolderFiles[l].getName().contains(RepeatedWidth.get(k))) {
                        String S1 = FolderFolderFiles[l].getName().substring(StringUtils.ordinalIndexOf(FolderFolderFiles[l].getName(), "_", 2) + 1);
                        String S2 = S1.substring(0, S1.indexOf("."));
                        String RemoveSiteLevel = new String();
                        if (S2.contains(SiteLevel1)) {
                            RemoveSiteLevel = S2.replace(SiteLevel1, "");
                        } else if (S2.contains(SiteLevel2)) {
                            RemoveSiteLevel = S2.replace(SiteLevel2, "");
                        }
                        UniqueBrowserOfUniqueWidth.add(RemoveSiteLevel);
                    }
                }
                // Iterate unique browser one by one
                int IncreaseWidth = 0;
                Iterator<String> IterateBrowser = UniqueBrowserOfUniqueWidth.iterator();
                while (IterateBrowser.hasNext()) {
                    String Browser = IterateBrowser.next();
                    for (int m = 0 ; m < FolderFolderFiles.length ; m++) {
                        if (FolderFolderFiles[m].getName().contains(Browser)) {
                            int NewWidth = Integer.parseInt(RepeatedWidth.get(k)) + IncreaseWidth;
                            String RenameFile = FolderFolderFiles[m].getName().replace(RepeatedWidth.get(k), String.valueOf(NewWidth));
                            // Rename filename with new width
                            FolderFolderFiles[m].renameTo(new File(BasePath + path + Site + "_" + SiteLevel1 + ScreenshotThrough + "_" + SiteLevel2 + ScreenshotThrough + "/" + FolderFolders[i].getName() + "/" + RenameFile));
                        }
                    }
                    IncreaseWidth++;
                }
                UniqueBrowserOfUniqueWidth.clear();
            }
        }
    }

    /**
     * After screenshot comparision two file will generate but their name not correct to generate gallery. So, it required to
     * change both filename.
     */
    public void RenameFilesAfterScreenshotComparision(String Path) throws IOException {
        // File object representing the directory on the disk.
        File Folder = new File(BasePath + Path);
        // Returns the array of files of particular extensions.
        File[] List_Folders = Folder.listFiles();

        for (int i=0 ; i < List_Folders.length ; i++) {
            File Files = new File(BasePath + Path + "/" + List_Folders[i].getName());
            File[] List_Files = Files.listFiles();

            for (int j=0 ; j < List_Files.length ; j++) {
                File Old_Name = new File(BasePath + Path + "/" + List_Folders[i].getName() + "/" + List_Files[j].getName());
                String FileNameWithOutExt = FilenameUtils.removeExtension(Old_Name.getName());

                if(FileNameWithOutExt.contains("diff")) {
                    String S1 = List_Files[j].getName().substring(StringUtils.ordinalIndexOf(List_Files[j].getName(), "_", 2) + 1);
                    String S2 = S1.substring(0, S1.indexOf("."));
                    String FileNameWithOutSpace = FileNameWithOutExt.replace(" ", "").replace("&", "and").replace(S2, "diff");
                    Old_Name.renameTo(new File(BasePath + Path + "/" + List_Folders[i].getName() + "/" + FileNameWithOutSpace + ".png"));
                }
                if(FileNameWithOutExt.contains("data")) {
                    String S1 = List_Files[j].getName().substring(StringUtils.ordinalIndexOf(List_Files[j].getName(), "_", 2) + 1);
                    String S2 = S1.substring(0, S1.indexOf("."));
                    String FileNameWithOutSpace = FileNameWithOutExt.replace(" ", "").replace("&", "and").replace(S2, "data");
                    Old_Name.renameTo(new File(BasePath + Path + "/" + List_Folders[i].getName() + "/" + FileNameWithOutSpace + ".txt"));
                }
            }
        }
    }

    // To login the site.
    public void Login(String UserName, String Password, String BaseUrl) throws IOException {
        driver.get(BaseUrl);
        driver.findElement(By.linkText("LOG IN")).click();
        driver.findElement(By.id("edit-name")).clear();
        driver.findElement(By.id("edit-name")).sendKeys(UserName);
        driver.findElement(By.id("edit-pass")).clear();
        driver.findElement(By.id("edit-pass")).sendKeys(Password);
        driver.findElement(By.xpath("//input[@class='form-submit']")).click();
    }
}

package Screenshot;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Point;
import org.openqa.selenium.Dimension;

public class HCL_Screens_Compare {
    private WebDriver driver;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Test
    @Parameters("browser")
    public void testSecond(String browser) throws Exception {
        int dimension;
        String path;

        if(browser.equalsIgnoreCase("desktop")) {
            dimension = 1200;
            path = "DesktopView";
            pre_output(dimension, path);
        }
        else if (browser.equalsIgnoreCase("tablet")){
            dimension = 600;
            path = "TabletView";
            pre_output(dimension, path);
        }
        else if (browser.equalsIgnoreCase("mobile")){
            dimension = 300;
            path = "MobileView";
            pre_output(dimension, path);
        }
    }

    private void pre_output(int dimension, String path) throws Exception {
        String filename = null;
        String url = null;

        String file_url[][] = new String[][]{
                {"Financial Services_Partnerships & Alliances","http://hcltechd7.innoraft.com/financial-services/partnerships-alliances"},
                {"Financial Services_Customer testimonial","http://hcltechd7.innoraft.com/financial-services/customer-testimonials"},
                {"Financial Services_Resource Library","http://hcltechd7.innoraft.com/resources/all?asmSelect0=Select%2Ba%2BValue&asmSelect1=Select%2Ba%2BValue&tid_1%255B%255D=11"},
                {"Financial Services_Resource Library_Brochures","http://hcltechd7.innoraft.com/resources/brochure?asmSelect0=Select%2Ba%2BValue&asmSelect1=Select%2Ba%2BValue&tid_1%255B%255D=11"},
                {"Financial Services_Resource Library_Case Studies","http://hcltechd7.innoraft.com/resources/success_story?asmSelect0=Select%2Ba%2BValue&asmSelect1=Select%2Ba%2BValue&tid_1%255B%255D=11"},
                {"Financial Services_Resource Library_News & Media","http://hcltechd7.innoraft.com/media/all?tid_2=All&asmSelect0=Select%20a%20Value&asmSelect1=Select%20a%20Value&tid_1%5B0%5D=11"},
                {"Financial Services_Resource Library_White Papers","http://hcltechd7.innoraft.com/resources/whitepaper?asmSelect0=Select%2Ba%2BValue&asmSelect1=Select%2Ba%2BValue&tid_1%255B%255D=11"}
        };

        String list[] = new String[2];
        for(int i=0; i<7; i++) {
            for(int j=0; j<2; j++) {
                list[j]  = file_url[i][j];
            }
            filename = list[0];
            url = list[1];
            get_image(dimension, path, filename, url);
        }
    }

    private void get_image(int dimension, String path, String filename, String url) throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(dimension, dimension));
        driver.get(url);

        File screen = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);
        String file_name1 = "C:\\hcltechd7Screenshot2\\" + path + "\\" + filename + ".png";
        FileUtils.copyFile(screen, new File(file_name1));
        String file_name2 = "C:\\hcltechd7Screenshot1\\" + path + "\\" + filename + ".png";

        driver.quit();

        imageCompare(file_name1, file_name2, path);
    }

    private void imageCompare(String file1, String file2, String path) throws Exception {
        int width;
        int height;
        boolean imagesEqual = true;

        File f1 = new File(file1);
        f1.setReadable(true, true);
        f1.setExecutable(true);
        f1.setWritable(true,true);
        Image image = ImageIO.read(f1);
        BufferedImage image1 = (BufferedImage) image;

        File f2 = new File(file2);
        image = ImageIO.read(f2);
        BufferedImage image2 = (BufferedImage) image;

        if( image1.getWidth()  == ( width  = image2.getWidth() ) &&
                image1.getHeight() == ( height = image2.getHeight() ) ){

            outer_loop:
            for(int x = 0; imagesEqual == true && x < width ; x++){
                for(int y = 0; imagesEqual == true && y < height ; y++) {
                    // getRGB(x,y) return color in a single integer value of pixel location
                    if( image1.getRGB(x, y) != image2.getRGB(x, y) ) {
                        System.out.println("Image not same");
                        // function is used to write coordinate in a file where image is not match
                        write_file("x:"+x+",y:"+y, file1, path);
                        break outer_loop;
                    }
                }
            }
        }else{
            write_file("Dimension not correct",file1,path);
        }
    }

    private void write_file(String message,String imagename,String path) throws Exception{
        // file where coordinate of image difference save
        File file= new File("C:\\hcltechd7Screenshot2\\".concat(path).concat(".txt"));
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile(),true); // true is for append content
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(imagename);
        bw.write("(" + message + ")");
        bw.newLine();
        bw.close();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}

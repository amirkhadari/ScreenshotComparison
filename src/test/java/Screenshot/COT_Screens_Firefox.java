package Screenshot;

/**
 * Created by om on 12/6/13.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;

import static org.junit.Assert.*;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class COT_Screens_Firefox {
    ScreenshotFunctions func;
    String Browser = "Firefox";
    String ScreenshotThrough = "Driver";
    String ReadFilenameAndUrlFrom = "Array";
    String Path = "TestingFiles\\Screenshots\\COT\\";

    String SheetName = "Sheet1";
    String ExcelFilePath = "C:\\Users\\om\\Downloads\\TestingFiles\\Screenshots\\";

    @Test
    @Parameters({"Role","Username","Password", "BaseUrl", "SiteLevel", "ExcelFile"})
    public void testFirst(String Role, String Username, String Password, String BaseUrl, String SiteLevel, String ExcelFile) throws Exception {
        func = new ScreenshotFunctions();
        // Create the directory for SiteLevel.
        File dir = new File(func.BasePath + Path + SiteLevel + ScreenshotThrough);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String[][] FilenameUrl = new String[2000][2];
        // Get Title and Url
        if (ReadFilenameAndUrlFrom == "Excel") {
            FilenameUrl = ReadExcel(ExcelFile);
        } else {
            FilenameUrl = ReadArray(BaseUrl, Role);
        }

        func.TestOnFirefox();
        func.Login(Username, Password, BaseUrl);
        func.OnBrowsersByWebDriver(FilenameUrl, SiteLevel, Path + SiteLevel + ScreenshotThrough + "\\" + Role + "\\", Browser);
    }

    private String[][] ReadArray(String BaseUrl, String Role) throws IOException {
        if (Role.contains("Student")) {
            return new String[][]{
                    {"Learning Styles Inventory", "" + BaseUrl + "/learning-styles"},
                    {"Do What You Are", "" + BaseUrl + "/do-what-you-are"},
                    {"Multiple Intelligences", "" + BaseUrl + "/multiple-intelligences"},
                    {"Search", "" + BaseUrl + "/college-search"},
                    {"FactSheet", "" + BaseUrl + "/factsheets/1100369"},
                    {"WishList","" + BaseUrl + "/college-wishlist"},
                    {"Course Requirement", "" + BaseUrl + "/course-requirements"},
                    {"Essay", "" + BaseUrl + "/essay"},
                    {"College Visits", "" + BaseUrl + "/college-visits-student"},
                    {"Course Planner", "" + BaseUrl + "/student-planner"},
                    {"Add To Planner", "" + BaseUrl + "/node/add/student-planner?stud=10290"},
                    {"ITasks", "" + BaseUrl + "/long-term-planner"},
                    {"Graduation Requirement", "" + BaseUrl + "/school-grad-req"},
                    {"Graduation Requirement_Course Catalog", "" + BaseUrl + "/course-catalog"},
                    {"Journal", "" + BaseUrl + "/journals"},
                    {"Journal_New Journal Entry", "" + BaseUrl + "/node/add/journal?destination=journals"},
                    {"Journal_Edit Journal Entry", "" + BaseUrl + "/node/18482/edit"},
                    {"Showcases", "" + BaseUrl + "/showcase"},
                    {"Showcases_Edit Showcase", "" + BaseUrl + "/node/18483/edit?destination=showcase"},
                    {"Showcases_New Showcase", "" + BaseUrl + "/node/add/showcase?destination=showcase"},
                    {"Resume", "" + BaseUrl + "/resume"},
                    {"Resume_Edit Resume", "" + BaseUrl + "/node/17434/edit?destination=resume"},
                    {"Activities", "" + BaseUrl + "/activities"},
                    {"Docushare", "" + BaseUrl + "/document-locker"},
                    {"Assigned Lessons", "" + BaseUrl + "/assigned-lesson"},
                    {"Assigned Lessons_Static Load", "" + BaseUrl + "/lesson-unit/75626/75627"},
                    {"Homework Assignments", "" + BaseUrl + "/lp-assignments-students"},
                    {"Homework Assignments_Submitted Homework", "" + BaseUrl + "/submitted-homework"},
                    {"Homework Assignments_Physics Learning Plan", "" + BaseUrl + "/node/19524"},
                    {"Assigned Tests", "" + BaseUrl + "/onlinetest-assigned-student"},
                    {"Assigned Tests_Submitted Test", "" + BaseUrl + "/submitted-onlinetest-student"},
                    {"Khan Academy", "" + BaseUrl + "/khan-academy"},
                    {"My Grades", "" + BaseUrl + "/student-view"},
                    {"Grade Percentage", "" + BaseUrl + "/grade-percentages"},
                    {"Dashboard", "" + BaseUrl + "/students-dashboard"},
                    {"Dashboard_Student Profile", "" + BaseUrl + "/students-dashboard?app=profile"},
                    {"Dashboard_Current Enrolled Classes", "" + BaseUrl + "/students-dashboard?app=schedule"},
                    {"Dashboard_Grades", "" + BaseUrl + "/students-dashboard?app=grades"},
                    {"Dashboard_Scores", "" + BaseUrl + "/students-dashboard?app=scores"},
                    {"Dashboard_Colleges", "" + BaseUrl + "/students-dashboard?app=colleges"},
                    {"Dashboard_College Visits", "" + BaseUrl + "/students-dashboard?app=college-visits"},
                    {"Dashboard_Scholarships", "" + BaseUrl + "/students-dashboard?app=scholarship-wishlist"},
                    {"Dashboard_SMS", "" + BaseUrl + "/students-dashboard?app=sms"},
                    {"Dashboard_Course Request", "" + BaseUrl + "/students-dashboard?app=course-request"},
                    {"Scholarship Search", "" + BaseUrl + "/scholarship-search"},
                    {"Scholarship Wishlist", "" + BaseUrl + "/scholarship-wishlist"},
                    {"Dialog", "" + BaseUrl + "/messages"},
                    {"Broadcast", "" + BaseUrl + "/messages/broadcast"},
                    {"Survey", "" + BaseUrl + "/my-surveys"},
                    {"Survey_Dummy Survey", "" + BaseUrl + "/survey/32"},
                    {"Mindset For Success","" + BaseUrl + "/college-readiness"},
                    {"Admission Tests", "" + BaseUrl + "/college-readiness/admissions-tests"},
                    {"Financial Aid", "" + BaseUrl + "/college-readiness/financial-aid"},
                    {"Admissions Factors", "" + BaseUrl + "/college-readiness/admissions-factors"},
                    {"Purpose Navigator", "" + BaseUrl + "/purpose-navigator"},
                    {"Test Prep", "" + BaseUrl + "/testprep"}
            };
        } else if (Role.contains("Teacher")) {
            return new String[][]{
                    {"Learning Styles Inventory", "" + BaseUrl + "/learning-styles"},
                    {"Do What You Are", "" + BaseUrl + "/do-what-you-are"},
                    {"Multiple Intelligences", "" + BaseUrl + "/multiple-intelligences"},
                    {"Search", "" + BaseUrl + "/college-search"},
                    {"FactSheet", "" + BaseUrl + "/factsheets/1100369"},
                    {"WishList","" + BaseUrl + "/college-wishlist"},
                    {"Course Requirement", "" + BaseUrl + "/course-requirements"},
                    {"Essay", "" + BaseUrl + "/essay"},
                    {"Course Planner", "" + BaseUrl + "/student-planner"},
                    {"Add To Planner", "" + BaseUrl + "/node/add/student-planner?stud=20662"},
                    {"ITasks", "" + BaseUrl + "/long-term-planner"},
                    {"ITasks_Add Milestone", "" + BaseUrl + "/iplan/add/20662"},
                    {"Graduation Requirement", "" + BaseUrl + "/school-grad-req"},
                    {"Graduation Requirement_Course Catalog", "" + BaseUrl + "/course-catalog"},
                    {"Journal", "" + BaseUrl + "/journals"},
                    {"Showcases", "" + BaseUrl + "/showcase"},
                    {"Resume", "" + BaseUrl + "/resume"},
                    {"Activities", "" + BaseUrl + "/activities"},
                    {"Docushare", "" + BaseUrl + "/document-locker"},
                    {"Digital Lessons", "" + BaseUrl + "/lesson-library"},
                    {"Digital Lessons_Add a New Unit or Lesson", "" + BaseUrl + "/lesson-unit/create"},
                    {"Digital Lessons_Static and Dynamic Loads", "" + BaseUrl + "/lesson-unit/75626/75627"},
                    {"Homework Library", "" + BaseUrl + "/teacher-class-assignments"},
                    {"Homework Library_Create New Homework Assignment", "" + BaseUrl + "/node/add/learning-plan"},
                    {"Homework Library_Edit Homework Library", "" + BaseUrl + "/node/81350/edit"},
                    {"Homework Library_Biology pla", "" + BaseUrl + "/node/81350"},
                    {"Online Tests", "" + BaseUrl + "/onlinetest-teacher"},
                    {"Online Tests_Create Online Tests", "" + BaseUrl + "/onlinetest/new/title"},
                    {"Online Tests_Dummy Online Tests", "" + BaseUrl + "/onlinetest/101716"},
                    {"Online Tests_Assign", "" + BaseUrl + "/onlinetest/101716/assign"},
                    {"Assigned Homework", "" + BaseUrl + "/teacher-progress"},
                    {"Khan Academy", "" + BaseUrl + "/khan-academy"},
                    {"Assignments", "" + BaseUrl + "/my-assignments"},
                    {"View Grades", "" + BaseUrl + "/grade-book/teacher-view"},
                    {"Submit Grades", "" + BaseUrl + "/grade-book/submit-grade"},
                    {"Grade Percentage", "" + BaseUrl + "/grade-percentages"},
                    {"Grading Standards", "" + BaseUrl + "/grading-standard"},
                    {"Dashboard", "" + BaseUrl + "/students-dashboard"},
                    {"Dashboard_Student Profile", "" + BaseUrl + "/students-dashboard?app=profile"},
                    {"Dashboard_Current Enrolled Classes", "" + BaseUrl + "/students-dashboard?app=schedule"},
                    {"Dashboard_Grades", "" + BaseUrl + "/students-dashboard?app=grades"},
                    {"Dashboard_Scores", "" + BaseUrl + "/students-dashboard?app=scores"},
                    {"Dashboard_Colleges", "" + BaseUrl + "/students-dashboard?app=colleges"},
                    {"Dashboard_College Visits", "" + BaseUrl + "/students-dashboard2?app=college-visits"},
                    {"Dashboard_Scholarships", "" + BaseUrl + "/students-dashboard2?app=scholarship-wishlist"},
                    {"Dashboard_Class Schedule", "" + BaseUrl + "/students-dashboard?app=my-schedule"},
                    {"Dashboard_SMS", "" + BaseUrl + "/students-dashboard?app=messages"},
                    {"Dashboard_Risk", "" + BaseUrl + "/students-dashboard?app=risk"},
                    {"Dashboard_Student Log", "" + BaseUrl + "/students-dashboard?app=student-log"},
                    {"Reports", "" + BaseUrl + "/teacher-reports"},
                    {"Reports_Student Activities", "" + BaseUrl + "/teacher-reports?app=student-activities"},
                    {"Reports_Admission Statistics", "" + BaseUrl + "/teacher-reports?app=admission-statistics"},
                    {"Reports_Colleges Students Attending", "" + BaseUrl + "/teacher-reports?app=students-attending"},
                    {"Reports_College Visits Activities", "" + BaseUrl + "/teacher-reports?app=college-visits"},
                    {"Reports_Grades & Test Score", "" + BaseUrl + "/teacher-reports?app=test-scores"},
                    {"Scholarship Search", "" + BaseUrl + "/scholarship-search"},
                    {"Scholarship Wishlist", "" + BaseUrl + "/scholarship-wishlist"},
                    {"Attendance", "" + BaseUrl + "/attendance"},
                    {"Dialog", "" + BaseUrl + "/messages"},
                    {"Broadcast", "" + BaseUrl + "/messages/broadcast"},
                    {"Survey", "" + BaseUrl + "/surveys"},
                    {"Survey_Create Survey", "" + BaseUrl + "/survey/new/edit"},
                    {"Survey_Assign Survey", "" + BaseUrl + "/node/add/school-admin-survey?destination=surveys"},
                    {"Survey_View Survey", "" + BaseUrl + "/survey/32/responses"},
                    {"Survey_Edit Survey", "" + BaseUrl + "/survey/31/edit"},
                    {"Mindset For Success","" + BaseUrl + "/college-readiness"},
                    {"Admission Tests", "" + BaseUrl + "/college-readiness/admissions-tests"},
                    {"Financial Aid", "" + BaseUrl + "/college-readiness/financial-aid"},
                    {"Admissions Factors", "" + BaseUrl + "/college-readiness/admissions-factors"},
                    {"Purpose Navigator", "" + BaseUrl + "/purpose-navigator"},
                    {"Progress", "" + BaseUrl + "/pn-my-students"},
                    {"Faculty & Students", "" + BaseUrl + "/my-students"},
                    {"Faculty & Students_My Page", "" + BaseUrl + "/my-schedule-page"}
            };
        } else if (Role.contains("SchoolAdmin")) {
            return new String[][]{
                    {"Learning Styles Inventory", "" + BaseUrl + "/learning-styles/10294"},
                    {"Do What You Are", "" + BaseUrl + "/do-what-you-are/10294"},
                    {"Multiple Intelligences", "" + BaseUrl + "/multiple-intelligences/10294"},
                    {"Search", "" + BaseUrl + "/college-search"},
                    {"FactSheet", "" + BaseUrl + "/factsheets/1100369"},
                    {"WishList","" + BaseUrl + "/college-wishlist/10294"},
                    {"Course Requirement", "" + BaseUrl + "/course-requirements"},
                    {"Essay", "" + BaseUrl + "/essay/10294"},
                    {"Course Planner", "" + BaseUrl + "/student-planner"},
                    {"Add To Planner", "" + BaseUrl + "/node/add/student-planner?stud=10294"},
                    {"ITasks", "" + BaseUrl + "/long-term-planner/10294"},
                    {"ITasks_Add Milestone", "" + BaseUrl + "/iplan/add/10294"},
                    {"ITasks_Admin Iplan Page", "" + BaseUrl + "/long-term-planner-admin"},
                    {"ITasks_Admin Iplan Page_Graduation Requirement", "" + BaseUrl + "/grad-req"},
                    {"ITasks_Admin Iplan Page_Course Catalog", "" + BaseUrl + "/school-content-course"},
                    {"ITasks_Admin Iplan Page_Course Catalog_Edit Course Catalog", "" + BaseUrl + "/node/17187/edit?destination=school-content-course"},
                    {"ITasks_Admin Iplan Page_Required Courses", "" + BaseUrl + "/mandatory-planner"},
                    {"ITasks_Admin Iplan Page_Teacher Schedules", "" + BaseUrl + "/teacher-schedule"},
                    {"ITasks_Admin Iplan Page_Class Scheduler", "" + BaseUrl + "/student-relocate/High"},
                    {"ITasks_Admin Iplan Page_iPlan", "" + BaseUrl + "/long-term-planner-admin"},
                    {"ITasks_Admin Iplan Page_iPlan_Add Milestone", "" + BaseUrl + "/iplan/add/"},
                    {"ITasks_Admin Iplan Page_iPlan Setup", "" + BaseUrl + "/long-term-planner-config"},
                    {"ITasks_Admin Iplan Page_Course Requirement", "" + BaseUrl + "/graduation_requirement_tracking"},
                    {"ITasks_Admin Iplan Page_Student Log Setup", "" + BaseUrl + "/discipline-admin"},
                    {"ITasks_Admin Iplan Page_Grade Percentage", "" + BaseUrl + "/grade-percentage"},
                    {"Graduation Requirement", "" + BaseUrl + "/school-grad-req"},
                    {"Graduation Requirement_Course Catalog", "" + BaseUrl + "/course-catalog"},
                    {"Journal", "" + BaseUrl + "/journals/10294"},
                    {"Journal_Alisha Kapoor", "" + BaseUrl + "/users/alisha-kapoor"},
                    {"Journal_Basketball—The Importance of Team Chemistry", "" + BaseUrl + "/node/21921"},
                    {"Journal_Basketball—The Importance of Team Chemistry_A Stanford Hospital Junior Volunteer", "" + BaseUrl + "/node/21920"},
                    {"Journal_Basketball—The Importance of Team Chemistry_Holiday Cheer", "" + BaseUrl + "/node/20378"},
                    {"Journal_Basketball—The Importance of Team Chemistry_September 2013 (2)", "" + BaseUrl + "/archives/10294/201309"},
                    {"Journal_Basketball—The Importance of Team Chemistry_August 2013 (1)", "" + BaseUrl + "/archives/10294/201308"},
                    {"Showcases", "" + BaseUrl + "/showcase/10294"},
                    {"Resume", "" + BaseUrl + "/resume/10294"},
                    {"Activities", "" + BaseUrl + "/activities/10294"},
                    {"Docushare", "" + BaseUrl + "/document-locker"},
                    {"Digital Lessons", "" + BaseUrl + "/lesson-library"},
                    {"Digital Lessons_Add a New Unit or Lesson", "" + BaseUrl + "/lesson-unit/create"},
                    {"Digital Lessons_Static and Dynamic Loads", "" + BaseUrl + "/lesson-unit/75626/75627"},
                    {"Digital Lessons_test", "" + BaseUrl + "/lesson-unit/75626/912751"},
                    {"Homework Library", "" + BaseUrl + "/teacher-class-assignments"},
                    {"Homework Library_Create New Homework Assignment", "" + BaseUrl + "/node/add/learning-plan"},
                    {"Online Tests", "" + BaseUrl + "/onlinetest-teacher"},
                    {"Online Tests_Create Online Tests", "" + BaseUrl + "/onlinetest/new/title"},
                    {"Assigned Homework", "" + BaseUrl + "/teacher-progress"},
                    {"Khan Academy", "" + BaseUrl + "/khan-academy"},
                    {"Assignments", "" + BaseUrl + "/my-assignments"},
                    {"View Grades", "" + BaseUrl + "/grade-book/teacher-view"},
                    {"Submit Grades", "" + BaseUrl + "/grade-book/submit-grade"},
                    {"Grading Standards", "" + BaseUrl + "/grading-standard"},
                    {"Dashboard", "" + BaseUrl + "/students-dashboard/10294"},
                    {"Dashboard_Student Profile", "" + BaseUrl + "/students-dashboard/10294?app=profile"},
                    {"Dashboard_Current Enrolled Classes", "" + BaseUrl + "/students-dashboard/10294?app=schedule"},
                    {"Dashboard_Grades", "" + BaseUrl + "/students-dashboard/10294?app=grades"},
                    {"Dashboard_Scores", "" + BaseUrl + "/students-dashboard/10294?app=scores"},
                    {"Dashboard_Colleges", "" + BaseUrl + "/students-dashboard/10294?app=colleges"},
                    {"Dashboard_College Visits", "" + BaseUrl + "/students-dashboard/10294?app=college-visits"},
                    {"Dashboard_Scholarships", "" + BaseUrl + "/students-dashboard/10294?app=scholarship-wishlist"},
                    {"Dashboard_Class Schedule", "" + BaseUrl + "/students-dashboard/10294?app=my-schedule"},
                    {"Dashboard_SMS", "" + BaseUrl + "/students-dashboard/10294?app=messages"},
                    {"Dashboard_Course Request", "" + BaseUrl + "/students-dashboard/10294?app=my-course-request"},
                    {"Dashboard_Risk", "" + BaseUrl + "/students-dashboard/10294?app=risk"},
                    {"Dashboard_Student Log", "" + BaseUrl + "/students-dashboard/10294?app=student-log"},
                    {"Reports", "" + BaseUrl + "/teacher-reports"},
                    {"Reports_Student Activities", "" + BaseUrl + "/teacher-reports?app=student-activities"},
                    {"Reports_Admission Statistics", "" + BaseUrl + "/teacher-reports?app=admission-statistics"},
                    {"Reports_Colleges Students Attending", "" + BaseUrl + "/teacher-reports?app=students-attending"},
                    {"Reports_College Visits Activities", "" + BaseUrl + "/teacher-reports?app=college-visits"},
                    {"Reports_Grades & Test Score", "" + BaseUrl + "/teacher-reports?app=test-scores"},
                    {"Scholarship Search", "" + BaseUrl + "/scholarship-search/10294"},
                    {"Scholarship Wishlist", "" + BaseUrl + "/scholarship-wishlist/10294"},
                    {"Attendance", "" + BaseUrl + "/attendance"},
                    {"Charts", "" + BaseUrl + "/attendance-chart"},
                    {"Students Attendance", "" + BaseUrl + "/mark-attendance"},
                    {"Dialog", "" + BaseUrl + "/messages"},
                    {"Broadcast", "" + BaseUrl + "/messages/broadcast"},
                    {"Survey", "" + BaseUrl + "/surveys"},
                    {"Survey_Create Survey", "" + BaseUrl + "/survey/new/edit"},
                    {"Survey_Assign Survey", "" + BaseUrl + "/node/add/school-admin-survey?destination=surveys"},
                    {"Survey_View Survey", "" + BaseUrl + "/survey/51/responses"},
                    {"Survey_Edit Survey", "" + BaseUrl + "/survey/51/edit"},
                    {"Mindset For Success","" + BaseUrl + "/college-readiness"},
                    {"Admission Tests", "" + BaseUrl + "/college-readiness/admissions-tests"},
                    {"Admission Tests_Edit Admission Tests","" + BaseUrl + "/node/14853/edit?college-read-type=admissions-tests"},
                    {"Financial Aid", "" + BaseUrl + "/college-readiness/financial-aid"},
                    {"Financial Aid_Edit Financial Aid", "" + BaseUrl + "/node/14852/edit?college-read-type=financial-aid"},
                    {"Admissions Factors", "" + BaseUrl + "/college-readiness/admissions-factors"},
                    {"Purpose Navigator", "" + BaseUrl + "/purpose-navigator"},
                    {"Progress", "" + BaseUrl + "/pn-my-students"},
                    {"School Setup", "" + BaseUrl + "/import/student_importer"},
                    {"School Setup_Add a Single Student", "" + BaseUrl + "/student-create"},
                    {"School Setup_Teachers", "" + BaseUrl + "/import/teacher_importer"},
                    {"School Setup_Course Catalog", "" + BaseUrl + "/import/course_catalog_content_type_importer"},
                    {"School Setup_Required Courses", "" + BaseUrl + "/import/mandatory_planner_importer"},
                    {"School Setup_Teacher Schedules", "" + BaseUrl + "/import/teacher_assignment_importer"},
                    {"School Setup_Student Schedules", "" + BaseUrl + "/import/feed_teacher_stud_relation"},
                    {"School Setup_Grades", "" + BaseUrl + "/course-marks-import"},
                    {"School Setup_ACT", "" + BaseUrl + "/import/act_importer"},
                    {"School Setup_AP", "" + BaseUrl + "/import/ap_importer"},
                    {"School Setup_PSAT", "" + BaseUrl + "/import/psat_importer"},
                    {"School Setup_SAT", "" + BaseUrl + "/import/sat_importer"},
                    {"School Setup_SAT Subject", "" + BaseUrl + "/import/sat_subject_importer"},
                    {"School Setup_TOEFL", "" + BaseUrl + "/import/toefl_importer"},
                    {"Faculty & Students", "" + BaseUrl + "/my-teachers"},
                    {"Faculty & Students_All Students", "" + BaseUrl + "/my-school-students"},
                    {"Faculty & Students_My Students", "" + BaseUrl + "/my-students"},
                    {"Faculty & Students_My Page", "" + BaseUrl + "/my-schedule-page"},
                    {"Faculty & Students_Promote Students", "" + BaseUrl + "/promote-student"},
                    {"Faculty & Students_Alumni", "" + BaseUrl + "/alumni-student"},
                    {"Transcript", "" + BaseUrl + "/admin-transcript?student=10290"}
            };
        } else {
            return new String[][]{
                    {"All Users", "" + BaseUrl + "/view-all-users"},
                    {"All Schools", "" + BaseUrl + "/view-all-schools"},
                    {"Add District", "" + BaseUrl + "/node/add/district"},
                    {"Add School", "" + BaseUrl + "/node/add/cot-subscriber"},
                    {"Add User", "" + BaseUrl + "/admin/people/create"}
            };
        }
    }

    private String[][] ReadExcel(String ExcelFile) throws IOException, InvalidFormatException {
        FileInputStream FileInput = new FileInputStream(ExcelFilePath + ExcelFile);
        XSSFWorkbook ExcelWBook = new XSSFWorkbook(org.apache.poi.openxml4j.opc.Package.open(FileInput));
        XSSFSheet ExcelWSheet = ExcelWBook.getSheet(SheetName);

        XSSFRow Row = null;
        XSSFCell Cell = null;
        int RowNum = ExcelWSheet.getLastRowNum() + 1;
        int ColNum = ExcelWSheet.getRow(0).getLastCellNum();
        String[][] TitleUrl = new String[RowNum][ColNum];
        for (int i = 1 ; i < RowNum ; i++){
            Row = ExcelWSheet.getRow(i);
            for (int j = 0 ; j < ColNum ; j++) {
                Cell = Row.getCell(j);
                TitleUrl[i][j] = cellToString(Cell);
            }
        }
        return TitleUrl;
    }

    private String cellToString(XSSFCell cell){
        int type;
        Object result;
        type = cell.getCellType();

        switch (type) {
            case XSSFCell.CELL_TYPE_STRING:
                result = cell.getRichStringCellValue().getString();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                result = cell.getNumericCellValue();
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                result = cell.getCellFormula();
                break;
            default:
                throw new RuntimeException("No support for this type of cell");
        }
        return result.toString();
    }
}
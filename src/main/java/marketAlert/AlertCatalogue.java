package marketAlert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import marketAlert.alerts.Alert;
import marketAlert.utils.AlertType;
import marketAlert.utils.FileManager;
import marketAlert.utils.PostAlert;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class AlertCatalogue {

    public List<Alert> alerts;
    protected FileManager fileManager;
    protected PostAlert postAlert;
    Alert alert = new Alert();
    protected AlertType alertType;
    WebDriver driver;
    public int statusCode = 0;
    Boolean added = false;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public HttpResponse<String> response;
    public int alertsFound;

    public int headingsFound;
    public int iconsFound;
    public int descriptionsFound;
    public int urlsFound;
    public int imageUrlsFound;
    public int pricesFound;


    public AlertCatalogue() throws IOException, ParseException {
        alerts = new LinkedList<>();
    }

    public Boolean addAlert() throws IOException, InterruptedException, java.text.ParseException {
        System.setProperty("webdriver.chrome.driver", "/Users/rober/webtesting/chromedriver.exe");
        driver = new ChromeDriver();

        //Go to google and disable cookies dialog
        driver.get("https://www.ultimate.com.mt/product-category/tv-audio/audio/headphones/");


        for(int i=0;i<5;i++) {
            Alert alert = new Alert();
            alert.setHeading(driver.findElements(By.className("woocommerce-loop-product__title")).get(i).getText());
            alert.setPostedBy("ff557502-1ba4-4578-b094-2efdd4375b1d");
            alert.setDescription(driver.findElements(By.cssSelector("#content > div:nth-of-type(2) > div > div > div > section:nth-of-type(4) > div > div > div:nth-of-type(2) > div > div > div > div > div > div > ul > li > a > p")).get(i).getText());
            alert.setImageUrl(driver.findElements(By.cssSelector("#content > div:nth-of-type(2) > div > div > div > section:nth-of-type(4) > div > div > div:nth-of-type(2) > div > div > div > div > div > div > ul > li > a > img:nth-of-type(2)")).get(i).getAttribute("src"));
            alert.setUrl(driver.findElements(By.cssSelector("#content > div:nth-of-type(2) > div > div > div > section:nth-of-type(4) > div > div > div:nth-of-type(2) > div > div > div > div > div > div > ul > li > a")).get(i).getAttribute("href"));


            NumberFormat nf = NumberFormat.getInstance(Locale.UK);
            Number num = nf.parse(driver.findElements(By.className("wccpf_archive_price_tag")).get(i).getAttribute("value"));
            long price = (long) num * 100;
            alert.setPriceInCents((int)price);


            int type = alertType.getAlertType();
            if(type == AlertType.ELECTRONICS) {
                alert.setType(type);
            }
            if (fileManager != null) {
                fileManager.saveProductToFile(alert);

            }

            alerts.add(i, alert);
            saveToJson(alert);
            added =  true;
        }
        Thread.sleep(500);

        return added;
    }



    public void setFileManager(FileManager fileManager) throws IOException {
        this.fileManager = fileManager;
    }

    public void setPostAlert(PostAlert postAlert){
        this.postAlert = postAlert;
    }

    public void setAlertType(AlertType alertType){ this.alertType = alertType;}

    public void saveToJson(Alert alert) throws IOException, InterruptedException {





          FileWriter file = new FileWriter("C:/Users/rober/Desktop/marketAlertTask1/src/main/java/marketAlert/data.json", false);
         FileReader readFile = new FileReader("C:/Users/rober/Desktop/marketAlertTask1/src/main/java/marketAlert/data.json");



               file.write(gson.toJson(alert));




         file.close();
    }


    public int deleteAlerts() throws InterruptedException, IOException {

        URI uri = URI.create("https://api.marketalertum.com/Alert?userId=ff557502-1ba4-4578-b094-2efdd4375b1d");
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .DELETE()
                .uri(uri)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        alerts.clear();

        return response.statusCode();
    }

    public HttpResponse<String> sendRequest() throws IOException, InterruptedException {



        for(int i=0; i<alerts.size(); i++) {
            if (postAlert != null) {
                postAlert.postItem();

            }
            String body = gson.toJson((alerts.get(i)));
            URI uri = URI.create("https://api.marketalertum.com/Alert");
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

             response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            statusCode = response.statusCode();





        }


        return response;

    }

    public void validLogin() throws InterruptedException {
        driver.get("https://www.marketalertum.com/Alerts/Login");
        WebElement searchField = driver.findElement(By.name("UserId"));
        searchField.sendKeys("ff557502-1ba4-4578-b094-2efdd4375b1d");
        WebElement searchButton = driver.findElement(By.xpath("/html/body/div/main/form/input[2]"));
        searchButton.submit();

        Thread.sleep(500);
    }
    public Boolean countAlerts() throws InterruptedException {


        validLogin();

         alertsFound = driver.findElements(By.cssSelector(" tbody ")).size();

        if(alertsFound == alerts.size()) {
            return true;
        }
        return false;


    }

    public Boolean findHeading() throws InterruptedException {

        validLogin();

        int alerts = driver.findElements(By.cssSelector(" tbody ")).size();
        headingsFound =  driver.findElements(By.tagName("h4")).size();
        if(headingsFound == alerts){
            return true;
        }
        else {
            return false;
        }
    }


    public Boolean findIcon() throws InterruptedException {

        validLogin();

        int alerts = driver.findElements(By.cssSelector(" tbody ")).size();
        iconsFound =  driver.findElements(By.cssSelector("tbody > tr:first-of-type > td > h4 > img")).size();

        if(iconsFound == alerts) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean findImage() throws InterruptedException {
        validLogin();
        int alerts = driver.findElements(By.cssSelector(" tbody ")).size();
        imageUrlsFound =  driver.findElements(By.cssSelector("tbody > tr:nth-of-type(2) > td")).size();
        if(imageUrlsFound == alerts){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean findDescription() throws InterruptedException {
        validLogin();

        int alerts = driver.findElements(By.cssSelector(" tbody ")).size();
        descriptionsFound =  driver.findElements(By.cssSelector("tbody > tr:nth-of-type(3) > td")).size();
        if(descriptionsFound == alerts){
            return true;
        }
        else {
            return false;
        }
    }
    public Boolean findLink() throws InterruptedException {

        validLogin();

        int alerts = driver.findElements(By.cssSelector(" tbody ")).size();
        urlsFound =  driver.findElements(By.cssSelector(" tbody > tr:nth-of-type(5) > td")).size();

        if(alerts == urlsFound) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean findPrice() throws InterruptedException {
        validLogin();

        int alerts = driver.findElements(By.cssSelector(" tbody ")).size();
        pricesFound = driver.findElements(By.cssSelector(" tbody > tr:nth-of-type(4) > td")).size();
        if (pricesFound == alerts) {
            return true;
        }
            driver.quit();
            return false;

    }

}

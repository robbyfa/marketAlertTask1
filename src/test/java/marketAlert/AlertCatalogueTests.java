package marketAlert;


import marketAlert.alerts.Alert;
import marketAlert.spies.FileManagerSpy;
import marketAlert.spies.PostSpy;
import marketAlert.utils.AlertType;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;

public class AlertCatalogueTests {


    AlertCatalogue catalogue;
    Alert alert;
    AlertType alertType;
    @BeforeEach
    public void setup() throws IOException, ParseException {
        catalogue = new AlertCatalogue();

        catalogue.setAlertType(alertType);
        AlertType alertType = Mockito.mock(AlertType.class);
        Mockito.when(alertType.getAlertType()).thenReturn(alertType.ELECTRONICS);
        catalogue.setAlertType(alertType);
    }

    @AfterEach
    public void teardown() {
        catalogue = null;
        alert = null;
    }

    @Test
    public void testEmptyAlertCatalogue() {
        //Verify
        Assertions.assertEquals(0, catalogue.alerts.size());
    }

    @Test
    public void testAlertCatalogueWith5Alerts() throws IOException, InterruptedException, java.text.ParseException {

        //setup
        catalogue.setAlertType(alertType);
        AlertType alertType = Mockito.mock(AlertType.class);
        Mockito.when(alertType.getAlertType()).thenReturn(alertType.ELECTRONICS);
        catalogue.setAlertType(alertType);

        //exercise
        Boolean result =  catalogue.addAlert();

        //verify
        Assertions.assertTrue(result);
    }

    @Test
    public void testProductSavedToFile() throws IOException, InterruptedException, java.text.ParseException {
        //Setup

        FileManagerSpy fileManager = new FileManagerSpy();
        catalogue.setFileManager(fileManager);

        //Exercise
        catalogue.addAlert();

        //Verify
        Assertions.assertEquals(5, fileManager.numCallsToSaveAlertToFile);
    }

    @Test
    public void testNumberOfAlertsPosted() throws IOException, InterruptedException, java.text.ParseException {

        //setup
        catalogue.addAlert();
        PostSpy postSpy = new PostSpy();
        catalogue.setPostAlert(postSpy);

        //exercise
        catalogue.sendRequest();

        //verify
        Assertions.assertEquals(5, postSpy.alertsPosted);

    }

    @Test
    public void testHttpRequest() throws InterruptedException, IOException, java.text.ParseException {

        //setup

        AlertType alertType = Mockito.mock(AlertType.class);
        Mockito.when(alertType.getAlertType()).thenReturn(alertType.ELECTRONICS);
        catalogue.setAlertType(alertType);
        catalogue.addAlert();

        //exercise
        int result = catalogue.sendRequest().statusCode();

        //verify
        Assertions.assertEquals(201, result);
    }

    @Test
    public void testRequestUri() throws InterruptedException, java.text.ParseException, IOException {

        //setup
        catalogue.addAlert();

        //exercise
        URI result = catalogue.sendRequest().uri();
        URI required = URI.create("https://api.marketalertum.com/Alert");

        //verify
        Assertions.assertEquals(required,result);
    }

    @Test
    public void testDeleteAlertsRequest() throws java.text.ParseException, InterruptedException, IOException {

        //setup
        catalogue.addAlert();

        //exercise
        int result = catalogue.deleteAlerts();

        //verify
        Assertions.assertEquals(200, result);
    }

    @Test
    public void testClearLists() throws java.text.ParseException, InterruptedException, IOException {

        //setup
        catalogue.addAlert();

        //exercise
        catalogue.deleteAlerts();

        //verify
        int size = catalogue.alerts.size();
        Assertions.assertEquals(0,size);

    }
}

package marketAlert.alerts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AlertTests {

    Alert alert;

    @Test
    public void testHeading(){
        //setup
        alert = new Alert();
        alert.setHeading("heading");

        //exercise
        String result = alert.getHeading();

        //verify
        Assertions.assertEquals("heading",result);
    }

    @Test
    public void testDescription(){
        //setup
        alert = new Alert();
        alert.setDescription("description");

        //exercise
        String result = alert.getDescription();

        //verify
        Assertions.assertEquals("description",result);
    }

    @Test
    public void testUrl(){
        //setup
        alert = new Alert();
        alert.setUrl("url");

        //exercise
        String result = alert.getUrl();

        //verify
        Assertions.assertEquals("url",result);
    }

    @Test
    public void testImageUrl(){
        //setup
        alert = new Alert();
        alert.setImageUrl("imageUrl");

        //exercise
        String result = alert.getImageUrl();

        //verify
        Assertions.assertEquals("imageUrl",result);
    }

    @Test
    public void testPostedBy(){
        //setup
        alert = new Alert();
        alert.setPostedBy("postedBy");

        //exercise
        String result = alert.getPostedBy();

        //verify
        Assertions.assertEquals("postedBy",result);
    }

    @Test
    public void testAlertType(){
        //setup
        alert = new Alert();
        alert.setType(1);

        //exercise
        int result = alert.getType();

        //verify
        Assertions.assertEquals(1,result);
    }

    @Test
    public void testPriceInCents(){
        //setup
        alert = new Alert();
        alert.setPriceInCents(1);

        //exercise
        int result = alert.getPriceInCents();

        //verify
        Assertions.assertEquals(1,result);
    }
}

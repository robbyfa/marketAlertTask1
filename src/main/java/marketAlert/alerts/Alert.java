package marketAlert.alerts;

public class Alert {

    protected int alertType;
    protected String heading;
    protected String description;
    protected String url;
    protected String imageUrl;
    protected String postedBy;
    protected int priceInCents;




    public String getHeading() {
        return heading;
    }
    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPriceInCents(int priceInCents) {
        this.priceInCents = priceInCents;
    }

       public int getPriceInCents() {
        return priceInCents;
    }

    public void setType(int alertType) {
        this.alertType = alertType;
    }

    public int getType() {
        return alertType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getImageUrl(){
        return imageUrl;
    }



}



package marketAlert.spies;


import marketAlert.utils.PostAlert;

public class PostSpy implements PostAlert {

   public int alertsPosted = 0;

    public void postItem(){
        alertsPosted++;
        }
    }


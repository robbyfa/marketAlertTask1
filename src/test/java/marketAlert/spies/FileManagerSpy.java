package marketAlert.spies;


import marketAlert.alerts.Alert;
import marketAlert.utils.FileManager;

public class FileManagerSpy implements FileManager {

    public int numCallsToSaveAlertToFile = 0;

    public void saveProductToFile(Alert alert) {
        numCallsToSaveAlertToFile++;
    }

}

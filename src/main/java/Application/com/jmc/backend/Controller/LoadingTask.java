package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.AccountType;
import javafx.concurrent.Task;

public class LoadingTask extends Task<Integer> {
    @Override
    protected Integer call() throws Exception {
        // Start preloading views
        if (Model.getInstance().getFactoryViews().getAccountType() == AccountType.Member) {
            Model.getInstance().getFactoryViews().preLoadLibrary();
        }
        // This loop will continue until the views are loaded or progress reaches 100
        int progress = 0;

        while (progress < 100) {
            // If the library is loaded, set the progress to 100 and break out of the loop
            if (Model.getInstance().getFactoryViews().getAccountType() == AccountType.Librarian) {
                progress++;
                continue;
            }
            if (Model.getInstance().getFactoryViews().checkIfLoadedLibrary()) {
                progress = 100; // Set progress to 100 when all views are loaded
            } else {
                // If not yet loaded, keep setting progress to 99
                progress = Math.min(progress + 1, 99);
            }

            // Update the progress bar
            updateProgress(progress, 100.0);

            // Sleep for a bit to allow UI updates
            Thread.sleep(100);
        }

        return 100; // Task completed
    }
}

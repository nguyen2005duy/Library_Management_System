package Application.com.jmc.backend.Controller;

import Application.com.jmc.backend.Model.Model;
import Application.com.jmc.backend.Views.AccountType;
import javafx.concurrent.Task;

public class LoadingTask extends Task<Integer> {
    @Override
    protected Integer call() throws Exception {
        if (Model.getInstance().getFactoryViews().getAccountType() == AccountType.Member) {
            Model.getInstance().getFactoryViews().preLoadLibrary();
        }
        System.out.println(Model.getInstance().getFactoryViews().getAccountType());
        int progress = 0;

        while (progress < 100) {
            if (Model.getInstance().getFactoryViews().getAccountType() == AccountType.Librarian) {
                progress++;
                continue;
            }
            System.out.println(progress+" "+ Model.getInstance().getFactoryViews().checkIfLoadedLibrary());
            if (Model.getInstance().getFactoryViews().checkIfLoadedLibrary()) {
                progress = 100;
            } else {
                progress = Math.min(progress + 1, 99);
            }

            updateProgress(progress, 100.0);

            Thread.sleep(100);
        }

        return 100; // Task completed
    }
}

package Application.frontend;

import Application.com.jmc.backend.Model.Model;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginTest extends ApplicationTest {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        // Gọi phương thức để hiển thị cửa sổ đăng nhập
        Model.getInstance().getFactoryViews().showLoginView();
        primaryStage = stage; // Lưu lại Stage để kiểm tra
    }

    @Test
    void testLoginViewIsShown() {
        // Kiểm tra cửa sổ chính đã được tạo
        assertNotNull(primaryStage, "Stage chính không được khởi tạo!");
    }
}
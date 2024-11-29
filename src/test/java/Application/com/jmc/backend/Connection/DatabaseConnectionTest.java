package Application.com.jmc.backend.Connection;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class DatabaseConnectionTest {

    @Test
    void testDatabaseConnection() {
        DatabaseConnection.databaseLink = mock(Connection.class);
        Connection connection = DatabaseConnection.getConnection();
        assertNotNull(connection, "Kết nối cơ sở dữ liệu không được là null");
    }
}
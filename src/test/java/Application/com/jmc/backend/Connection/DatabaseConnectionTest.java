package Application.com.jmc.backend.Connection;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseConnectionTest {

    @Test
    void testMockStaticGetConnection() {
        Connection mockConnection = mock(Connection.class);

        try (MockedStatic<DatabaseConnection> mockedStatic = mockStatic(DatabaseConnection.class)) {
            mockedStatic.when(DatabaseConnection::getConnection).thenReturn(mockConnection);

            Connection connection = DatabaseConnection.getConnection();

            assertNotNull(connection);
            assertEquals(mockConnection, connection);

            mockedStatic.verify(DatabaseConnection::getConnection, times(1));
        }
    }
}
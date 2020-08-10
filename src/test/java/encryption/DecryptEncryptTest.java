package encryption;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import org.junit.Test;

public class DecryptEncryptTest {

    private ClassLoader classLoader = DecryptEncryptTest.class.getClassLoader();

    @Test
    public void shouldEncryptAMessageAndDecryptSuccessfully() throws IOException {
        // given
        String inputFilePath = Objects.requireNonNull(classLoader.getResource("cat.jpg")).getFile();
        Path temporaryOutputFilePath = Paths
                .get(Objects.requireNonNull(classLoader.getResource(".")).getFile(),
                        "cat_output.jpg");
        String secretMessage = "This is a secret message";

        // when
        Throwable encryptOperation =
                catchThrowable(
                        () ->
                                new Encrypter().encrypt(inputFilePath, secretMessage,
                                        Optional.of(temporaryOutputFilePath.toString()))
                );

        StringBuilder decryptedText = new StringBuilder();

        Throwable decryptOperation =
                catchThrowable(
                        () ->
                        {
                            DecryptResult decryptResult = new Decrypter()
                                    .decrypt(temporaryOutputFilePath.toString());
                            decryptedText.append(decryptResult.getData());
                        }
                );

        Throwable deleteOperation =
                catchThrowable(
                        () -> Files.delete(temporaryOutputFilePath));

        assertNull(encryptOperation);
        assertNull(decryptOperation);
        assertNull(deleteOperation);
        assertEquals(secretMessage, decryptedText.toString());
    }

}

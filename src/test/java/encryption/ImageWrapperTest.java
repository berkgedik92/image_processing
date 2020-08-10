package encryption;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Objects;
import org.junit.Test;

public class ImageWrapperTest {

    private final ClassLoader classLoader = DecryptEncryptTest.class.getClassLoader();
    private final String imageFilePath =
            Objects.requireNonNull(classLoader.getResource("cat.jpg")).getFile();

    @Test
    public void readImageSuccessfullyTest() {

        Throwable throwable =
                catchThrowable(
                        () -> new ImageWrapper(imageFilePath));

        assertNull(throwable);
    }

    @Test
    public void imageWrapperShouldReturnDimensionOfImage() {
        ImageWrapper imageWrapper = new ImageWrapper(imageFilePath);
        assertEquals(244860, imageWrapper.getPixelAmount());
    }

}

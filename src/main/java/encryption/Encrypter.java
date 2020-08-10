package encryption;

import static encryption.Utils.getCodeForChar;
import static encryption.Utils.getDigit;

import java.util.Optional;
import java.util.stream.IntStream;
import javafx.util.Pair;

class Encrypter {

    void encrypt(String imagePath, String text, Optional<String> outputFilePath) {
        ImageWrapper image = new ImageWrapper(imagePath);

        if (text.length() >= image.getPixelAmount() - 1) {
            throw new IllegalArgumentException(
                    "Text to encrypt is too long, for this picture maximum "
                            + (image.getPixelAmount() - 1)
                            + " characters are allowed.");
        }

        image.setRGBForPixel(text.length(), 0, 0, 0);

        IntStream.range(0, text.length()).sequential()
                .mapToObj(i -> new Pair<>(i, getCodeForChar(text.charAt(i))))
                .forEach(pair -> {
                    int R = (getDigit(pair.getValue(), 2)
                            + getDigit(image.getR(pair.getKey()), 2) * 100
                            + getDigit(image.getR(pair.getKey()), 1) * 10);

                    int G = (getDigit(pair.getValue(), 1)
                            + getDigit(image.getG(pair.getKey()), 2) * 100
                            + getDigit(image.getG(pair.getKey()), 1) * 10);

                    int B = (getDigit(pair.getValue(), 0)
                            + getDigit(image.getB(pair.getKey()), 2) * 100
                            + getDigit(image.getB(pair.getKey()), 1) * 10);

                    image.setRGBForPixel(pair.getKey(), R, G, B);
                });

        IntStream.range(text.length() + 1, image.getPixelAmount())
                .sequential()
                .forEach(i -> image.setRGBForPixel(i, Math.max(image.getR(i), 1), image.getG(i),
                        image.getB(i)));

        image.commit();
        if (outputFilePath.isPresent()) {
            image.save(outputFilePath.get());
        } else {
            image.save();
        }
    }
}

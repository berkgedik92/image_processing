package encryption;

import static encryption.Utils.getCharForCode;
import static encryption.Utils.isCodeValid;

public class Decrypter {

    DecryptResult decrypt(String imagePath) {
        ImageWrapper image = new ImageWrapper(imagePath);

        if (image.getBlackPixelAmount() != 1) {
            return DecryptResult.emptyResult();
        }

        int firstBlackPixel = image.getFirstBlackPixel();
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < firstBlackPixel; i++) {
            int code =
                    (image.getR(i) % 10) * 100 + (image.getG(i) % 10) * 10 + (image.getB(i)
                            % 10);
            if (!isCodeValid(code)) {
                return DecryptResult.emptyResult();
            } else {
                decryptedText.append(getCharForCode(code));
            }
        }
        return DecryptResult.resultWithData(decryptedText.toString());
    }
}

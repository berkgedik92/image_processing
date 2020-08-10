package encryption;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ColorProcessor;
import java.util.stream.IntStream;

class ImageWrapper {

    private ImagePlus image;
    private int[] R;
    private int[] G;
    private int[] B;

    private int firstBlackPixel;
    private int blackPixelAmount;

    ImageWrapper(String imagePath) {
        image = IJ.openImage(imagePath);
        R = new int[image.getHeight() * image.getWidth()];
        G = new int[image.getHeight() * image.getWidth()];
        B = new int[image.getHeight() * image.getWidth()];
        blackPixelAmount = 0;
        firstBlackPixel = -1;

        int index = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int[] rgb = image.getPixel(x, y);
                R[index] = rgb[0];
                G[index] = rgb[1];
                B[index] = rgb[2];
                if (rgb[0] + rgb[1] + rgb[2] == 0) {
                    blackPixelAmount++;
                    if (blackPixelAmount == 1) {
                        firstBlackPixel = index;
                    }
                }
                index++;
            }
        }
    }

    int getBlackPixelAmount() {
        return blackPixelAmount;
    }

    int getFirstBlackPixel() {
        return firstBlackPixel;
    }

    int getR(int index) {
        return R[index];
    }

    int getG(int index) {
        return G[index];
    }

    int getB(int index) {
        return B[index];
    }

    int getPixelAmount() {
        return image.getWidth() * image.getHeight();
    }

    void setRGBForPixel(int index, int R, int G, int B) {
        this.R[index] = R;
        this.G[index] = G;
        this.B[index] = B;
    }

    void commit() {
        byte[] R = new byte[image.getWidth() * image.getHeight()];
        byte[] G = new byte[image.getWidth() * image.getHeight()];
        byte[] B = new byte[image.getWidth() * image.getHeight()];
        IntStream.range(0, image.getWidth() * image.getHeight()).sequential().forEach(i -> {
            R[i] = (byte) this.R[i];
            G[i] = (byte) this.G[i];
            B[i] = (byte) this.B[i];
        });

        ((ColorProcessor) image.getProcessor()).setRGB(R, G, B);
    }

    void save(String outputFilePath) {
        new FileSaver(image).saveAsTiff(outputFilePath);
    }

    void save() {
        new FileSaver(image).saveAsTiff();
    }
}

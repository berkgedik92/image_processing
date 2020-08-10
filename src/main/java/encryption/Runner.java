package encryption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Runner {

    private Options getCommandLineOptions() {
        Options options = new Options();
        options.addOption("e", "encrypt", false, "This flag runs the application in encrypt mode");
        options.addOption("d", "decrypt", false, "This flag runs the application in decrypt mode");
        options.addOption("i", "input_file", true,
                "In encrypt mode, specifies the image file path that will be used to put text. In decrypt mode, specifies the image file path where the application looks for an encrypted data");
        options.addOption("o", "output_file", true,
                "In encrypt mode, specifies the output image file path");
        options.addOption("t", "text", true,
                "In encrypt mode specifies the text which will be put inside the output image");
        options.addOption("tf", "text_file", true,
                "In encrypt mode specifies the file whose contents will be put inside the output image");
        return options;
    }

    private void checkArguments(CommandLine parsedArgs) {
        if (parsedArgs.hasOption("d") && parsedArgs.hasOption("e")) {
            throw new IllegalArgumentException(
                    "Cannot execute in both encrypt mode and decrypt mode");
        }
        if (!parsedArgs.hasOption("d") && !parsedArgs.hasOption("e")) {
            throw new IllegalArgumentException("Must choose either encrypt mode and decrypt mode");
        }

        if (!parsedArgs.hasOption("i")) {
            throw new IllegalArgumentException("Input file must be specified");
        }

        if (parsedArgs.hasOption("e")) {
            if (parsedArgs.hasOption("t") && parsedArgs.hasOption("tf")) {
                throw new IllegalArgumentException(
                        "Cannot specify both text and text file");
            }
            if (!parsedArgs.hasOption("t") && !parsedArgs.hasOption("tf")) {
                throw new IllegalArgumentException(
                        "Must specify either a text or a text file path");
            }
        }
    }

    public static void main(String[] args) throws ParseException {
        Runner runner = new Runner();
        Options options = runner.getCommandLineOptions();
        CommandLine parsedArgs = new DefaultParser().parse(options, args);
        runner.checkArguments(parsedArgs);
        String inputImagePath = parsedArgs.getOptionValue("i");

        if (parsedArgs.hasOption("d")) {
            DecryptResult result = new Decrypter().decrypt(inputImagePath);
            if (result.isDataFound()) {
                System.out.println("Encrypted text found in the loaded image");
                System.out.println(result.getData());
            } else {
                System.out.println("No encrypted text found in the loaded image");
            }
        } else if (parsedArgs.hasOption("e")) {
            String text;
            if (parsedArgs.hasOption("t")) {
                text = parsedArgs.getOptionValue("t");
            } else {
                try {
                    text = new String(
                            Files.readAllBytes(Paths.get(parsedArgs.getOptionValue("tf"))));
                } catch (IOException e) {
                    throw new IllegalStateException("Could not open the input text file", e);
                }
            }

            new Encrypter().encrypt(inputImagePath, text,
                    Optional.ofNullable(parsedArgs.getOptionValue("o")));
            System.out.println("Saved output image with encrypted text.");

        } else {
            throw new IllegalStateException("Should not reach here");
        }
    }
}

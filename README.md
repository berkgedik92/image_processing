# Experimental Image Processing Projects

Experimental image processing-related projects that use IJ library can be found in this repository. Details for each project are given below

## Text Encoding into Image

This is a simple application that takes an image and a text and encodes this text into the image in a way that the image does not change too much (it is almost impossible for a human to detect the changes). Then the same application can be used to read the text encoded in the image.

### Usage

To encode a text given in command-line into an image:
```
java -jar text_encoder.jar -e -i <path_to_input_image> -t "text to encode" [-o <path_for_output_image>]
```

To encode a text from a file into an image:
```
java -jar text_encoder.jar -e -i <path_to_input_image> -tf <path_to_text_file> [-o <path_for_output_image>]
```

To decode an image and see the text encoded in the image file:
```
java -jar text_encoder.jar -d -i <path_to_input_image>
```
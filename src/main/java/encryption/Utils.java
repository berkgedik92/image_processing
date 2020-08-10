package encryption;

class Utils {

    // Allow only ASCII characters
    static int getCodeForChar(char c) {
        if (c < 32 || c > 126) {
            throw new IllegalArgumentException(
                    "Character " + c + "(code = " + (int) c + " is not supported");
        }
        return c - 31;
    }

    static char getCharForCode(int code) {
        if (!isCodeValid(code)) {
            throw new IllegalArgumentException(
                    "Code " + code + " is not supported");
        }
        return (char) (code + 31);
    }

    static boolean isCodeValid(int code) {
        return !(code < 0 || code > 94);
    }

    /*
        returns the "place"th digit of "number"

        For example:
        number = 987

        for place = 0 returns 7
            place = 1 returns 8
            place = 2 returns 9
            place > 3 returns 0
     */
    static int getDigit(int number, int place) {
        return (int) ((number % Math.pow(10, place + 1)) / Math.pow(10, place));
    }

}

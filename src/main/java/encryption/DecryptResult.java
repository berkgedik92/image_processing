package encryption;

class DecryptResult {

    enum DecryptResultType {ENCRYPTED_DATA_FOUND, NO_ENCRYPTED_DATA}

    private DecryptResultType type;
    private String data;

    private DecryptResult(DecryptResultType type, String data) {
        this.type = type;
        this.data = data;
    }

    static DecryptResult emptyResult() {
        return new DecryptResult(DecryptResultType.NO_ENCRYPTED_DATA, "");
    }

    static DecryptResult resultWithData(String data) {
        return new DecryptResult(DecryptResultType.ENCRYPTED_DATA_FOUND, data);
    }

    public boolean isDataFound() {
        return type.equals(DecryptResultType.ENCRYPTED_DATA_FOUND);
    }

    public String getData() {
        return data;
    }
}

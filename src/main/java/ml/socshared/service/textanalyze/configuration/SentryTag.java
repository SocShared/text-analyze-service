package ml.socshared.service.textanalyze.configuration;

public enum SentryTag {
    EXTRACT_KEY_WORDS("type","extract_key_words"),
    SEARCH_TARGET_PHRASES("type", "search_target_phrases"),
    ADD_TARGET_PHRASES("type", "add_target_phrases"),
    GET_TARGET_PHRASES("type", "get_target_phrases");

    SentryTag(String t, String tag) {
        type = t;
        sentryTag = tag;
    }

    public String type() {
        return type;
    }
    public String value() {
        return sentryTag;
    }

    private final String sentryTag;
    private final String type;
    public static final String SERVICE_NAME = "TAS";

}

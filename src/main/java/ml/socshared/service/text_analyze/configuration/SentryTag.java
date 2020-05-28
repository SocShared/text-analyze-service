package ml.socshared.service.text_analyze.configuration;

public enum SentryTag {
    ExtractKeyWords("type","extract_key_words"),
    SearchTargetPhrases("type", "search_target_phrases"),
    AddTargetPhrases("type", "add_target_phrases"),
    GetTargetPhrases("type", "get_target_phrases");

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

    private String sentryTag;
    private String type;
    public static final String service_name = "TAS";

}

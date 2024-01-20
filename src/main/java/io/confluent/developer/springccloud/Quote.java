package io.confluent.developer.springccloud;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quote {
    @JsonProperty
    public String quote;

    public Quote(String quote) {
        this.quote = quote;
    }
}

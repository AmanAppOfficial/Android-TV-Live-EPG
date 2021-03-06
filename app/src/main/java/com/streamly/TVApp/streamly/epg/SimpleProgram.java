package com.streamly.TVApp.streamly.epg;

public class SimpleProgram {

    String id;
    String description;
    String metadata;

    public SimpleProgram() {
    }

    public SimpleProgram(String id, String description, String metadata) {
        this.id = id;
        this.description = description;
        this.metadata = metadata;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}

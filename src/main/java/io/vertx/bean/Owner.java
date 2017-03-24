package io.vertx.bean;

public class Owner {
    private String id;
    private String provider;
    private String display_name;

    public Owner(String id, String provider, String display_name) {
        this.id = id;
        this.provider = provider;
        this.display_name = display_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}

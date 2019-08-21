package kiwi.blog.common.enums;

public enum  OauthType {
    NAVER("naver"),
    GOOGLE("google");

    private String value;

    OauthType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

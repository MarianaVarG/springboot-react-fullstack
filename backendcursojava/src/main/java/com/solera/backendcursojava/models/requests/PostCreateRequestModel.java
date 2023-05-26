package com.solera.backendcursojava.models.requests;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/*Fields that client sends to the API*/
public class PostCreateRequestModel {
    public static final String TITLE_IS_MANDATORY = "Title is mandatory.";
    public static final String CONTENT_IS_MANDATORY = "Content is mandatory.";
    public static final String EXPOSURE_IS_MANDATORY = "Exposure is mandatory.";
    public static final String EXPOSURE_IS_INVALID = "Exposure is invalid.";
    public static final String EXPIRATION_TIME_IS_MANDATORY = "Expiration Time is mandatory.";
    public static final String EXPIRATION_TIME_IS_INVALID = "Expiration Time is invalid.";
    @NotBlank(message = TITLE_IS_MANDATORY)
    @NotNull(message = TITLE_IS_MANDATORY)
    @NotEmpty(message = TITLE_IS_MANDATORY)
    @Size(min = 1)
    private String title;

    @NotBlank(message = CONTENT_IS_MANDATORY)
    @NotNull(message = CONTENT_IS_MANDATORY)
    @NotEmpty(message = CONTENT_IS_MANDATORY)
    private String content;

    @NotNull(message = EXPOSURE_IS_MANDATORY)
    @Range(min = 1, max = 2, message = EXPOSURE_IS_INVALID)
    private long exposureId;

    //@NotBlank(message = EXPIRATION_TIME_IS_MANDATORY)
    @NotNull(message = EXPIRATION_TIME_IS_MANDATORY)
    @Range(min = 0, max = 1440, message = EXPIRATION_TIME_IS_INVALID)
    private int expirationTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getExposureId() {
        return exposureId;
    }

    public void setExposureId(long exposureId) {
        this.exposureId = exposureId;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }
}

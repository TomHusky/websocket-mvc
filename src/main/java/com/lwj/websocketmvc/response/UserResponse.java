package com.lwj.websocketmvc.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author gzfyit
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserResponse implements Serializable {

    @JsonProperty(value = "name")
    private String userName;

    @JsonProperty(value = "userApikey")
    private String userApiKey;

    @JsonProperty(value = "flagCode")
    private String flagCode;
}

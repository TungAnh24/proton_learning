package com.proton.learning.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Setter @Getter @Accessors(chain = true)
public class EmployeeDto {
    private Integer eplId;
    private String eplUsername;
    private String eplPassword;
    private String eplFullName;
    private String eplPhone;
    private String eplEmail;
    private String eplAddress;
//    private LocalDateTime eplCreateTime;
//    private LocalDateTime eplLastModifiedTime;
    private Boolean eplIsActivated;
//    private Boolean eplIsDeleted;
}

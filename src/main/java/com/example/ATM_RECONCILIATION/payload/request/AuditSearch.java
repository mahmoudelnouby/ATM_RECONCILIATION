package com.example.ATM_RECONCILIATION.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditSearch {
    Integer actionId;
    String actionName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date actionDate;
    String username;
    String otherDetails;
}

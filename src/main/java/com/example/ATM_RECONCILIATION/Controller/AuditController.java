package com.example.ATM_RECONCILIATION.Controller;

import com.example.ATM_RECONCILIATION.GlobalEnums.ResponseMessage;
import com.example.ATM_RECONCILIATION.payload.request.AuditSearch;
import com.example.ATM_RECONCILIATION.payload.response.Response;
import com.example.ATM_RECONCILIATION.services.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("audit")
@CrossOrigin(origins = "*")
public class AuditController {
    AuditService auditService;
    public AuditController(AuditService auditService){this.auditService = auditService;}

    @PostMapping("search/{pageNumber}/{pageSize}")
//    @PreAuthorize("hasAuthority('ScAudit.view')")
    public ResponseEntity<?> Search(@RequestBody AuditSearch audit, @PathVariable int pageNumber , @PathVariable int pageSize){

        return ResponseEntity.ok(new Response(
                200,"Successful request",
                ResponseMessage.Succesful_Request.getMessageByApi("AuditSearch"),
                auditService.SearchAudit(audit,pageNumber,pageSize)));
    }
}

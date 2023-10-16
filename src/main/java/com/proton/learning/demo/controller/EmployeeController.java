package com.proton.learning.demo.controller;

import com.proton.learning.demo.customException.IdNotFoundException;
import com.proton.learning.demo.dto.EmployeeDto;
import com.proton.learning.demo.service.EmployeeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {

    private final EmployeeServiceImpl eplService;

    public EmployeeController(EmployeeServiceImpl eplService) {
        this.eplService = eplService;
    }

    @GetMapping("listEpl")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getAllEpl(){
        var res = eplService.getLstEmployee();
        return res.getSucceed() ? ResponseEntity.ok(res)
                : ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(IdNotFoundException.class)
    @GetMapping("epl/{eplId}")
    public ResponseEntity<?> getEpl(@PathVariable(name = "eplId") Integer eplId) throws Exception, IdNotFoundException {
        var res = eplService.findEplById(eplId);
        return res.getSucceed() ? ResponseEntity.ok(res)
                : ResponseEntity.badRequest().body(res);
    }

    @PostMapping("add-new")
    public ResponseEntity<?> insertEpl(@RequestBody EmployeeDto eplDto) {
        var res = eplService.insertEpl(eplDto);
        return res.getSucceed() ? ResponseEntity.ok(res)
                : ResponseEntity.badRequest().body(res);
    }

    @PostMapping("update")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> updateEpl(@RequestBody EmployeeDto eplDto){
        var res = eplService.updateEpl(eplDto);
        return res.getSucceed() ? ResponseEntity.ok(res)
                : ResponseEntity.badRequest().body(res);
    }

    @PostMapping("delete/{eplId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> deleteEpl(@PathVariable(name = "eplId") Integer eplId){
        var res = eplService.deleteEpl(eplId);
        return res.getSucceed() ?
                ResponseEntity.ok(res)
                : ResponseEntity.badRequest().body(res);
    }
}

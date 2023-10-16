package com.proton.learning.demo.dto;

import com.proton.learning.demo.entities.Employee;

public class DtoConvert {
    public static EmployeeDto convertEplToDto(Employee epl){
        return new EmployeeDto()
                .setEplId(epl.getId())
                .setEplUsername(epl.getUsername())
                .setEplPassword(epl.getPassword())
                .setEplFullName(epl.getFullName())
                .setEplPhone(epl.getPhone())
                .setEplEmail(epl.getEmail())
                .setEplAddress(epl.getAddress())
//                .setEplCreateTime(epl.getCreateTime())
//                .setEplLastModifiedTime(epl.getLastModifiedTime())
                .setEplIsActivated(epl.getIsActivated())
//                .setEplIsDeleted(epl.getIsDeleted())
                ;
    }

    public static Employee convertDtoToEpl(EmployeeDto eplDto){
        return new Employee()
                .setId(eplDto.getEplId())
                .setUsername(eplDto.getEplUsername())
                .setPassword(eplDto.getEplPassword())
                .setFullName(eplDto.getEplFullName())
                .setPhone(eplDto.getEplPhone())
                .setEmail(eplDto.getEplEmail())
                .setAddress(eplDto.getEplAddress())
//                .setCreateTime(eplDto.getEplCreateTime())
//                .setLastModifiedTime(eplDto.getEplLastModifiedTime())
                .setIsActivated(eplDto.getEplIsActivated());
//                .setIsDeleted(eplDto.getEplIsDeleted());
    }
}

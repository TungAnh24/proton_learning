package com.proton.learning.demo.service;

import com.proton.learning.demo.customException.DuplicatedEntityException;
import com.proton.learning.demo.customException.IdNotFoundException;
import com.proton.learning.demo.dto.BaseResponse;
import com.proton.learning.demo.dto.DtoConvert;
import com.proton.learning.demo.dto.EmployeeDto;
import com.proton.learning.demo.entities.Employee;
import com.proton.learning.demo.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl {

    private final EmployeeRepository eplRepository;
    private final PasswordEncoder pwEncoder;

    public BaseResponse getLstEmployee() {
        var lstEpl = eplRepository.findAll()
                .stream().filter(e -> e.getIsActivated() && !e.getIsDeleted()).collect(Collectors.toList());
        return BaseResponse.success(lstEpl).setMessage("Get lstEpl successfully");
    }

    public BaseResponse findEplById(Integer eplId) {
        try {
            var epl = eplRepository.findById(eplId)
                    .orElseThrow(() -> new IdNotFoundException("Employee invalid by id: " + eplId));
            log.info("Get epl by id: " + eplId + " successfully");
            return BaseResponse.success(Optional.of(epl)).setMessage("Get epl successfully");
        } catch (Exception ex){
            log.error("Get epl fail by error: " + ex.getMessage());
            return BaseResponse.error(ex.getMessage());
        }
    }

    public BaseResponse insertEpl(EmployeeDto eplDto) {
        try {
            var eplExist = eplRepository.findById(eplDto.getEplId()).orElse(null);

            if (!Objects.isNull(eplExist))
                throw new DuplicatedEntityException("Employee already exist by id: " + eplDto.getEplId());

            var eplCurrent = eplRepository.checkExistEpl(eplExist.getUsername(),
                    eplExist.getPhone(), eplExist.getEmail());
            if (eplCurrent)
                throw new RuntimeException("Information employee exist in the system");
            eplExist.setPassword(pwEncoder.encode(eplDto.getEplPassword()));
            eplExist.setIsActivated(true);
            var eplSave = eplRepository.save(eplExist);
            return BaseResponse.success(DtoConvert.convertEplToDto(eplSave));
        } catch (Exception ex) {
            log.error("Cannot insert employee by id: " + eplDto.getEplId() + " FAIL: " + ex.getMessage());
            return BaseResponse.error(ex.getMessage()).setMessage("Cannot insert employee by id: " + eplDto.getEplId());
        }
    }

    public BaseResponse updateEpl(EmployeeDto eplDto) {
        try {
            var eplFound = eplRepository.findById(eplDto.getEplId());
            if (!eplFound.isPresent())
                throw new RuntimeException("Employee invalid by id: " + eplFound.get().getId());
            var eplInfoExist = eplRepository.findAll().stream()
                    .filter(e -> (e.getUsername().equals(eplDto.getEplUsername())
                            || e.getPhone().contains(eplDto.getEplPhone())
                            || e.getEmail().contains(eplDto.getEplEmail()))
                            && !Objects.equals(e.getId(), eplFound.get().getId())).toList();
            if (eplInfoExist.size() > 0) {
                log.error("Information employee exist in system");
                throw new Exception("Information employee exist in system");
//                return BaseResponse.error().setMessage("Information employee exist in system");
            }
            eplFound.get().setId(eplDto.getEplId())
                    .setUsername(eplDto.getEplUsername())
                    .setPassword(pwEncoder.encode(eplDto.getEplPassword()))
                    .setFullName(eplDto.getEplFullName())
                    .setPhone(eplDto.getEplPhone())
                    .setEmail(eplDto.getEplEmail())
                    .setAddress(eplDto.getEplAddress())
                    .setIsActivated(eplDto.getEplIsActivated());
            eplFound.get().setLastModifiedTime(LocalDateTime.now());
            var eplSave = eplRepository.save(eplFound.get());
            log.info("Update employee successfully");
            return BaseResponse.success(eplSave).setMessage("Update employee successfully");
        } catch (Exception ex) {
            log.error("Update employee fail: " + ex.getMessage());
            return BaseResponse.error(ex.getMessage()).setMessage("Update employee fail: " + ex.getMessage());
        }
    }

    public BaseResponse deleteEpl(Integer eplId) {
        try {
            var eplExist = eplRepository.findById(eplId);
            if (eplExist.isEmpty())
                throw new RuntimeException("Employee invalid by id: " + eplId);
            eplExist.get().setLastModifiedTime(LocalDateTime.now());
            eplExist.get().setIsActivated(false);
            eplExist.get().setIsDeleted(true);
            var eplSave = eplRepository.save(eplExist.get());
            log.info("Deleted employee successfully");
            return BaseResponse.success(eplSave).setMessage("Deleted employee successfully");
        } catch (Exception ex) {
            log.error("Deleted employee by id fail: " + ex.getMessage());
            return BaseResponse.error(ex.getMessage()).setMessage("Deleted employee by id fail");
        }
    }
}

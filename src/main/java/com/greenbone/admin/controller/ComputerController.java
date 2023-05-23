package com.greenbone.admin.controller;

import com.greenbone.admin.dto.ComputerDto;
import com.greenbone.admin.dto.ComputerFilter;
import com.greenbone.admin.service.ComputerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Tag(name = "V1-Computers")
@RestController
@RequestMapping("/v1/computers")
public class ComputerController {

    private final ComputerServiceImpl computerService;
    private final ModelMapper modelMapper;

    @Operation(description = "Get computer info by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", description = "No Computer found with the provided Id")})
    @GetMapping(path = "/{computerId}")
    public ResponseEntity<ComputerDto> getComputerInfoById(@PathVariable UUID computerId) {
        ComputerDto computer = computerService.getComputerInfoById(computerId);
        return new ResponseEntity<>(computer, HttpStatus.OK);
    }

    @Operation(description = "Get all computers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "204", description = "No results found")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ComputerDto>> getAllComputers(
        @ModelAttribute Optional<ComputerFilter> computerFilter) {
        List<ComputerDto> computers = computerService.getAllComputers(computerFilter.get());
        HttpStatus responseStatus = computers.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.status(responseStatus).body(computers);
    }

    @Operation(description = "Save new computer record")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "400", description = "Bad request"),})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
        MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComputerDto> addNewComputer(
        @Valid @RequestBody ComputerDetailsRequest computerDetails) {
        ComputerDto computerDto = modelMapper.map(computerDetails, ComputerDto.class);
        computerDto = computerService.saveComputer(computerDto);
        return new ResponseEntity<>(computerDto, HttpStatus.CREATED);
    }

    //    @Operation(description = "Update computer record")
    //    @ApiResponses(value = {
    //        @ApiResponse(responseCode = "200"),
    //        @ApiResponse(responseCode = "404", description = "No dress code found with the
    //        provided "
    //            + "ID")})
    //    @PutMapping(path = "/{computerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //    public ResponseEntity<ComputerDto> updateComputerInfo(@PathVariable String computerId,
    //        @Valid @RequestBody ComputerDetailsRequest computerDetailsRequest) {
    //
    //        return new ResponseEntity<>(null, HttpStatus.OK);
    //    }

    @Operation(description = "Delete computer record")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", description = "No DressCode found with the provided "
            + "ID")})
    @DeleteMapping(path = "/{computerId}")
    public ResponseEntity<String> deleteComputer(@PathVariable UUID computerId) {
        computerService.deleteComputer(computerId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }


    @Data
    private static class ComputerDetailsRequest {

        @NotBlank
        private String macAddress;
        @NotBlank
        private String computerName;
        @NotBlank
        private String ipAddress;
        private String description;
    }
}
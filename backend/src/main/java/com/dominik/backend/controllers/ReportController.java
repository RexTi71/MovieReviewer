package com.dominik.backend.controllers;

import com.dominik.backend.dto.ReportDto;
import com.dominik.backend.dto.ReportResponseDto;
import com.dominik.backend.model.Report;
import com.dominik.backend.service.ReportService;
import com.dominik.backend.service.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReportController {
    private final ReportService reportService;
    private final ResponseBuilder responseBuilder;

    @PostMapping("/report")
    public ResponseEntity<String> reportContent(@RequestBody ReportDto reportDto){
        try{
            return responseBuilder.buildSuccessResponse(reportService.addReport(reportDto));
        }catch (IllegalArgumentException ex){
            return responseBuilder.buildErrorResponse(ex.getMessage());
        }
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ReportResponseDto>> getReports(){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(reportService.getReports() );
    }
}

package com.dominik.backend.service;

import com.dominik.backend.dto.CommentResponseDto;
import com.dominik.backend.dto.ReportDto;
import com.dominik.backend.dto.ReportResponseDto;
import com.dominik.backend.model.Account;
import com.dominik.backend.model.Comment;
import com.dominik.backend.model.Report;
import com.dominik.backend.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final AccountService accountService;
    private final CommentService commentService;

    public String addReport(ReportDto reportDto) throws RuntimeException{
        Report report = new Report();
        Account account = accountService.getAccountFromUsername(reportDto.getUsername());

        if(account == null){
            throw new RuntimeException("Nie ma takiego użytkownika");
        }
        Comment comment = commentService.getCommentById(reportDto.getCommentId());

        if(comment == null){
            throw new RuntimeException("Nie ma takiego komentarza");
        }

        report.setAccount(account);
        report.setComment(comment);
        reportRepository.save(report);
        return "Dziękujemy za zgłoszenie";
    }

    public List<ReportResponseDto> getReports(){
        List<Report> reports = reportRepository.findAll();
        List<ReportResponseDto> lightWeightReports = new ArrayList<>();
        reports.forEach(report -> {
            ReportResponseDto lightWeightReport = new ReportResponseDto();
            CommentResponseDto lightWeightComment = new CommentResponseDto();

            //Mapowanie do CommentResponseDto
            lightWeightComment.setId(report.getComment().getId());
            lightWeightComment.setUserId(report.getAccount().getId());
            lightWeightComment.setUsername(report.getAccount().getUsername());
            lightWeightComment.setContent(report.getComment().getContent());
            lightWeightComment.setDate(report.getComment().getDate());

            //Mapowanie do lzejsze wersji report
            lightWeightReport.setComment(lightWeightComment);
            lightWeightReport.setAccount(report.getAccount());
            lightWeightReports.add(lightWeightReport);
        });
        return lightWeightReports;
    }
}

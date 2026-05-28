package com.dominik.backend.repository;

import com.dominik.backend.model.Report;
import com.dominik.backend.model.chiaveComplessa.ReportId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, ReportId> {
}

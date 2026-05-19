package com.dominik.backend.Archivio;

import com.dominik.backend.Entità.Report;
import com.dominik.backend.Entità.Session;
import com.dominik.backend.Entità.chiaveComplessa.ReportId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchSession extends JpaRepository<Session, Long> {
}

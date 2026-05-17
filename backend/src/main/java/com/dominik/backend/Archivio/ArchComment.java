package com.dominik.backend.Archivio;

import com.dominik.backend.Entità.Account;
import com.dominik.backend.Entità.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchComment extends JpaRepository<Comment,Long> {
}

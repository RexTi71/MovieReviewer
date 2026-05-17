package com.dominik.backend.Archivio;

import com.dominik.backend.Entità.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchCategory extends JpaRepository<Category,Long> {

}

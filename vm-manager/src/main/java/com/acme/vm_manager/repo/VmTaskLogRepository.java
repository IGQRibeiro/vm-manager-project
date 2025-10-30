package com.acme.vm_manager.repo;

import com.acme.vm_manager.domain.VmTaskLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VmTaskLogRepository extends JpaRepository<VmTaskLog, Long> {}

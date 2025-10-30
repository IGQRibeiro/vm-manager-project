package com.acme.vm_manager.web;

import com.acme.vm_manager.domain.VmTaskLog;
import com.acme.vm_manager.repo.VmTaskLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/logs")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskLogController {

    private final VmTaskLogRepository repo;

    public TaskLogController(VmTaskLogRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Page<VmTaskLog> list(Pageable pageable) {
        return repo.findAll(pageable);
    }
}

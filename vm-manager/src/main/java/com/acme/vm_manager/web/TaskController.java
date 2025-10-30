package com.acme.vm_manager.web;

import com.acme.vm_manager.domain.VmTaskLog;
import com.acme.vm_manager.service.VirtualMachineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final VirtualMachineService service;

    public TaskController(VirtualMachineService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<VmTaskLog>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.listLogs(pageable));
    }
}

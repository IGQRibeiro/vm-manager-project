package com.acme.vm_manager.web.dto;

import com.acme.vm_manager.domain.VMStatus;
import com.acme.vm_manager.domain.VirtualMachine;
import com.acme.vm_manager.service.VirtualMachineService;
import com.acme.vm_manager.web.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vms")
public class VmController {

    private final VirtualMachineService service;

    public VmController(VirtualMachineService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<VMResponseDTO> create(@Valid @RequestBody VMCreateDTO dto, Principal principal) {
        var vm = VirtualMachine.builder()
                .name(dto.name())
                .cpu(dto.cpu())
                .memoryGb(dto.memoryGb())
                .diskGb(dto.diskGb())
                .build();
        var username = principal != null ? principal.getName() : "system";
        var saved = service.create(vm, username);
        return ResponseEntity.ok(toDto(saved));
    }

    // LIST (pagina + filtro opcional name/status)
    @GetMapping
    public ResponseEntity<Page<VMResponseDTO>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        VMStatus st = null;
        if (status != null && !status.isBlank()) {
            st = VMStatus.valueOf(status.toUpperCase());
        }
        var result = service.list(name, st, pageable);
        List<VMResponseDTO> content = result.getContent().stream().map(this::toDto).toList();
        return ResponseEntity.ok(new PageImpl<>(content, pageable, result.getTotalElements()));
    }

    // GET by id
    @GetMapping("/{id}")
    public ResponseEntity<VMResponseDTO> get(@PathVariable Long id) {
        var vm = service.get(id);
        return ResponseEntity.ok(toDto(vm));
    }

    // UPDATE (troca name/cpu/memory/disk)
    @PutMapping("/{id}")
    public ResponseEntity<VMResponseDTO> update(
            @PathVariable Long id, @Valid @RequestBody VMUpdateDTO dto, Principal principal) {

        var username = principal != null ? principal.getName() : "system";
        var updated = service.update(id, vm -> {
            vm.setName(dto.name());
            vm.setCpu(dto.cpu());
            vm.setMemoryGb(dto.memoryGb());
            vm.setDiskGb(dto.diskGb());
        }, username);
        return ResponseEntity.ok(toDto(updated));
    }

    // PATCH status: { "action": "start|stop|suspend" }
    @PatchMapping("/{id}/status")
    public ResponseEntity<VMResponseDTO> changeStatus(
            @PathVariable Long id, @Valid @RequestBody StatusActionDTO body, Principal principal) {
        var username = principal != null ? principal.getName() : "system";
        var vm = service.changeStatus(id, body.action(), username);
        return ResponseEntity.ok(toDto(vm));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        var username = principal != null ? principal.getName() : "system";
        service.delete(id, username);
        return ResponseEntity.noContent().build();
    }

    // mapper simples
    private VMResponseDTO toDto(VirtualMachine vm) {
        return new VMResponseDTO(
                vm.getId(),
                vm.getName(),
                vm.getCpu(),
                vm.getMemoryGb(),
                vm.getDiskGb(),
                vm.getStatus().name(),
                vm.getCreatedAt()
        );
    }
}

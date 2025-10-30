package com.acme.vm_manager.service;

import com.acme.vm_manager.domain.VMStatus;
import com.acme.vm_manager.domain.VirtualMachine;
import com.acme.vm_manager.domain.VmTaskLog;
import com.acme.vm_manager.repo.VirtualMachineRepository;
import com.acme.vm_manager.repo.VmTaskLogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class VirtualMachineService {

    private final VirtualMachineRepository repo;
    private final VmTaskLogRepository logRepo;

    public VirtualMachineService(VirtualMachineRepository repo, VmTaskLogRepository logRepo) {
        this.repo = repo;
        this.logRepo = logRepo;
    }

    // CREATE
    public VirtualMachine create(VirtualMachine vm, String username) {
        var saved = repo.save(vm);
        logRepo.save(VmTaskLog.builder()
                .vmId(saved.getId())
                .vmName(saved.getName())
                .action("CREATE")
                .username(username)
                .timestamp(Instant.now().truncatedTo(ChronoUnit.MILLIS))
                .build());
        return saved;
    }

    // READ (by id)
    public VirtualMachine get(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("VM not found"));
    }

    // LIST (filtros + paginação)
    public Page<VirtualMachine> list(String name, VMStatus status, Pageable pageable) {
        Specification<VirtualMachine> spec = Specification.where(null);

        if (name != null && !name.isBlank()) {
            spec = spec.and((root, q, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (status != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("status"), status));
        }
        return repo.findAll(spec, pageable);
    }

    // UPDATE
    public VirtualMachine update(Long id, java.util.function.Consumer<VirtualMachine> updater, String username) {
        var vm = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("VM not found"));
        updater.accept(vm);
        var saved = repo.save(vm);
        logRepo.save(VmTaskLog.builder()
                .vmId(saved.getId())
                .vmName(saved.getName())
                .action("UPDATE")
                .username(username)
                .timestamp(Instant.now().truncatedTo(ChronoUnit.MILLIS))
                .build());
        return saved;
    }

    // DELETE
    public void delete(Long id, String username) {
        var vm = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("VM not found"));
        repo.delete(vm);
        logRepo.save(VmTaskLog.builder()
                .vmId(vm.getId())
                .vmName(vm.getName())
                .action("DELETE")
                .username(username)
                .timestamp(Instant.now().truncatedTo(ChronoUnit.MILLIS))
                .build());
    }

    // CHANGE STATUS
    public VirtualMachine changeStatus(Long id, String action, String username) {
        var vm = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("VM not found"));
        switch (action.toUpperCase()) {
            case "START" -> {
                if (vm.getStatus() == VMStatus.STOPPED || vm.getStatus() == VMStatus.SUSPENDED)
                    vm.setStatus(VMStatus.STARTED);
                else throw new IllegalStateException("Só inicia a partir de STOPPED/SUSPENDED");
            }
            case "STOP" -> vm.setStatus(VMStatus.STOPPED);
            case "SUSPEND" -> {
                if (vm.getStatus() == VMStatus.STARTED)
                    vm.setStatus(VMStatus.SUSPENDED);
                else throw new IllegalStateException("Só suspende a partir de STARTED");
            }
            default -> throw new IllegalArgumentException("Ação inválida");
        }

        var saved = repo.save(vm);

        logRepo.save(VmTaskLog.builder()
                .vmId(saved.getId())
                .vmName(saved.getName())
                .action(action.toUpperCase())
                .username(username)
                .timestamp(Instant.now().truncatedTo(ChronoUnit.MILLIS))
                .build());

        return saved;
    }

    // LOGS
    public Page<VmTaskLog> listLogs(Pageable pageable) {
        return logRepo.findAll(pageable);
    }
}

package com.acme.vm_manager.repo;

import com.acme.vm_manager.domain.VirtualMachine;
import com.acme.vm_manager.domain.VMStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface VirtualMachineRepository
        extends JpaRepository<VirtualMachine, Long>, JpaSpecificationExecutor<VirtualMachine> {
    List<VirtualMachine> findByStatus(VMStatus status);
    List<VirtualMachine> findByNameContainingIgnoreCase(String name);
}

-- Ajusta PK e FK para BIGINT

-- 1) virtual_machine.id -> BIGINT
ALTER TABLE virtual_machine
  ALTER COLUMN id TYPE BIGINT;

-- 2) vm_task_log.id -> BIGINT
ALTER TABLE vm_task_log
  ALTER COLUMN id TYPE BIGINT;

-- 3) vm_task_log.vm_id (FK) -> BIGINT
ALTER TABLE vm_task_log
  ALTER COLUMN vm_id TYPE BIGINT;

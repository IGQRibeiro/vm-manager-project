-- V3__add_timestamp_to_vm_task_log.sql

ALTER TABLE vm_task_log
ADD COLUMN IF NOT EXISTS "timestamp" timestamp without time zone;

-- Preenche os registros antigos
UPDATE vm_task_log
SET "timestamp" = COALESCE(created_at, NOW())
WHERE "timestamp" IS NULL;

-- Garante NOT NULL daqui pra frente
ALTER TABLE vm_task_log
ALTER COLUMN "timestamp" SET NOT NULL;

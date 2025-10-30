export interface VmTaskLog {
  id?: number;
  vmId: number;
  vmName: string;
  username: string;
  action: string;
  createdAt: string;
}

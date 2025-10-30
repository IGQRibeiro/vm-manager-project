export interface VirtualMachine {
  id?: number;
  name: string;
  cpu: number;
  memoryGb: number;
  diskGb: number;
  status?: 'STOPPED' | 'STARTED' | 'SUSPENDED';
  createdAt?: string;
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VirtualMachine } from '../models/vm.model';

export interface PageResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
}

@Injectable({ providedIn: 'root' })
export class VmService {
  private api = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) {}

  getVms(page = 0, size = 10): Observable<PageResponse<VirtualMachine>> {
    return this.http.get<PageResponse<VirtualMachine>>(
      `${this.api}/vms?page=${page}&size=${size}`
    );
  }

  getVm(id: number): Observable<VirtualMachine> {
    return this.http.get<VirtualMachine>(`${this.api}/vms/${id}`);
  }

  createVm(vm: VirtualMachine): Observable<VirtualMachine> {
    return this.http.post<VirtualMachine>(`${this.api}/vms`, vm);
  }

  updateVm(id: number, vm: VirtualMachine): Observable<VirtualMachine> {
    return this.http.put<VirtualMachine>(`${this.api}/vms/${id}`, vm);
  }

  deleteVm(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/vms/${id}`);
  }

  changeStatus(
    id: number,
    action: 'start' | 'stop' | 'suspend'
  ): Observable<VirtualMachine> {
    return this.http.patch<VirtualMachine>(
      `${this.api}/vms/${id}/status`,
      { action }
    );
  }
  getLogs(page = 0, size = 10) {
    return this.http.get<any>(`${this.api}/logs?page=${page}&size=${size}`);
  }

}

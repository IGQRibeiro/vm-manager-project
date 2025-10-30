import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { VmService } from '../../core/services/vm.service';
import { VirtualMachine } from '../../core/models/vm.model';

@Component({
  selector: 'app-vm-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatPaginatorModule,
    MatSnackBarModule
  ],
  templateUrl: './vm-list.component.html',
  styleUrls: ['./vm-list.component.scss']
})
export class VmListComponent implements OnInit {
  displayedColumns = ['name', 'cpu', 'memoryGb', 'diskGb', 'status', 'createdAt', 'actions'];
  data: VirtualMachine[] = [];
  loading = false;

  totalElements = 0;
  pageIndex = 0;
  pageSize = 10;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private vmService: VmService,
    private snack: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void { this.load(); }

  load(event?: PageEvent): void {
    if (event) { this.pageIndex = event.pageIndex; this.pageSize = event.pageSize; }
    this.loading = true;
    this.vmService.getVms(this.pageIndex, this.pageSize).subscribe({
      next: (res) => {
        this.data = res.content;
        this.totalElements = res.totalElements;
        this.loading = false;
      },
      error: () => { this.snack.open('Falha ao carregar VMs', 'OK', { duration: 3000 }); this.loading = false; }
    });
  }

  goNew(): void { this.router.navigate(['/vms/new']); }
  goEdit(vm: VirtualMachine): void { this.router.navigate(['/vms', vm.id, 'edit']); }

  delete(vm: VirtualMachine): void {
    if (!vm.id) return;
    if (!confirm(`Excluir VM "${vm.name}"?`)) return;
    this.vmService.deleteVm(vm.id).subscribe({
      next: () => { this.snack.open('VM excluída', 'OK', { duration: 2500 }); this.load(); },
      error: () => this.snack.open('Erro ao excluir', 'OK', { duration: 3000 })
    });
  }

  change(vm: VirtualMachine, action: 'start'|'stop'|'suspend'): void {
    if (!vm.id) return;
    this.vmService.changeStatus(vm.id, action).subscribe({
      next: () => { this.snack.open(`Ação ${action} aplicada`, 'OK', { duration: 2500 }); this.load(); },
      error: () => this.snack.open(`Erro ao aplicar ${action}`, 'OK', { duration: 3000 })
    });
  }
}

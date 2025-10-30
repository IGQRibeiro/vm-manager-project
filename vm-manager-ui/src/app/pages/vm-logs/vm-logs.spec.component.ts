import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { VmService } from '../../core/services/vm.service';

@Component({
  selector: 'app-vm-logs',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatPaginatorModule],
  templateUrl: './vm-logs.component.html',
  styleUrl: './vm-logs.component.scss'
})
export class VmLogsComponent implements OnInit {
  displayedColumns = ['timestamp', 'vmName', 'action'];
  data: any[] = [];
  totalElements = 0;
  pageIndex = 0;
  pageSize = 10;

  constructor(private vmService: VmService) {}

  ngOnInit(): void { this.load(); }

  load(event?: PageEvent) {
    if (event) { this.pageIndex = event.pageIndex; this.pageSize = event.pageSize; }
    this.vmService.getLogs(this.pageIndex, this.pageSize).subscribe((res: any) => {
      this.data = res.content ?? [];
      this.totalElements = res.totalElements ?? 0;
    });
  }
}

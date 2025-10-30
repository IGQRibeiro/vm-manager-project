import { Routes } from '@angular/router';
import { VmListComponent } from './pages/vm-list/vm-list.component';
import { VmFormComponent } from './pages/vm-form/vm-form';
import { VmLogsComponent } from './pages/vm-logs/vm-logs.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'vms' },
  { path: 'vms', component: VmListComponent },
  { path: 'vms/new', component: VmFormComponent },
  { path: 'vms/:id/edit', component: VmFormComponent },
  { path: 'logs', component: VmLogsComponent },
  { path: '**', redirectTo: 'vms' },

];

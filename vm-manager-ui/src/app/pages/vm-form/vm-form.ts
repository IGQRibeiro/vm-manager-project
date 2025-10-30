import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { VmService } from '../../core/services/vm.service';
import { VirtualMachine } from '../../core/models/vm.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-vm-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './vm-form.html',
  styleUrl: './vm-form.scss'
})
export class VmFormComponent {
  private fb = inject(FormBuilder);
  private service = inject(VmService);
  protected router = inject(Router);
  private route = inject(ActivatedRoute);

  title = 'Nova VM';
  id?: number;

  form = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    cpu: [1, [Validators.required, Validators.min(1)]],
    memoryGb: [1, [Validators.required, Validators.min(1)]],
    diskGb: [1, [Validators.required, Validators.min(1)]],
  });

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = +idParam;
      this.title = 'Editar VM';
      this.service.getVm(this.id).subscribe((vm: VirtualMachine) => {
        this.form.patchValue({
          name: vm.name, cpu: vm.cpu, memoryGb: vm.memoryGb, diskGb: vm.diskGb
        });
      });
    }
  }

  submit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    const payload = this.form.value as Omit<VirtualMachine, 'id'>;

    const req = this.id
      ? this.service.updateVm(this.id, payload as VirtualMachine)
      : this.service.createVm(payload as VirtualMachine);

    req.subscribe(() => this.router.navigate(['/vms']));
  }
}

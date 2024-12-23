import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Field } from '../form/form.component';

@Component({
  selector: 'app-crud-container',
  templateUrl: './crud-container.component.html',
  styleUrl: './crud-container.component.css'
})
export class CrudContainerComponent {
  @Input() title: string = 'CRUD';
  @Input() fields: Field[] = [];
  @Input() items: any[] = [];
  @Input() currentPage = 1;
  @Input() totalPages = 1;
  @Input() isSubmitting = false;
  @Input() showAddButton: boolean = true;
  @Output() create = new EventEmitter<any>();
  @Output() update = new EventEmitter<any>();
  @Output() delete = new EventEmitter<any>();
  @Output() pageChange = new EventEmitter<number>();
  @Output() terminarCita = new EventEmitter<any>();

  showForm = false;
  selectedItem: any = null;

  onSubmit(data: any) {
    if (this.selectedItem) {
      this.update.emit({ ...data, id: this.selectedItem.id });
    } else {
      this.create.emit(data);
    }
  }

  editItem(item: any) {
    this.selectedItem = item;
    this.showForm = true;
  }

  deleteItem(item: any) {
    if (confirm('¿Está seguro de eliminar este registro?')) {
      this.delete.emit(item);
    }
  }

  cancelForm() {
    this.showForm = false;
    this.selectedItem = null;
  }

  onPageChange(page: number) {
    this.pageChange.emit(page);
  }

  onTerminarCita(item: any) {
    this.terminarCita.emit(item);
  }
}

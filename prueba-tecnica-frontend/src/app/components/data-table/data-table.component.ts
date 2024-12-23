import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrl: './data-table.component.css'
})
export class DataTableComponent {
  @Input() columns: { name: string; label: string }[] = [];
  @Input() items: any[] = [];
  @Input() currentPage = 1;
  @Input() totalPages = 1;

  @Output() onEdit = new EventEmitter<any>();
  @Output() onDelete = new EventEmitter<any>();
  @Output() pageChange = new EventEmitter<number>();
  @Output() terminarCita = new EventEmitter<any>();
}

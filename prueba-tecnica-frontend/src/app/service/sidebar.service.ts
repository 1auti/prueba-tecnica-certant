import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {
  private sidebarOpen = new BehaviorSubject(false);
  public sidebarOpen$ = this.sidebarOpen.asObservable();

  constructor() { }

  toggleSidebar(): void {
    this.sidebarOpen.next(!this.sidebarOpen.value);
  }

  showSidebar(): void {
    this.sidebarOpen.next(true);
  }

  hideSidebar(): void {
    this.sidebarOpen.next(false);
  }
}
import { Component, Output, EventEmitter, Input } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent {
  @Output() public clicked: EventEmitter<any> = new EventEmitter();
  @Output() public optionChanged: EventEmitter<String> = new EventEmitter();
  @Output() public logoutClicked: EventEmitter<any> = new EventEmitter();
  @Input() public pages: String[];
  @Input() public currentPage: String;

  constructor() {}

  public sendClickedNotification() {
    this.clicked.emit({});
  }

  public onChange(newValue) {
    this.optionChanged.emit(newValue);
  }

  public logout() {
    this.logoutClicked.emit({});
  }
}

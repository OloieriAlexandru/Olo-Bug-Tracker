import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bugs',
  templateUrl: './bugs.component.html',
  styleUrls: ['./bugs.component.scss'],
})
export class BugsComponent implements OnInit {
  public bugs: any[] = null;

  constructor() {}

  ngOnInit(): void {}
}

import {ChangeDetectionStrategy, Component} from '@angular/core';
import { Columns, Config, DefaultConfig } from 'ngx-easy-table';
import {HttpResponse} from "@angular/common/http";

import {EventAttack, EventService, RequestObject} from "./eventService";
import { Observable} from "rxjs";



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [EventService],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppComponent {
  public configuration: Config;
  public configurationDetails: Config;
  public columns: Columns[];
  public columnsDetails: Columns[];
  public data$: Observable<HttpResponse<EventAttack[]>>;
  public dataDetails$: RequestObject[];


  modal = false;


  constructor(private eventService: EventService) {}

  ngOnInit(): void {
    this.configuration = { ...DefaultConfig };
    this.configuration.searchEnabled = true;
    this.configuration.tableLayout.theme= 'dark';
    this.configuration.rows =100 ;
    this.configurationDetails = { ...DefaultConfig };
    this.configurationDetails.searchEnabled = true;
    this.configurationDetails.infiniteScroll = true;
    this.configurationDetails.paginationEnabled = false;


    this.configurationDetails.infiniteScrollThrottleTime = 10;
    this.configurationDetails.rows = 10;

    // ... etc.
    this.columns = [
      { key: 'ip', title: 'IP' },
      { key: 'dateStart', title: 'Data rozpoczęcia' },
      { key: 'dateEnd', title: 'Data zakończenia' },
      { key: 'frequency', title: 'Częstotliwość' },
      { key: 'status', title: 'Status' },
    ];

    this.columnsDetails = [
      { key: 'requestHeader', title: 'Żądanie' },
      { key: 'requestBody', title: 'Body' },
      { key: 'requestType', title: 'Typ' },
    ];



    this.data$ = this.eventService.getEvents('', false);

    this.startTimer();


  }

  eventEmitted($event: { event: string; value: any }): void {
    this.dataDetails$ =  [];
 $event.value.row.requests.forEach(r => this.dataDetails$.push(new RequestObject(r.requestHeader, r.requestBody, r.requestType)))

    this.showModal();
    // console.log('$event', $event);
  }


  startTimer() {
    setInterval(() => {
      this.data$ = this.eventService.getEvents('', false);
    },3000)
  }

  showModal(): void {
    this.modal = true;
  }

  hideModal(): void {
    this.modal = false;
  }

}

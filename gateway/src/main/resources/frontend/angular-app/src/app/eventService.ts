import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class EventService {
  private readonly BACKEND_URL =
    'http://localhost:8080/events';

  constructor(private http: HttpClient) {}

  getEvents(params: string = '', observe: boolean = true): Observable<HttpResponse<EventAttack[]>> {
    return this.http.get<EventAttack[]>(`${this.BACKEND_URL}${params}`, {
      observe: observe ? 'response' : null,
    });
  }
}

export interface EventAttack {
  id:number;
  ip: string;
  requests: RequestObject[];
  dateStart: string;
  dateEnd: string;
  frequency: number;
  status: string;

}

export class RequestObject {
  requestHeader:string;
  requestBody:string;
  requestType:string;


  constructor(requestHeader: string,requestBody: string, requestType: string) {
    this.requestHeader = requestHeader;
    this.requestBody = requestBody;
    this.requestType = requestType;
  }
}


import { Injectable } from '@angular/core';
import {HttpUtil} from '../util/http-util';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  constructor(private httpUtil: HttpUtil ) { }

  public getAccountLogList(currentPage: number, pageSize: number): Observable<any> {
    const url = '/log/accountLog/' + currentPage + '/' + pageSize;
    return this.httpUtil.get(url);
  }
  public getOperateLogList(currentPage: number, pageSize: number): Observable<any> {
    const url = 'log/operationLog/' + currentPage + '/' + pageSize;
    return this.httpUtil.get(url);
  }
}

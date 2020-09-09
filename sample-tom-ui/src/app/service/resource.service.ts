import { Injectable } from '@angular/core';
import {HttpUtil} from '../util/http-util';
import {HttpParams} from '@angular/common/http';

@Injectable()
export class ResourceService {

  constructor(private httpUtil: HttpUtil) { }

  public getAuthorityMenuByUid() {
    const url = 'resource/authorityMenu';
    return this.httpUtil.get(url);
  }

  public getMenuList() {
    const url = 'resource/menus';
    return this.httpUtil.get(url);
  }

  public addMenu(menu: any) {
    const url = 'resource/menu';
    return this.httpUtil.post(url, menu);
  }

  public modifyMenu(menu: any) {
    const url = 'resource/menu';
    return this.httpUtil.put(url, menu);
  }

  public deleteMenuByMenuId(menuId: number) {
    const url = 'resource/menu' + '/' + menuId;
    return this.httpUtil.delete(url);
  }

  public getRestApiList(teamId: number, currentPage: number, pageSize: number) {
    const url = 'resource/api' + '/' + teamId + '/' + currentPage + '/' + pageSize;
    return this.httpUtil.get(url);
  }

  public addRestApi(api: any) {
    const url = 'resource/api';
    return this.httpUtil.post(url, api);
  }

  public updateRestApi(api: any) {
    const url = 'resource/api';
    return this.httpUtil.put(url, api);
  }

  public deleteRestApi(apiId: number) {
    const url = 'resource/api' + '/' + apiId;
    return this.httpUtil.delete(url);
  }

}

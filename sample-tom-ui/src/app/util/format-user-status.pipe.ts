import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'formatUserStatus'})
export class FormatUserStatusPipe implements PipeTransform {

  transform(value: any): string {
    let status: string = '未知';
    if (value === '1' || value === 1) {
      status = '正常';
    } else if (value === 2 || value === '2' ) {
      status = '锁定';
    } else if (value === 3 || value === '3' ) {
      status = '删除';
    } else if (value === 4 || value === '4' ) {
      status = '非法';
    }
    return status;
  }

}

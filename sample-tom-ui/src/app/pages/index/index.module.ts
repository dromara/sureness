import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IndexRoutingModule } from './index-routing.module';
import { IndexComponent } from './index.component';
import { ApiManageComponent } from './api-manage/api-manage.component';
import { RoleManageComponent } from './role-manage/role-manage.component';


@NgModule({
  declarations: [IndexComponent, ApiManageComponent, RoleManageComponent],
  imports: [
    CommonModule,
    IndexRoutingModule
  ],
  exports: [IndexComponent, ApiManageComponent, RoleManageComponent]
})
export class IndexModule { }

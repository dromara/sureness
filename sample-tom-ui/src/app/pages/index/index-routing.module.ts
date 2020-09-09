import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ApiManageComponent} from './api-manage/api-manage.component';
import {RoleManageComponent} from './role-manage/role-manage.component';

const routes: Routes = [
  { path: '', component: ApiManageComponent },
  { path: 'api', component: ApiManageComponent },
  { path: 'role', component: RoleManageComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IndexRoutingModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LeftAsideComponent } from './left-aside/left-aside.component';
import { RightAsideComponent } from './right-aside/right-aside.component';



@NgModule({
  declarations: [HeaderComponent, FooterComponent, LeftAsideComponent, RightAsideComponent],
  imports: [
    CommonModule
  ],
  exports: [HeaderComponent, FooterComponent, LeftAsideComponent, RightAsideComponent]
})
export class ShareModule { }
